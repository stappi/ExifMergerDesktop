/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

/**
 *
 * @author Michael Stappert
 */
public class Menu extends JMenuBar {

    private final MainFrame mainFrame;

    private JMenuItem exitMenuItem;

    private JMenuItem openPhotosMenuItem;
    private JMenuItem openFolderMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveCopyMenuItem;
    private JMenuItem clearPhotoTableMenuItem;

    private JMenuItem loadMenuItem;
    private JMenuItem removeMenuItem;

    private JMenuItem globalExifDataMenuItem;
    private JMenuItem mergePriorizationMenuItem;

    private JMenuItem resetViewMenuItem;
    private JMenuItem helpMenuItem;
    private JMenuItem aboutMenuItem;

    public Menu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initFileMenu();
        initPhotosMenu();
        initPhotoReferenceMenu();
        initSettingsMenu();
        initHelpMenu();
    }

    // listeners ===============================================================
    private void openPhotosMenuItemActionPerformed() {
        List<Photo> newPhotos = Photo.loadPhotos(GuiUtilities.showPhotosChooser(
                this, mainFrame.getPhotoTableModel().getLastAddedPhotoFile()));
        mainFrame.getPhotoTableModel().addPhotos(newPhotos);
    }

    private void openFolderMenuItemActionPerformed() {
        File directory = GuiUtilities.showDirectoryChooser(
                this, mainFrame.getPhotoTableModel().getLastAddedPhotoFile());
        List<Photo> newPhotos = Photo.loadPhotosFromDir(directory);
        mainFrame.getPhotoTableModel().addPhotos(newPhotos);
    }

    private void saveMenuItemActionPerformed() {
        System.out.println("Menu Item Save");
    }

    private void saveCopyMenuItemActionPerformed() {
        System.out.println("Menu Item Save Copy");
    }

    private void clearPhotoTableMenuItemActionPerformed() {
        mainFrame.getPhotoTableModel().clear();
    }

    private void exitMenuItemActionPerformed() {
        System.exit(0);
    }

    private void loadMenuItemActionPerformed() {
        System.out.println("Menu Item Load Photo Reference");
    }

    private void removeMenuItemActionPerformed() {
        System.out.println("Menu Item Remove Photo Reference");
    }

    private void globalExifDataMenuItemActionPerformed() {
        mainFrame.getHorizontalSplitPane().setRightComponent(
                mainFrame.getGeneralExifDataPanel());
        mainFrame.getSidebar().showButtonsForSettingsGeneralExifData();
    }

    private void mergePriorizationMenuItemActionPerformed() {
        mainFrame.getHorizontalSplitPane().setRightComponent(
                mainFrame.getMergePriorizationPanel());
    }

    private void resetViewMenuItemActionPerformed() {
        System.out.println("Menu Item Reset View");
    }

    private void helpMenuItemActionPerformed() {
        System.out.println("Menu Item Global Help");
    }

    private void aboutMenuItemActionPerformed() {
        System.out.println("Menu Item Global About");
    }

    // init ====================================================================
    private void initFileMenu() {

        JMenu fileMenu = new JMenu();
        fileMenu.setText("File");

        exitMenuItem = new JMenuItem();
        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener((ActionEvent evt) -> {
            exitMenuItemActionPerformed();
        });
        fileMenu.add(exitMenuItem);

        add(fileMenu);
    }

    private void initPhotosMenu() {

        JMenu photosMenu = new JMenu();
        photosMenu.setText("Photos");

        openPhotosMenuItem = new JMenuItem();
        openPhotosMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        openPhotosMenuItem.setMnemonic('o');
        openPhotosMenuItem.setText("Open Photos");
        openPhotosMenuItem.addActionListener((ActionEvent evt) -> {
            openPhotosMenuItemActionPerformed();
        });
        photosMenu.add(openPhotosMenuItem);

        openFolderMenuItem = new JMenuItem();
        openFolderMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        openFolderMenuItem.setText("Open Folder With Photos");
        openFolderMenuItem.addActionListener((ActionEvent evt) -> {
            openFolderMenuItemActionPerformed();
        });
        photosMenu.add(openFolderMenuItem);

        photosMenu.add(new JSeparator());

        saveMenuItem = new JMenuItem();
        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener((ActionEvent evt) -> {
            saveMenuItemActionPerformed();
        });
        photosMenu.add(saveMenuItem);

        saveCopyMenuItem = new JMenuItem();
        saveCopyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        saveCopyMenuItem.setText("Save Copy");
        saveCopyMenuItem.addActionListener((ActionEvent evt) -> {
            saveCopyMenuItemActionPerformed();
        });
        photosMenu.add(saveCopyMenuItem);

        photosMenu.add(new JSeparator());

        clearPhotoTableMenuItem = new JMenuItem();
        clearPhotoTableMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        clearPhotoTableMenuItem.setText("Clear photo list");
        clearPhotoTableMenuItem.addActionListener((ActionEvent evt) -> {
            clearPhotoTableMenuItemActionPerformed();
        });
        photosMenu.add(clearPhotoTableMenuItem);

        add(photosMenu);
    }

    private void initPhotoReferenceMenu() {

        JMenu photoReferenceMenu = new JMenu();
        photoReferenceMenu.setText("Reference");

        loadMenuItem = new JMenuItem();
        loadMenuItem.setText("Load Photo Reference");
        loadMenuItem.addActionListener((ActionEvent evt) -> {
            loadMenuItemActionPerformed();
        });
        photoReferenceMenu.add(loadMenuItem);

        removeMenuItem = new JMenuItem();
        removeMenuItem.setText("Remove Photo Reference");
        removeMenuItem.addActionListener((ActionEvent evt) -> {
            removeMenuItemActionPerformed();
        });
        photoReferenceMenu.add(removeMenuItem);

        add(photoReferenceMenu);
    }

    private void initSettingsMenu() {

        JMenu settingsMenu = new JMenu();
        settingsMenu.setText("Settings");

        globalExifDataMenuItem = new JMenuItem();
        globalExifDataMenuItem.setText("Global Exif Data Settings");
        globalExifDataMenuItem.addActionListener((ActionEvent evt) -> {
            globalExifDataMenuItemActionPerformed();
        });
        settingsMenu.add(globalExifDataMenuItem);

        mergePriorizationMenuItem = new JMenuItem();
        mergePriorizationMenuItem.setText("Exif Merge Priorization");
        mergePriorizationMenuItem.addActionListener((ActionEvent evt) -> {
            mergePriorizationMenuItemActionPerformed();
        });
        settingsMenu.add(mergePriorizationMenuItem);

        add(settingsMenu);
    }

    private void initHelpMenu() {

        JMenu helpMenu = new JMenu();
        helpMenu.setText("Help");

        resetViewMenuItem = new JMenuItem();
        resetViewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        resetViewMenuItem.setText("Reset View");
        resetViewMenuItem.addActionListener((ActionEvent evt) -> {
            resetViewMenuItemActionPerformed();
        });
        helpMenu.add(resetViewMenuItem);

        helpMenuItem = new JMenuItem();
        helpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpMenuItem.setText("Help");
        helpMenuItem.addActionListener((ActionEvent evt) -> {
            helpMenuItemActionPerformed();
        });
        helpMenu.add(helpMenuItem);

        aboutMenuItem = new JMenuItem();
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener((ActionEvent evt) -> {
            aboutMenuItemActionPerformed();
        });
        helpMenu.add(aboutMenuItem);

        add(helpMenu);
    }

}
