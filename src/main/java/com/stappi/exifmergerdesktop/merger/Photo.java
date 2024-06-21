/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.merger;

import java.io.File;
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
    
    @Getter
    private final File file;

    @Override
    public int compareTo(Photo photo) {
        return this.file.getName().compareTo(photo.getFile().getName());
    }
}
