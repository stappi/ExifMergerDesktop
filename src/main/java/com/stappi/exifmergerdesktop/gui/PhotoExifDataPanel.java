/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.merger.Photo;
import com.stappi.exifmergerdesktop.utilities.FileUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *
 * @author Michael Stappert
 */
public class PhotoExifDataPanel extends JPanel {

    private static final int GRID_LAYOUT_WIDTH = 5;

    private final GridBagConstraints constraints;

    private JTextField fileNameTextField;
    private JLabel directoryLabel;
    private JLabel fileTypeLabel;
    private JLabel fileLengthLabel;
    private JLabel lastModifiedLabel;
    private JLabel changeDateLabel;

    private JComboBox titleComboBox;
    private JComboBox subjectComboBox;
    private JComboBox ratingComboBox;
    private JComboBox markingComboBox;
    private JComboBox commentsComboBox;

    private JComboBox authorsComboBox;
    private JComboBox recordingDateComboBox;
    private JComboBox softwareNameComboBox;
    private JComboBox entryDateComboBox;
    private JComboBox copyRightComboBox;

    public PhotoExifDataPanel() {
        super(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        initComponents();
    }
    
    public void setExifDataForPhoto(Photo photo) throws IOException {

        fileNameTextField.setText(photo.getFile().getName());
        directoryLabel.setText(photo.getFile().getParent());
        fileTypeLabel.setText(FileUtilities.getExtension(photo.getFile()).orElse("?"));
        fileLengthLabel.setText(photo.getLength());
        lastModifiedLabel.setText(photo.getLastModified());
        changeDateLabel.setText(photo.getCreationTime());

        recordingDateComboBox.setModel(new DefaultComboBoxModel(photo.getRecordingDateTimeValues()));
        recordingDateComboBox.setSelectedIndex(0);
    }

    private void initComponents() {

        int currentRow = 0;
        currentRow = initExifDataPanelAddFileInfos(currentRow);
        currentRow = initExifDataPanelAddDescriptionInfos(currentRow);
        initExifDataPanelAddSourceInfos(currentRow);

        // Platzhalter-Komponente hinzufügen, um das Grid nach oben links zu schieben
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.gridwidth = 3; // Reicht über alle Spalten
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        add(new JPanel(), constraints);
    }

    private int initExifDataPanelAddFileInfos(int row) {

        fileNameTextField = new JTextField();
        directoryLabel = new JLabel();
        fileTypeLabel = new JLabel();
        fileLengthLabel = new JLabel();
        lastModifiedLabel = new JLabel();
        changeDateLabel = new JLabel();

        addCaptionRowToGrid("File Info", row++);

        addRowToGrid("Filename:", fileNameTextField, row++);
        addRowToGrid("Directory:", directoryLabel, row++);
        addRowToGrid("Filetype:", fileTypeLabel, row++);
        addRowToGrid("Length:", fileLengthLabel, row++);
        addRowToGrid("Last Modified:", lastModifiedLabel, row++);
        addRowToGrid("Creation Time:", changeDateLabel, row++);

        return row;
    }

    private int initExifDataPanelAddDescriptionInfos(int row) {

        titleComboBox = new JComboBox();
        subjectComboBox = new JComboBox();
        ratingComboBox = new JComboBox();
        markingComboBox = new JComboBox();
        commentsComboBox = new JComboBox();

        addCaptionRowToGrid("Description", row++);

        addRowToGrid("Title:", titleComboBox, row++);
        addRowToGrid("Subject:", subjectComboBox, row++);
        addRowToGrid("Rating:", ratingComboBox, row++);
        addRowToGrid("Marking:", markingComboBox, row++);
        addRowToGrid("Comments:", commentsComboBox, row++);

        return row;
    }

    private int initExifDataPanelAddSourceInfos(int row) {

        authorsComboBox = new JComboBox();
        recordingDateComboBox = new JComboBox();
        recordingDateComboBox.setEditable(true);
        softwareNameComboBox = new JComboBox();
        entryDateComboBox = new JComboBox();
        copyRightComboBox = new JComboBox();

        addCaptionRowToGrid("Source", row++);

        // Methode zum Hinzufügen einer Zeile mit Label, Textfeld und optionalem Button
        addRowToGrid("Authors:", authorsComboBox, row++);
        addRowToGrid("Recording Date:", recordingDateComboBox, row++);
        addRowToGrid("Software:", softwareNameComboBox, row++);
        addRowToGrid("Entry Date:", entryDateComboBox, row++);
        addRowToGrid("Copyright:", copyRightComboBox, row++);

        return row;
    }

    private void addCaptionRowToGrid(String labelText, int row) {

        constraints.insets = new Insets(15, 5, 5, 5);
        constraints.gridy = row;
        constraints.gridx = 0;
        constraints.gridwidth = GRID_LAYOUT_WIDTH;
        JLabel captionLabel = new JLabel(labelText);
        captionLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        add(captionLabel, constraints);

        constraints.insets = new Insets(40, 5, 5, 5);
        add(new JSeparator(), constraints);
    }

    private void addRowToGrid(String labelText, JComponent component, int row) {

        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridy = row;

        // add label
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        JLabel label = new JLabel(labelText);
        add(label, constraints);

        // add component
        constraints.gridx = 1;
        constraints.gridwidth = GRID_LAYOUT_WIDTH - 1;
        add(component, constraints);
    }
}
