/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.FileUtilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 * @author Michael Stappert
 */
public class PhotoTableModel extends DefaultTableModel {

    private static final String[] COLUMN_NAMES = new String[]{
            "File", "Extension", "Last updated", "Size"
    };

    private List<Photo> photos;
    private List<Photo> filteredPhotos;

    private Photo lastAddedPhoto;

    public PhotoTableModel() {
        setDataVector(new Object[][]{}, COLUMN_NAMES);
    }

    public void filter(String filter) {
        filteredPhotos = photos.stream()
                               .filter(photo -> photo.getFile().getName().toLowerCase().contains(filter.toLowerCase()))
                               .collect(Collectors.toList());
        setTableData();
    }

    public File getLastAddedPhotoFile() {
        return Optional.ofNullable(lastAddedPhoto)
                       .map(Photo::getFile)
                       .orElse(null);
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

    public Photo getPhotoAt(int index) {
        if (index < 0 || index >= this.filteredPhotos.size()) {
            return null;
        }
        return this.filteredPhotos.get(index);
    }

    @Override
    public void removeRow(int row) {
        super.removeRow(row);
        this.photos.remove(this.filteredPhotos.remove(row));
        this.filteredPhotos.remove(row);
    }

    private void setPhotos(List<Photo> newPhotos) {

        this.lastAddedPhoto = !newPhotos.isEmpty() ? newPhotos.get(newPhotos.size() - 1) : null;

        Set<Photo> uniquePhotos = new HashSet(Optional.ofNullable(this.photos).orElse(new ArrayList()));
        uniquePhotos.addAll(Optional.ofNullable(newPhotos).orElse(new ArrayList()));
        this.photos = uniquePhotos.stream().collect(Collectors.toList());

        this.filteredPhotos = photos;
        setTableData();
    }

    private void setTableData() {
        setDataVector(filteredPhotos.stream().map(photo -> new Object[]{
                photo.getFile().getName(),
                FileUtilities.getExtension(photo.getFile()).orElse("?"),
                photo.getLastModified(),
                photo.getLength()
        }).toArray(Object[][]::new), COLUMN_NAMES);
    }
}
