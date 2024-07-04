/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *
 * @author Michael Stappert
 */
public class SettingsGeneralExifDataPanel extends JPanel {

    private static final int GRID_LAYOUT_WIDTH = 5;
    
    private final GridBagConstraints constraints;
    
    private JTextField titleTextField;
    private JTextField subjectTextField;
    private JTextField ratingTextField;
    private JTextField markingTextField;
    private JTextField commentsTextField;

    private JTextField authorsTextField;
    private JTextField recordingDateTextField;
    private JTextField softwareNameTextField;
    private JTextField entryDateTextField;
    private JTextField copyRightTextField;
    
    public SettingsGeneralExifDataPanel() {
        super(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        initComponents();
    }
    
    private void initComponents() {
        
        int currentRow = 0;
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
    
    private int initExifDataPanelAddDescriptionInfos(int row) {

        titleTextField = new JTextField();
        subjectTextField = new JTextField();
        ratingTextField = new JTextField();
        markingTextField = new JTextField();
        commentsTextField = new JTextField();

        addCaptionRowToGrid("Description", row++);

        addRowToGrid("Title:", titleTextField, row++);
        addRowToGrid("Subject:", subjectTextField, row++);
        addRowToGrid("Rating:", ratingTextField, row++);
        addRowToGrid("Marking:", markingTextField, row++);
        addRowToGrid("Comments:", commentsTextField, row++);

        return row;
    }

    private int initExifDataPanelAddSourceInfos(int row) {

        authorsTextField = new JTextField();
        recordingDateTextField = new JTextField();
        softwareNameTextField = new JTextField();
        entryDateTextField = new JTextField();
        copyRightTextField = new JTextField();

        addCaptionRowToGrid("Source", row++);

        // Methode zum Hinzufügen einer Zeile mit Label, Textfeld und optionalem Button
        addRowToGrid("Authors:", authorsTextField, row++);
        addRowToGrid("Recording Date:", recordingDateTextField, row++);
        addRowToGrid("Software:", softwareNameTextField, row++);
        addRowToGrid("Entry Date:", entryDateTextField, row++);
        addRowToGrid("Copyright:", copyRightTextField, row++);

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
