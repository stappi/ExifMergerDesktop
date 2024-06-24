package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.FileUtilities;
import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class MainFrame extends JFrame {

    private static final int MAIN_FRAME_WIDTH = 1000;
    private static final int MAIN_FRAME_HEIGHT = 600;
    private static final int SIDEBAR_MIN_WIDTH = 50;
    private static final int SIDEBAR_MAX_WIDTH = 200;

    private static final String IMG_NOT_SET = "images/img_not_set.png";
    private static final String IMG_REFERENCE_NOT_SET = "images/img_ref_not_set.png";

    // main panels
    private JSplitPane verticalSplitPane;
    private JSplitPane horizontalSplitPane;
    private JPanel sidebar;
    private JPanel photosPanel;
    private JPanel exifDataPanel;
    
    // sidebar
    private JButton toggleSidebarButton;

    // photo panel
    private PhotoTableModel photoTableModel;

    // exif panel
    private PhotoExifDataPanel photoExifDataPanel;

    private JLabel imageLabel;
    private JLabel referenceLabel;
    private JButton openReferenceButton;
    private JButton adjustReferenceButton;
    private JButton removeReferenceButton;

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

        GuiUtilities.setImageToLabel(imageLabel, photo.getFile(), 240, 240);
    }

    private File loadImageFromResources(String path) throws IOException {
        try (InputStream inputStream
                = MainFrame.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + path);
            }

            // Create a temporary file
            Path tempFile = Files.createTempFile("tempFile", ".tmp");
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile.toFile();
        }
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

    private void initMenu() {
        setJMenuBar(new Menu(photoTableModel));
    }

    private void initMainPanels() {

        // create main panels for displaying photos and exif data: =
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        photosPanel = new JPanel(new BorderLayout());
        exifDataPanel = new JPanel(new BorderLayout());
        verticalSplitPane.setTopComponent(photosPanel);
        verticalSplitPane.setBottomComponent(exifDataPanel);
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

        photosPanel.add(searchPanel, BorderLayout.NORTH);
        photosPanel.add(tableScrollPane, BorderLayout.CENTER);

        // drag and drop photos to table
        new DropTarget(photosPanel, new PhotoListDropTargetListener(photoTableModel));
    }

    private void initExifDataPanel() {

        photoExifDataPanel = new PhotoExifDataPanel();
        JScrollPane dataScrollPane = new JScrollPane(photoExifDataPanel);

        // images (original and reference if exists)
        JPanel imageViewPanel = new JPanel();
        imageViewPanel.setLayout(new BoxLayout(imageViewPanel, BoxLayout.Y_AXIS));

        imageViewPanel.add(Box.createVerticalStrut(10));

        imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            GuiUtilities.setImageToLabel(imageLabel, loadImageFromResources(IMG_NOT_SET), 240, 240);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        imageViewPanel.add(imageLabel);

        imageViewPanel.add(Box.createVerticalStrut(10));

        imageViewPanel.add(new JLabel("Reference Photo"));

        referenceLabel = new JLabel();
        referenceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            GuiUtilities.setImageToLabel(referenceLabel, loadImageFromResources(IMG_REFERENCE_NOT_SET), 240, 240);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        // drag and drop photos to table
        new DropTarget(referenceLabel, new ReferencePhotoDropTargetListener(referenceLabel));
        imageViewPanel.add(referenceLabel);

        JPanel manageReferencePanel = new JPanel();
        manageReferencePanel.setLayout(new BoxLayout(manageReferencePanel, BoxLayout.X_AXIS));
        openReferenceButton = new JButton("O");
        adjustReferenceButton = new JButton("E");
        removeReferenceButton = new JButton("R");
        manageReferencePanel.add(openReferenceButton);
        manageReferencePanel.add(adjustReferenceButton);
        manageReferencePanel.add(removeReferenceButton);
        imageViewPanel.add(manageReferencePanel);

        imageViewPanel.setPreferredSize(new Dimension(250, 0));
        
        JScrollPane imageViewScrollPane = new JScrollPane(imageViewPanel);

        // add panels
        exifDataPanel.add(dataScrollPane, BorderLayout.CENTER);
        exifDataPanel.add(imageViewScrollPane, BorderLayout.EAST);
    }
}
