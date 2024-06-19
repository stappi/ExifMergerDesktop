/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

/**
 *
 * @author Michael Stappert
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        photosPanel = new javax.swing.JPanel();
        exifDataPanel = new javax.swing.JPanel();
        workPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openPhotoMenuItem = new javax.swing.JMenuItem();
        openFolderMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveCopyMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        photoReferenceMenu = new javax.swing.JMenu();
        loadMenuItem = new javax.swing.JMenuItem();
        removeMenuItem = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        globalExifDataMenuItem = new javax.swing.JMenuItem();
        sessionExifDataMenuItem = new javax.swing.JMenuItem();
        mergePriorizationMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        photosPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        photosPanel.setPreferredSize(new java.awt.Dimension(200, 4));

        javax.swing.GroupLayout photosPanelLayout = new javax.swing.GroupLayout(photosPanel);
        photosPanel.setLayout(photosPanelLayout);
        photosPanelLayout.setHorizontalGroup(
            photosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );
        photosPanelLayout.setVerticalGroup(
            photosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        exifDataPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        exifDataPanel.setName(""); // NOI18N
        exifDataPanel.setPreferredSize(new java.awt.Dimension(500, 4));

        javax.swing.GroupLayout exifDataPanelLayout = new javax.swing.GroupLayout(exifDataPanel);
        exifDataPanel.setLayout(exifDataPanelLayout);
        exifDataPanelLayout.setHorizontalGroup(
            exifDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 513, Short.MAX_VALUE)
        );
        exifDataPanelLayout.setVerticalGroup(
            exifDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        workPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        workPanel.setPreferredSize(new java.awt.Dimension(200, 4));

        javax.swing.GroupLayout workPanelLayout = new javax.swing.GroupLayout(workPanel);
        workPanel.setLayout(workPanelLayout);
        workPanelLayout.setHorizontalGroup(
            workPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        workPanelLayout.setVerticalGroup(
            workPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );

        fileMenu.setText("File");

        openPhotoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openPhotoMenuItem.setMnemonic('o');
        openPhotoMenuItem.setText("Open Photo");
        openPhotoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPhotoMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openPhotoMenuItem);

        openFolderMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        openFolderMenuItem.setText("Open Folder");
        openFolderMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFolderMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openFolderMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveCopyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveCopyMenuItem.setText("Save Copy");
        saveCopyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveCopyMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveCopyMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_DOWN_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        photoReferenceMenu.setText("Photo Reference");

        loadMenuItem.setText("Load Reference");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        photoReferenceMenu.add(loadMenuItem);

        removeMenuItem.setText("Remove Reference");
        removeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMenuItemActionPerformed(evt);
            }
        });
        photoReferenceMenu.add(removeMenuItem);

        menuBar.add(photoReferenceMenu);

        settingsMenu.setText("Settings");

        globalExifDataMenuItem.setText("Global Exif Data Settings");
        globalExifDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                globalExifDataMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(globalExifDataMenuItem);

        sessionExifDataMenuItem.setText("Session Based Exif Data Settings");
        sessionExifDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionExifDataMenuItemActionPerformed(evt);
            }
        });
        settingsMenu.add(sessionExifDataMenuItem);

        mergePriorizationMenuItem.setText("Exif Merge Priorization");
        mergePriorizationMenuItem.setActionCommand("Exif Merge Priorization");
        settingsMenu.add(mergePriorizationMenuItem);

        menuBar.add(settingsMenu);

        helpMenu.setText("Help");
        helpMenu.setToolTipText("");

        helpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        helpMenuItem.setText("Help");
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(photosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exifDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(workPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(workPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addComponent(exifDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                    .addComponent(photosPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openPhotoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPhotoMenuItemActionPerformed
        System.out.println("Menu Item Open Photo");
    }//GEN-LAST:event_openPhotoMenuItemActionPerformed

    private void openFolderMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openFolderMenuItemActionPerformed
        System.out.println("Menu Item Open Folder");
    }//GEN-LAST:event_openFolderMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        System.out.println("Menu Item Save");
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveCopyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveCopyMenuItemActionPerformed
        System.out.println("Menu Item Save Copy");
    }//GEN-LAST:event_saveCopyMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        System.out.println("Menu Item Load Photo Reference");
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void removeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMenuItemActionPerformed
        System.out.println("Menu Item Remove Photo Reference");
    }//GEN-LAST:event_removeMenuItemActionPerformed

    private void globalExifDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_globalExifDataMenuItemActionPerformed
        System.out.println("Menu Item Global Exif Data Settings");
    }//GEN-LAST:event_globalExifDataMenuItemActionPerformed

    private void sessionExifDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionExifDataMenuItemActionPerformed
        System.out.println("Menu Item Session Exif Data Settings");
    }//GEN-LAST:event_sessionExifDataMenuItemActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        System.out.println("Menu Item Global Help");
    }//GEN-LAST:event_helpMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        System.out.println("Menu Item Global About");
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JPanel exifDataPanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem globalExifDataMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mergePriorizationMenuItem;
    private javax.swing.JMenuItem openFolderMenuItem;
    private javax.swing.JMenuItem openPhotoMenuItem;
    private javax.swing.JMenu photoReferenceMenu;
    private javax.swing.JPanel photosPanel;
    private javax.swing.JMenuItem removeMenuItem;
    private javax.swing.JMenuItem saveCopyMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem sessionExifDataMenuItem;
    private javax.swing.JMenu settingsMenu;
    private javax.swing.JPanel workPanel;
    // End of variables declaration//GEN-END:variables
}
