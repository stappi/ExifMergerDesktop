/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
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
import java.util.stream.Collectors;

/**
 *
 * @author micha
 */
public class DropPhotoListListener implements DropTargetListener {

    private final PhotoTableModel photoTableModel;

    public DropPhotoListListener(PhotoTableModel photoTableModel) {
        this.photoTableModel = photoTableModel;
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
                    List<Photo> newPhotos = ((List<File>) transferable.getTransferData(flavor))
                            .stream().map(file -> file.isDirectory()
                            ? Photo.loadPhotosFromDir(file)
                            : Photo.loadPhotos(file))
                            .flatMap(stream -> stream.stream())
                            .collect(Collectors.toList());
                    photoTableModel.addPhotos(newPhotos);
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
