/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author micha
 */
public class DropReferencePhotoListener implements DropTargetListener {

    private final JLabel referenceLabel;

    public DropReferencePhotoListener(JLabel referenceLabel) {
        this.referenceLabel = referenceLabel;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        // Optional: Handle drag enter event
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        // Optional: Handle drag over event
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        // Optional: Handle drop action changed event
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        // Optional: Handle drag exit event
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            // Accept the drop
            dtde.acceptDrop(DnDConstants.ACTION_COPY);

            // Get the dropped files
            Transferable transferable = dtde.getTransferable();
            DataFlavor[] flavors = transferable.getTransferDataFlavors();

            for (DataFlavor flavor : flavors) {
                if (flavor.isFlavorJavaFileListType()) {

                    ((List<File>) transferable.getTransferData(flavor))
                            .stream()
                            .findFirst()
                            .ifPresent(referencePhoto -> {
                                try {
                                    GuiUtilities.setImageToLabel(referenceLabel, referencePhoto, 240, 240);
                                    referenceLabel.getParent().setMaximumSize(new Dimension(
                                            Integer.MAX_VALUE, referenceLabel.getParent().getPreferredSize().height));
                                    //TODO set reference photo
                                } catch (IOException ex) {
                                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                    break;
                }
            }

            // Complete the drop
            dtde.dropComplete(true);
        } catch (UnsupportedFlavorException | IOException ex) {
            dtde.dropComplete(false);
        }
    }

}
