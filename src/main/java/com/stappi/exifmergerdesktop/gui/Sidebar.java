/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Michael Stappert
 */
public class Sidebar extends JPanel {

    private MainFrame mainFrame;
    private JButton okButton;
    private JButton applyButton;
    private JButton saveButton;
    private JButton saveCopyButton;
    private JButton cancelButton;

    public Sidebar(MainFrame mainFrame) {

        this.mainFrame = mainFrame;

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 5, 5);

        okButton = new JButton("Ok");
        add(okButton, constraints);

        cancelButton = new JButton("Cancel");
        add(cancelButton, constraints);

        saveButton = new JButton("Save");
        add(saveButton, constraints);

        // Platzhalter-Komponente hinzufügen, um das Grid nach oben links zu schieben
        GuiUtilities.addPlaceholder2Panel(this);
    }
}
