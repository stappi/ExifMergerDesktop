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
    private PhotoTableModel photoTableModel;

    // exif panel
    private JTextField fileNameTextField;
    private JLabel directoryLabel;
    private JLabel fileTypeLabel;
    private JLabel fileLengthLabel;
    private JLabel lastModifiedLabel;
    private JLabel changeDateLabel;

    private JComboBox titleComboBox;
    private JComboBox subjectComboBox;
    private JComboBox ratingComboBox;
    private JComboBox markingComboBox;
    private JComboBox commentsComboBox;

    private JComboBox authorsComboBox;
    private JComboBox recordingDateComboBox;
    private JComboBox softwareNameComboBox;
    private JComboBox entryDateComboBox;
    private JComboBox copyRightComboBox;

    private JLabel imageLabel;
    private JLabel referenceLabel;
    private JButton openReferenceButton;
    private JButton adjustReferenceButton;
    private JButton removeReferenceButton;

    // =========================================================================
    public MainFrame() {
        initMainFrame();
        initMenu();
        initMainPanels();
        initSideBar();
        initPhotosPanel();
        initExifDataPanel();
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

    private void openPhotosMenuItemActionPerformed(ActionEvent evt) {
        System.out.println("Menu Item Open Photo");

//        try {
        List<Photo> newPhotos = Photo.loadPhotos(GuiUtilities.showPhotosChooser(this, getLastAddedPhotoFile()));
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
        File directory = GuiUtilities.showDirectoryChooser(this, getLastAddedPhotoFile());
        List<Photo> newPhotos = Photo.loadPhotosFromDir(directory);
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

    private void setExifDataForSelectedPhoto(Photo photo) throws IOException {

        fileNameTextField.setText(photo.getFile().getName());
        directoryLabel.setText(photo.getFile().getParent());
        fileTypeLabel.setText(FileUtilities.getExtension(photo.getFile()).orElse("?"));
        fileLengthLabel.setText(photo.getLength());
        lastModifiedLabel.setText(photo.getLastModified());
        changeDateLabel.setText(photo.getCreationTime());

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

        photosMenu.add(new JSeparator());

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

        photosMenu.add(new JSeparator());

        clearPhotoTableMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        clearPhotoTableMenuItem.setText("Clear photo list");
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
        verticalSplitPane.setDividerLocation(getHeight() - 475);

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
        // Adding DropTarget to the panel
        new DropTarget(photosPanel, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                // Optional: Handle drag enter event
            }

            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                // Optional: Handle drag over event
            }

            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
                // Optional: Handle drop action changed event
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                // Optional: Handle drag exit event
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    // Accept the drop
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    // Get the dropped files
                    Transferable transferable = dtde.getTransferable();
                    DataFlavor[] flavors = transferable.getTransferDataFlavors();

                    for (DataFlavor flavor : flavors) {
                        if (flavor.isFlavorJavaFileListType()) {
                            List<Photo> newPhotos = ((List<File>) transferable.getTransferData(flavor))
                                    .stream().map(file -> file.isDirectory()
                                    ? Photo.loadPhotosFromDir(file)
                                    : Photo.loadPhotos(file))
                                    .flatMap(stream -> stream.stream())
                                    .collect(Collectors.toList());
                            photoTableModel.addPhotos(newPhotos);
                            break;
                        }
                    }

                    // Complete the drop
                    dtde.dropComplete(true);
                } catch (UnsupportedFlavorException | IOException ex) {
                    dtde.dropComplete(false);
                }
            }
        });
    }

    private void initExifDataPanel() {

        JPanel dataPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        int currentRow = 0;
        currentRow = initExifDataPanelAddFileInfos(dataPanel, constraints, currentRow);
        currentRow = initExifDataPanelAddDescriptionInfos(dataPanel, constraints, currentRow);
        initExifDataPanelAddSourceInfos(dataPanel, constraints, currentRow);

        // Platzhalter-Komponente hinzufügen, um das Grid nach oben links zu schieben
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 3; // Reicht über alle Spalten
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        dataPanel.add(new JPanel(), constraints);

        JScrollPane dataScrollPane = new JScrollPane(dataPanel);

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

        // add panels
        exifDataPanel.add(dataScrollPane, BorderLayout.CENTER);
        exifDataPanel.add(imageViewPanel, BorderLayout.EAST);
    }

    private int initExifDataPanelAddFileInfos(JPanel parentPanel,
            GridBagConstraints constraints, int row) {

        fileNameTextField = new JTextField();
        directoryLabel = new JLabel();
        fileTypeLabel = new JLabel();
        fileLengthLabel = new JLabel();
        lastModifiedLabel = new JLabel();
        changeDateLabel = new JLabel();

        GuiUtilities.addCaptionRowToGrid(parentPanel, "File Info", constraints, row++);

        GuiUtilities.addRowToGrid(parentPanel, "Filename:", fileNameTextField, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Directory:", directoryLabel, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Filetype:", fileTypeLabel, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Length:", fileLengthLabel, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Last Modified:", lastModifiedLabel, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Creation Time:", changeDateLabel, constraints, row++);

        return row;
    }

    private int initExifDataPanelAddDescriptionInfos(JPanel parentPanel, 
            GridBagConstraints constraints, int row) {

        titleComboBox = new JComboBox();
        subjectComboBox = new JComboBox();
        ratingComboBox = new JComboBox();
        markingComboBox = new JComboBox();
        commentsComboBox = new JComboBox();
        
        GuiUtilities.addCaptionRowToGrid(parentPanel, "Description", constraints, row++);

        GuiUtilities.addRowToGrid(parentPanel, "Title:", titleComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Subject:", subjectComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Rating:", ratingComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Marking:", markingComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Comments:", commentsComboBox, constraints, row++);

        return row;
    }

    private int initExifDataPanelAddSourceInfos(JPanel parentPanel,
            GridBagConstraints constraints, int row) {

        authorsComboBox = new JComboBox();
        recordingDateComboBox = new JComboBox();
        softwareNameComboBox = new JComboBox();
        entryDateComboBox = new JComboBox();
        copyRightComboBox = new JComboBox();
        
        GuiUtilities.addCaptionRowToGrid(parentPanel, "Source", constraints, row++);

        // Methode zum Hinzufügen einer Zeile mit Label, Textfeld und optionalem Button
        GuiUtilities.addRowToGrid(parentPanel, "Authors:", authorsComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Recording Date:", recordingDateComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Software:", softwareNameComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Entry Date:", entryDateComboBox, constraints, row++);
        GuiUtilities.addRowToGrid(parentPanel, "Copyright:", copyRightComboBox, constraints, row++);
        
        return row;
    }
}
