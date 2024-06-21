/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.utilities;

import java.io.File;
import java.util.Optional;

/**
 *
 * @author Michael Stappert
 */
public final class FileUtilities {

    public static Optional<String> getExtension(File file) {
        return Optional.ofNullable(file)
                .map(File::getName)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf(".") + 1));
    }
}
