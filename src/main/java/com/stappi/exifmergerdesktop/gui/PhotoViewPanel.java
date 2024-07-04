/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Michael Stappert
 */
public class PhotoViewPanel extends JPanel {

    private static final String IMG_NOT_SET = "images/img_not_set.png";
    private static final String IMG_REFERENCE_NOT_SET = "images/img_ref_not_set.png";
    
    private JLabel photoLabel;
    private JLabel referenceLabel;
    private JButton openReferenceButton;
    private JButton adjustReferenceButton;
    private JButton removeReferenceButton;

    public PhotoViewPanel() {
        setLayout(new BorderLayout());
        initComponents();
    }
    
    public void showPhotoOnView(Photo photo) throws IOException {
        
        GuiUtilities.setImageToLabel(photoLabel, photo.getFile(), 240, 240);
    }

    private void initComponents() {

        // Image
        JPanel photoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        photoLabel = new JLabel();
        try {
            GuiUtilities.setImageToLabel(photoLabel, loadImageFromResources(IMG_NOT_SET), 240, 240);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        photoPanel.add(photoLabel);
        add(photoPanel, BorderLayout.NORTH);

        // Reference
        JPanel referencePanel = new JPanel();
        referencePanel.setLayout(new BoxLayout(referencePanel, BoxLayout.Y_AXIS));

        // Reference title
        JPanel referenceTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel referenceTitleLabel = new JLabel("Reference Photo");
        referenceTitlePanel.add(referenceTitleLabel);
        referenceTitlePanel.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, referenceTitlePanel.getPreferredSize().height));
        referencePanel.add(referenceTitlePanel);
        
        // Reference photo
        JPanel referencePhotoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        referenceLabel = new JLabel();
        new DropTarget(referenceLabel, new DropReferencePhotoListener(referenceLabel));
        try {
            GuiUtilities.setImageToLabel(referenceLabel, loadImageFromResources(IMG_REFERENCE_NOT_SET), 240, 240);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        referencePhotoPanel.add(referenceLabel);  
        referencePhotoPanel.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, referencePhotoPanel.getPreferredSize().height));             
        referencePanel.add(referencePhotoPanel);

        // Reference buttons
        JPanel manageReferencePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        openReferenceButton = new JButton("O");
        adjustReferenceButton = new JButton("E");
        removeReferenceButton = new JButton("R");
        manageReferencePanel.add(openReferenceButton);
        manageReferencePanel.add(adjustReferenceButton);
        manageReferencePanel.add(removeReferenceButton);
        manageReferencePanel.setMaximumSize(new Dimension(
                Integer.MAX_VALUE, manageReferencePanel.getPreferredSize().height));
        referencePanel.add(manageReferencePanel);
        
        add(referencePanel, BorderLayout.CENTER);
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
}
