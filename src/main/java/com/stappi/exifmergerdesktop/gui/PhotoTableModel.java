/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.FileUtilities;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.table.DefaultTableModel;
import lombok.Getter;

/**
 *
 * @author Michael Stappert
 */
public class PhotoTableModel extends DefaultTableModel {

    private static final SimpleDateFormat DATE_TIME_FORMAT = 
            new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");

    private static final long BYTE_TO_MB = 1024L * 1024;
    
    private static final String[] COLUMN_NAMES = new String[]{
        "File", "Extension", "Letzte Änderung", "Aufnahmedatum", "Size"
    };

    private final SortedSet<Photo> photos = new TreeSet<>();
    
    @Getter
    private Photo lastAddedPhoto;

    public PhotoTableModel() {
        setDataVector(new Object[][]{}, COLUMN_NAMES);
    }

    public PhotoTableModel(List<Photo> photos) {
        setPhotos(photos);
    }

    public void addPhotos(List<Photo> newPhotos) {
        setPhotos(newPhotos);
        fireTableDataChanged();  // informs table, that list of photos has changed
    }

    public void clear() {
        this.photos.clear();
        setPhotos(new ArrayList<>());
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    private void setPhotos(List<Photo> newPhotos) {
        this.photos.addAll(newPhotos);
        this.lastAddedPhoto = !newPhotos.isEmpty() 
                ? newPhotos.get(newPhotos.size() - 1) : null;
        setDataVector(photos.stream()
                .map(photo -> new Object[]{
            photo.getFile().getName(),
            FileUtilities.getExtension(photo.getFile()).orElse("?"),
            DATE_TIME_FORMAT.format(new Date(photo.getFile().lastModified())),
            "Aufnahmedatum",
            byteToMb(photo.getFile().length())
        }).toArray(Object[][]::new), COLUMN_NAMES);
    }
    
    private String byteToMb(long bytes) {
        return Math.round(bytes * 100.0 / BYTE_TO_MB) / 100.0 + " MB";                
    }
}
