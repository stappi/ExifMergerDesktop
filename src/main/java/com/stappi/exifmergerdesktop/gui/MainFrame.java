package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import com.stappi.exifmergerdesktop.utilities.ImageUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int MAIN_FRAME_WIDTH = 1000;
    private static final int MAIN_FRAME_HEIGHT = 600;
    private static final int SIDEBAR_MIN_WIDTH = 50;
    private static final int SIDEBAR_MAX_WIDTH = 200;

    // main panels
    private JSplitPane verticalSplitPane;
    private JSplitPane horizontalSplitPane;
    private JPanel sidebar;
    private JPanel photosPanel;
    private JPanel exifDataPanel;

    // menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    private JMenu photosMenu;
    private JMenuItem openPhotosMenuItem;
    private JMenuItem openFolderMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveCopyMenuItem;
    private JMenuItem clearPhotoTableMenuItem;    
    private JMenu photoReferenceMenu;
    private JMenuItem loadMenuItem;
    private JMenuItem removeMenuItem;
    private JMenu settingsMenu;
    private JMenuItem globalExifDataMenuItem;
    private JMenuItem mergePriorizationMenuItem;
    private JMenu helpMenu;
    private JMenuItem helpMenuItem;
    private JMenuItem aboutMenuItem;

    // sidebar
    private JButton toggleSidebarButton;

    // photo panel
    private JTable photosTable;
    private PhotoTableModel photoTableModel;

    // =========================================================================
    public MainFrame() {
        initMainFrame();
        initMenu();
        initMainPanels();
        initSideBar();
        initPhotosPanel();
    }

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

    private void openPhotosMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Open Photo");

//        try {
        List<Photo> newPhotos = GuiUtilities.showPhotosChooser(
                this, photoTableModel.getLastAddedPhoto() != null ? photoTableModel.getLastAddedPhoto().getFile() : null)
                .stream()
                .map(file -> Photo.builder().file(file).build())
                .collect(Collectors.toList());
        photoTableModel.addPhotos(newPhotos);

        // remove all ready existing images
//            for (File file : newPhotos) {
//                // Grafik für das skalierte Bild erstellen
//                BufferedImage bufferedScaledImage = ImageUtilities.loadImage(file, 172, 172);
//                photosPanel.add(Box.createVerticalStrut(10));
//                photosPanel.setLayout(new BoxLayout(photosPanel, BoxLayout.Y_AXIS));
//                // JLabel mit dem skalierten Bild setzen
//
//                JLabel label = new JLabel(new ImageIcon(bufferedScaledImage));
//                label.setAlignmentX(Component.CENTER_ALIGNMENT);
//                photosPanel.add(label);
//                photosPanel.add(Box.createVerticalStrut(10));
//            }
//            photos.addAll(newPhotos);
//
//            // revalidate and redraw panel
//            photosPanel.revalidate();
//            photosPanel.repaint();
//        } catch (IOException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void openFolderMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Open Folder");

//        try {
        File directory = GuiUtilities.showDirectoryChooser(this,
                photoTableModel.getLastAddedPhoto() != null ? photoTableModel.getLastAddedPhoto().getFile() : null);
        List<Photo> newPhotos = directory != null
                ? Arrays.asList(directory.listFiles((File file)
                        -> file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")))
                        .stream()
                        .map(file -> Photo.builder().file(file).build())
                        .collect(Collectors.toList())
                : new ArrayList<>();
        photoTableModel.addPhotos(newPhotos);

//            // remove all ready existing images
//            for (File file : newPhotos) {
//                // Grafik für das skalierte Bild erstellen
//                BufferedImage bufferedScaledImage = ImageUtilities.loadImage(file, 172, 172);
//                photosPanel.add(Box.createVerticalStrut(10));
//                photosPanel.setLayout(new BoxLayout(photosPanel, BoxLayout.Y_AXIS));
//                // JLabel mit dem skalierten Bild setzen
//
//                JLabel label = new JLabel(new ImageIcon(bufferedScaledImage));
//                label.setAlignmentX(Component.CENTER_ALIGNMENT);
//                photosPanel.add(label);
//                photosPanel.add(Box.createVerticalStrut(10));
//            }
//            photos.addAll(newPhotos);
//
//            // revalidate and redraw panel
//            photosPanel.revalidate();
//            photosPanel.repaint();
//        } catch (IOException ex) {
//            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    private void saveMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Save");
    }

    private void saveCopyMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Save Copy");
    }
    
    private void clearPhotoTableMenuItemActionPerformed(ActionEvent evt) {
        photoTableModel.clear();
    }

    private void exitMenuItemActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void loadMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Load Photo Reference");
    }

    private void removeMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Remove Photo Reference");
    }

    private void globalExifDataMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Global Exif Data Settings");
    }

    private void helpMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Global Help");
    }

    private void aboutMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Global About");
    }

    // =========================================================================
    private void initMainFrame() {

        setTitle("Exif Merger Desktop");
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Zentriert das Fenster auf dem Bildschirm
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu();
        exitMenuItem = new JMenuItem();
        photosMenu = new JMenu();
        openPhotosMenuItem = new JMenuItem();
        openFolderMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        saveCopyMenuItem = new JMenuItem();
        clearPhotoTableMenuItem = new JMenuItem();
        photoReferenceMenu = new JMenu();
        loadMenuItem = new JMenuItem();
        removeMenuItem = new JMenuItem();
        settingsMenu = new JMenu();
        globalExifDataMenuItem = new JMenuItem();
        mergePriorizationMenuItem = new JMenuItem();
        helpMenu = new JMenu();
        helpMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();

        // file menu        
        fileMenu.setText("File");

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener((ActionEvent evt) -> {
            exitMenuItemActionPerformed(evt);
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        photosMenu.setText("Photos");

        openPhotosMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openPhotosMenuItem.setMnemonic('o');
        openPhotosMenuItem.setText("Open Photos");
        openPhotosMenuItem.addActionListener((ActionEvent evt) -> {
            openPhotosMenuItemActionPerformed(evt);
        });
        photosMenu.add(openPhotosMenuItem);

        openFolderMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        openFolderMenuItem.setText("Open Folder With Photos");
        openFolderMenuItem.addActionListener((ActionEvent evt) -> {
            openFolderMenuItemActionPerformed(evt);
        });
        photosMenu.add(openFolderMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener((ActionEvent evt) -> {
            saveMenuItemActionPerformed(evt);
        });
        photosMenu.add(saveMenuItem);

        saveCopyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        saveCopyMenuItem.setText("Save Copy");
        saveCopyMenuItem.addActionListener((ActionEvent evt) -> {
            saveCopyMenuItemActionPerformed(evt);
        });
        photosMenu.add(saveCopyMenuItem);

        clearPhotoTableMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        clearPhotoTableMenuItem.setText("Remove photo list");
        clearPhotoTableMenuItem.addActionListener((ActionEvent evt) -> {
            clearPhotoTableMenuItemActionPerformed(evt);
        });
        photosMenu.add(clearPhotoTableMenuItem);

        menuBar.add(photosMenu);

        // reference menu
        photoReferenceMenu.setText("Reference");

        loadMenuItem.setText("Load Photo Reference");
        loadMenuItem.addActionListener((ActionEvent evt) -> {
            loadMenuItemActionPerformed(evt);
        });
        photoReferenceMenu.add(loadMenuItem);

        removeMenuItem.setText("Remove Photo Reference");
        removeMenuItem.addActionListener((ActionEvent evt) -> {
            removeMenuItemActionPerformed(evt);
        });
        photoReferenceMenu.add(removeMenuItem);

        menuBar.add(photoReferenceMenu);

        // settings menu
        settingsMenu.setText("Settings");

        globalExifDataMenuItem.setText("Global Exif Data Settings");
        globalExifDataMenuItem.addActionListener((ActionEvent evt) -> {
            globalExifDataMenuItemActionPerformed(evt);
        });
        settingsMenu.add(globalExifDataMenuItem);

        mergePriorizationMenuItem.setText("Exif Merge Priorization");
        settingsMenu.add(mergePriorizationMenuItem);

        menuBar.add(settingsMenu);

        // help menu
        helpMenu.setText("Help");
        helpMenu.setToolTipText("");

        helpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpMenuItem.setText("Help");
        helpMenuItem.addActionListener((ActionEvent evt) -> {
            helpMenuItemActionPerformed(evt);
        });
        helpMenu.add(helpMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener((ActionEvent evt) -> {
            aboutMenuItemActionPerformed(evt);
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        // set menu bar
        setJMenuBar(menuBar);
    }

    private void initMainPanels() {

        // create main panels for displaying photos and exif data: =
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        photosPanel = new JPanel(new BorderLayout());
        exifDataPanel = new JPanel();
        verticalSplitPane.setTopComponent(photosPanel);
        verticalSplitPane.setBottomComponent(exifDataPanel);
        verticalSplitPane.setResizeWeight(1.0);

        // create sidebar: |=
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sidebar = new JPanel();
        sidebar.setMinimumSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        sidebar.setPreferredSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        horizontalSplitPane.setLeftComponent(sidebar);
        horizontalSplitPane.setRightComponent(verticalSplitPane);

        // set default position
        horizontalSplitPane.setDividerLocation(200);
        verticalSplitPane.setDividerLocation(300);

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
        searchPanel.add(selectAllButton);
        searchPanel.add(deselectAllButton);

        // Erstellen der Tabelle
        photoTableModel = new PhotoTableModel();
        photosTable = new JTable(photoTableModel);
        JScrollPane tableScrollPane = new JScrollPane(photosTable);

        photosPanel.add(searchPanel, BorderLayout.NORTH);
        photosPanel.add(tableScrollPane, BorderLayout.CENTER);
    }
}
