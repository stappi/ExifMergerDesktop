/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stappi.exifmergerdesktop.utilities;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author micha
 */
public final class GuiUtilities {
    
    
    public static List<File> showPhotosChooser(Component parent, File currentDir) {
        
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setCurrentDirectory(currentDir);
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);
        
        return fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION
                ? new ArrayList<>(Arrays.asList(fileChooser.getSelectedFiles()))
                : new ArrayList<>();
    }
    
    public static File showDirectoryChooser(Component parent, File currentDir) {
        
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setCurrentDirectory(currentDir);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        
        return fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION
                ? fileChooser.getSelectedFile()
                : null;
    }
    
    public static void addRowToGrid(JPanel panel, String labelText, 
            JComponent component, GridBagConstraints constraints) {
        
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 0;
        constraints.weighty = 0;

        panel.add(new JLabel(labelText), constraints);

        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, constraints);
    }
    
    public static void setImageToLabel(JLabel label, File imgFile, 
            int maxWidth, int maxHeight) throws IOException {
        BufferedImage bufferedScaledImage = ImageUtilities.loadImage(imgFile, maxWidth, maxHeight);
        label.setIcon(new ImageIcon(bufferedScaledImage));
    }
}
