/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.stappi.exifmergerdesktop;

import com.stappi.exifmergerdesktop.gui.MainFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Michael Stappert
 */
public class ExifMergerDesktop {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
             new MainFrame().setVisible(true);
        });
    }
}
