package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class MainFrame extends JFrame {

    private static final int MAIN_FRAME_WIDTH = 1200;
    private static final int MAIN_FRAME_HEIGHT = 750;
    private static final int SIDEBAR_MIN_WIDTH = 50;
    private static final int SIDEBAR_MAX_WIDTH = 200;
   
    // main panels
    private JSplitPane verticalSplitPane;
    private JSplitPane horizontalSplitPane;
    private JPanel sidebar;
    private JPanel photoListPanel;
    private JPanel photoDetailsPanel;
    
    // sidebar
    private JButton toggleSidebarButton;

    // photosPanel
    private PhotoTableModel photoTableModel;
    
    // photoDetailsPanel   
    private PhotoExifDataPanel photoExifDataPanel;
    private PhotoViewPanel photoViewPanel;

    // =========================================================================
    public MainFrame() {
        initMainFrame();
        initMainPanels();
        initSideBar();
        initPhotosPanel();
        initExifDataPanel();
        initMenu();
    }

    // =========================================================================
    private File getLastAddedPhotoFile() {
        return Optional.ofNullable(photoTableModel.getLastAddedPhoto())
                .map(Photo::getFile)
                .orElse(null);
    }

    // =========================================================================
    // listener
    // =========================================================================
    private void toggleSidebar() {
        if (toggleSidebarButton.getText().equals("<")) {
            horizontalSplitPane.setDividerLocation(SIDEBAR_MIN_WIDTH);  // Sidebar einklappen
            sidebar.setPreferredSize(new Dimension(SIDEBAR_MIN_WIDTH, 0));
            toggleSidebarButton.setText(">");
        } else {
            horizontalSplitPane.setDividerLocation(SIDEBAR_MAX_WIDTH);  // Sidebar ausklappen
            sidebar.setPreferredSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
            toggleSidebarButton.setText("<");
        }
    }

    private void setExifDataForSelectedPhoto(Photo photo) throws IOException {

        photoExifDataPanel.setExifDataForPhoto(photo);       
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
        photoListPanel = new JPanel(new BorderLayout());
        photoDetailsPanel = new JPanel(new BorderLayout());
        verticalSplitPane.setTopComponent(photoListPanel);
        verticalSplitPane.setBottomComponent(photoDetailsPanel);
//        verticalSplitPane.setResizeWeight(1.0); // photosPanel height grows higher with main frame

        // create sidebar: |=
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sidebar = new JPanel();
        sidebar.setMinimumSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        sidebar.setPreferredSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        horizontalSplitPane.setLeftComponent(sidebar);
        horizontalSplitPane.setRightComponent(verticalSplitPane);

        // set default position
        horizontalSplitPane.setDividerLocation(200);
        verticalSplitPane.setDividerLocation(getHeight() - 550);

        getContentPane().add(horizontalSplitPane);
    }

    private void initSideBar() {
        toggleSidebarButton = new JButton("<");
        toggleSidebarButton.addActionListener((ActionEvent e) -> {
            toggleSidebar();
        });
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(toggleSidebarButton, BorderLayout.NORTH);
        sidebar.add(buttonPanel, BorderLayout.WEST);
    }

    private void initPhotosPanel() {
        // Hinzufügen des Suchfelds und der Buttons im oberen Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton selectAllButton = new JButton("Select all");
        JButton deselectAllButton = new JButton("Deselect all");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
//        searchPanel.add(selectAllButton);
//        searchPanel.add(deselectAllButton);

        // Erstellen der Tabelle
        photoTableModel = new PhotoTableModel();
        JTable photosTable = new JTable(photoTableModel);
        photosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photosTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting() && photosTable.getSelectedRow() != -1) {
                try {
                    setExifDataForSelectedPhoto(photoTableModel.getPhotoAt(photosTable.getSelectedRow()));
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(photosTable);

        photoListPanel.add(searchPanel, BorderLayout.NORTH);
        photoListPanel.add(tableScrollPane, BorderLayout.CENTER);

        // drag and drop photos to table
        new DropTarget(photoListPanel, new DropPhotoListListener(photoTableModel));
    }

    private void initExifDataPanel() {

        photoExifDataPanel = new PhotoExifDataPanel();
        JScrollPane dataScrollPane = new JScrollPane(photoExifDataPanel);

        photoViewPanel = new PhotoViewPanel();
        JScrollPane photoViewScrollPane = new JScrollPane(photoViewPanel);

        // add panels
        photoDetailsPanel.add(dataScrollPane, BorderLayout.CENTER);
        photoDetailsPanel.add(photoViewScrollPane, BorderLayout.EAST);
    }    

    private void initMenu() {
        setJMenuBar(new Menu(photoTableModel));
    }
}
