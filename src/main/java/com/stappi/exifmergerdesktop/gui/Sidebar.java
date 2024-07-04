/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.gui;

import com.stappi.exifmergerdesktop.SettingsManager;
import com.stappi.exifmergerdesktop.utilities.GuiUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Michael Stappert
 */
public class Sidebar extends JPanel {

    private final MainFrame mainFrame;

    private final GridBagConstraints constraints;

    private View currentView;

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

        showButtonsForPhotosView();
    }

    // =========================================================================
    // show buttons
    // =========================================================================
    public final void showButtonsForPhotosView() {
        this.removeAll();
        currentView = View.PHOTOS;
        add(saveButton, constraints);
        add(saveCopyButton, constraints);
        add(new JPanel(), GuiUtilities.createPlaceholderConstraint());
    }

    public final void showButtonsForSettingsGeneralExifData() {
        this.removeAll();
        this.currentView = View.GENERAL_EXIF_DATA;
        add(okButton, constraints);
        add(applyButton, constraints);
        add(cancelButton, constraints);
        add(new JPanel(), GuiUtilities.createPlaceholderConstraint());
    }

    public final void showButtonsForMergePriorization() {
        this.removeAll();
        this.currentView = View.MERGE_PRIO;
        add(okButton, constraints);
        add(applyButton, constraints);
        add(cancelButton, constraints);
        add(new JPanel(), GuiUtilities.createPlaceholderConstraint());
    }

    // =========================================================================
    // init
    // =========================================================================
    private void initButtons() {
        okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent e) -> {
            okButtonActionPerformed();
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener((ActionEvent e) -> {
            cancelButtonActionPerformed();
        });
        saveButton = new JButton("Save");
        saveCopyButton = new JButton("Copy & Save");
        applyButton = new JButton("Apply");
        applyButton.addActionListener((ActionEvent e) -> {
            applyButtonActionPerformed();
        });
    }

    // =========================================================================
    // listeners
    // =========================================================================
    private void okButtonActionPerformed() {
        if (View.GENERAL_EXIF_DATA.equals(currentView)) {
            showButtonsForPhotosView();
            mainFrame.getHorizontalSplitPane().setRightComponent(
                    mainFrame.getVerticalSplitPane());
            SettingsManager.getInstance().saveGeneralExifData(
                    mainFrame.getGeneralExifDataPanel().getExifData());

        } else if (View.MERGE_PRIO.equals(currentView)) {
            showButtonsForPhotosView();
            mainFrame.getHorizontalSplitPane().setRightComponent(
                    mainFrame.getVerticalSplitPane());
            SettingsManager.getInstance().saveGeneralExifData(null);
        }
    }

    private void applyButtonActionPerformed() {
        if (View.GENERAL_EXIF_DATA.equals(currentView)) {
            SettingsManager.getInstance().saveGeneralExifData(
                    mainFrame.getGeneralExifDataPanel().getExifData());
        } else if (View.MERGE_PRIO.equals(currentView)) {
            SettingsManager.getInstance().saveGeneralExifData(null);
        }
    }

    private void cancelButtonActionPerformed() {
        if (View.GENERAL_EXIF_DATA.equals(currentView)
                || View.MERGE_PRIO.equals(currentView)) {
            showButtonsForPhotosView();
            mainFrame.getHorizontalSplitPane().setRightComponent(
                    mainFrame.getVerticalSplitPane());
        }
    }

    // =========================================================================
    // enum
    // =========================================================================
    private enum View {
        PHOTOS, GENERAL_EXIF_DATA, MERGE_PRIO
    }
}
