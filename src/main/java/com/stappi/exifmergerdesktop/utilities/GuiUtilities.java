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
    
    private static final int GRID_LAYOUT_WIDTH = 5;
    
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
    
    public static void addCaptionRowToGrid(JPanel panel, String labelText, 
            GridBagConstraints constraints, int row) {
        
        constraints.gridy = row;
        constraints.gridx = 0;
        constraints.gridwidth = GRID_LAYOUT_WIDTH;        
        JLabel captionLabel = new JLabel("File Info");
        panel.add(captionLabel, constraints);
        row++;
    }
    
    public static void addRowToGrid(JPanel panel, String labelText, 
            JComponent component, GridBagConstraints constraints, int row) {
        
        // add label
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 1;     
        JLabel label = new JLabel(labelText);
        panel.add(label, constraints);
        
        // add component
        constraints.gridx = 1;
        constraints.gridwidth = GRID_LAYOUT_WIDTH - 1; 
        panel.add(component, constraints);
    }
    
    public static void setImageToLabel(JLabel label, File imgFile, 
            int maxWidth, int maxHeight) throws IOException {
        BufferedImage bufferedScaledImage = ImageUtilities.loadImage(imgFile, maxWidth, maxHeight);
        label.setIcon(new ImageIcon(bufferedScaledImage));
    }
}
