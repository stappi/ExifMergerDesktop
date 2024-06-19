/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stappi.exifmergerdesktop.utilities;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
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
}
