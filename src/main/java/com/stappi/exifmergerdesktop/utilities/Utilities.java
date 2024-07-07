/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.utilities;

/**
 *
 * @author Michael Stappert
 */
public final class Utilities {
    
    public static boolean isEmpty(String value) {
        return value == null || value.replace(" ", "").isEmpty();
    }
}
