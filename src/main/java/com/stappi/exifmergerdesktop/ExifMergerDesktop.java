/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.stappi.exifmergerdesktop;

import com.stappi.exifmergerdesktop.gui.MainFrame;

/**
 *
 * @author Michael Stappert
 */
public class ExifMergerDesktop {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
