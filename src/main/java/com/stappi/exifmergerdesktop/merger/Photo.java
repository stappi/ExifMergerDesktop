/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.merger;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Michael Stappert
 */
@Builder
@ToString
public class Photo implements Comparable<Photo> {

    private static final FileFilter ACCEPTED_FILES = (File file1) 
            -> file1.getName().endsWith(".jpg") || file1.getName().endsWith(".jpeg");
    
  
    @Getter
    private final File file;

    @Override
    public int compareTo(Photo photo) {
        return this.file.getAbsolutePath().compareTo(photo.getFile().getAbsolutePath());
    }

    
    public static List<Photo> loadPhotosFromDir(File directory) {
        
        return directory != null && directory.isDirectory()
                ? Arrays.asList(directory.listFiles(ACCEPTED_FILES))
                        .stream()
                        .map(file -> Photo.builder().file(file).build())
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }
    
    public static List<Photo> loadPhotos(File... files) {
        
        return loadPhotos(Arrays.asList(files));
    }

    public static List<Photo> loadPhotos(Collection<File> files) {
        
        return files
                .stream()
                .filter(file -> ACCEPTED_FILES.accept(file))
                .map(file -> Photo.builder().file(file).build())
                .collect(Collectors.toList());
    }
}
