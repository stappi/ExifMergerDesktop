/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
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
    
    private final MainFrame mainFrame;
    private final GridBagConstraints constraints;
    private JButton okButton;
    private JButton applyButton;
    private JButton saveButton;
    private JButton saveCopyButton;
    private JButton cancelButton;

    public Sidebar(MainFrame mainFrame) {
        super(new GridBagLayout());

        this.mainFrame = mainFrame;
        
        initButtons();

        this.constraints = new GridBagConstraints();       
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 5, 5);

        add(okButton, constraints);
        add(cancelButton, constraints);
        add(saveButton, constraints);
        GuiUtilities.addPlaceholder2Panel(this);
    }
   
    public void showButtonsForSettingsGeneralExifData() {
        this.removeAll();
        add(okButton, constraints);
        add(applyButton, constraints);
        add(cancelButton, constraints);
        GuiUtilities.addPlaceholder2Panel(this);
    }
    
    private void initButtons() {
        okButton = new JButton("Ok");
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");
        applyButton = new JButton("Apply");
    }
}
