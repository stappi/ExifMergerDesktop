package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class MainFrame extends JFrame {

    private static final int MAIN_FRAME_WIDTH = 1200;
    private static final int MAIN_FRAME_HEIGHT = 750;
    private static final int SIDEBAR_MAX_WIDTH = 200;

    // main panels
    private JSplitPane verticalSplitPane;
    private JSplitPane horizontalSplitPane;
    private Sidebar sidebar;
    private PhotoTablePanel photoListPanel;
    private JPanel photoDetailsPanel;
    private SettingsGeneralExifDataPanel generalExifDataPanel;
    private JPanel mergePriorizationPanel;
//
//    // photosPanel
//    private PhotoTableModel photoTableModel;

    // photoDetailsPanel   
    private ExifDataPanel photoExifDataPanel;
    private PhotoViewPanel photoViewPanel;

    // =========================================================================
    public MainFrame() {
        initMainFrame();
        initMainPanels();
//        initSideBar();
//        initPhotosPanel();
        initExifDataPanel();
        initGeneralExifDataPanel();
        initMergePriorizationPanel();

        initMenu();
    }
    
    // =========================================================================
    // update gui
    // =========================================================================
    public JSplitPane getHorizontalSplitPane() {
        return horizontalSplitPane;
    }

    public JSplitPane getVerticalSplitPane() {
        return verticalSplitPane;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public SettingsGeneralExifDataPanel getGeneralExifDataPanel() {
        return generalExifDataPanel;
    }

    public JPanel getMergePriorizationPanel() {
        return mergePriorizationPanel;
    }
    
    public ExifDataPanel getExifDataPanel() {
        return photoExifDataPanel;
    }
    
    public PhotoViewPanel getPhotoViewPanel() {
        return this.photoViewPanel;
    }
    
    public PhotoTablePanel getPhotoTablePanel() {
        return photoListPanel;
    }

    // =========================================================================
    // listener
    // =========================================================================
    private void setExifDataForSelectedPhoto(Photo photo) throws IOException {

        photoExifDataPanel.mergeExifDataForPhoto(photo);
        photoViewPanel.showPhotoOnView(photo);
    }

    // =========================================================================
    // init
    // =========================================================================
    private void initMainFrame() {

        setTitle("Exif Merger Desktop");
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Zentriert das Fenster auf dem Bildschirm
    }

    private void initMainPanels() {

        // create main panels for displaying photos and exif data: =
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        photoListPanel = new PhotoTablePanel(this);
        photoDetailsPanel = new JPanel(new BorderLayout());
        verticalSplitPane.setTopComponent(photoListPanel);
        verticalSplitPane.setBottomComponent(photoDetailsPanel);
//        verticalSplitPane.setResizeWeight(1.0); // photosPanel height grows higher with main frame
        verticalSplitPane.setOneTouchExpandable(true);

        // create sidebar: |=
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sidebar = new Sidebar(this);
        sidebar.setMinimumSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        sidebar.setPreferredSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        horizontalSplitPane.setLeftComponent(sidebar);
        horizontalSplitPane.setRightComponent(verticalSplitPane);
        horizontalSplitPane.setOneTouchExpandable(true);

        // set default position
        horizontalSplitPane.setDividerLocation(200);
        verticalSplitPane.setDividerLocation(getHeight() - 550);

        getContentPane().add(horizontalSplitPane);
    }

//    private void initPhotosPanel() {
//        // Hinzufügen des Suchfelds und der Buttons im oberen Panel
//        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        JTextField searchField = new JTextField(20);
//        JButton selectAllButton = new JButton("Select all");
//        JButton deselectAllButton = new JButton("Deselect all");
//
//        searchPanel.add(new JLabel("Search:"));
//        searchPanel.add(searchField);
////        searchPanel.add(selectAllButton);
////        searchPanel.add(deselectAllButton);
//
//        // Erstellen der Tabelle
//        photoTableModel = new PhotoTableModel();
//        JTable photosTable = new JTable(photoTableModel);
//        photosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        photosTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
//            if (!event.getValueIsAdjusting() && photosTable.getSelectedRow() != -1) {
//                try {
//                    setExifDataForSelectedPhoto(photoTableModel.getPhotoAt(photosTable.getSelectedRow()));
//                } catch (IOException ex) {
//                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//        JScrollPane tableScrollPane = new JScrollPane(photosTable);
//        
//        // context menu
//        JPopupMenu popupMenu = new JPopupMenu();
//        JMenuItem printExifInfoMenuItem = new JMenuItem("Print Exif Info");
//        printExifInfoMenuItem.addActionListener(e -> {
//            // Handle delete action here
//            // Get the selected row using table.getSelectedRow()
//            // Perform the delete operation
////            photoTableModel.getLastAddedPhoto().
//        });
//        popupMenu.add(printExifInfoMenuItem);
//        photosTable.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
//                    int row = photosTable.rowAtPoint(e.getPoint());
//                    int column = photosTable.columnAtPoint(e.getPoint());
//                    if (!photosTable.isRowSelected(row)) {
//                        photosTable.changeSelection(row, column, false, false);
//                    }
//                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//        });
//        
//        photoListPanel.add(searchPanel, BorderLayout.NORTH);
//        photoListPanel.add(tableScrollPane, BorderLayout.CENTER);
//
//        // drag and drop photos to table
//        new DropTarget(photoListPanel, new DropPhotoListListener(photoTableModel));
//    }

    private void initExifDataPanel() {

        photoExifDataPanel = new ExifDataPanel();
        JScrollPane dataScrollPane = new JScrollPane(photoExifDataPanel);

        photoViewPanel = new PhotoViewPanel();
        JScrollPane photoViewScrollPane = new JScrollPane(photoViewPanel);

        // add panels
        photoDetailsPanel.add(dataScrollPane, BorderLayout.CENTER);
        photoDetailsPanel.add(photoViewScrollPane, BorderLayout.EAST);
    }

    private void initGeneralExifDataPanel() {
        generalExifDataPanel = new SettingsGeneralExifDataPanel(this);
    }

    private void initMergePriorizationPanel() {

        mergePriorizationPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Merge Prio");
        mergePriorizationPanel.add(label, BorderLayout.CENTER);
    }

    private void initMenu() {
        setJMenuBar(new Menu(this));
    }
}
