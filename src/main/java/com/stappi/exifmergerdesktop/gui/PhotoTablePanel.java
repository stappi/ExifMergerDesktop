package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Michael Stappert
 */
public class PhotoTablePanel extends JPanel {

    private final MainFrame mainFrame;

    private PhotoTableModel photoTableModel;

    public PhotoTablePanel(MainFrame mainFrame) {
        super(new BorderLayout());

        this.mainFrame = mainFrame;

        initComponents();
    }

    public PhotoTableModel getTableModel() {
        return photoTableModel;
    }

    private void initComponents() {
        // Hinzufügen des Suchfelds und der Buttons im oberen Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        JButton selectAllButton = new JButton("Select all");
        JButton deselectAllButton = new JButton("Deselect all");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
//        searchPanel.add(selectAllButton);
//        searchPanel.add(deselectAllButton);

        // Erstellen der Tabelle
        photoTableModel = new PhotoTableModel();
        JTable photosTable = new JTable(photoTableModel);
        photosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photosTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            if (!event.getValueIsAdjusting() && photosTable.getSelectedRow() != -1) {
                try {
                    setExifDataForSelectedPhoto(photoTableModel.getPhotoAt(photosTable.getSelectedRow()));
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(photosTable);

        // context menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem printExifInfoMenuItem = new JMenuItem("Print Exif Info");
        printExifInfoMenuItem.addActionListener(event -> {
            if (photosTable.getSelectedRow() != -1) {
                Map<String, String> photos = photoTableModel.getPhotoAt(photosTable.getSelectedRow()).getMetadataAsMap();
                System.out.println(photos);
            }
        });
        popupMenu.add(printExifInfoMenuItem);
        photosTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
                    int row = photosTable.rowAtPoint(e.getPoint());
                    int column = photosTable.columnAtPoint(e.getPoint());
                    if (!photosTable.isRowSelected(row)) {
                        photosTable.changeSelection(row, column, false, false);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // drag and drop photos to table
        new DropTarget(this, new DropPhotoListListener(photoTableModel));
    }

    private void setExifDataForSelectedPhoto(Photo photo) throws IOException {

        mainFrame.getExifDataPanel().mergeExifDataForPhoto(photo);
        mainFrame.getPhotoViewPanel().showPhotoOnView(photo);
    }
}
