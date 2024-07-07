package com.stappi.exifmergerdesktop.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

public class MainFrame extends JFrame {

    private static final int MAIN_FRAME_WIDTH = 1200;
    private static final int MAIN_FRAME_HEIGHT = 750;
    private static final int SIDEBAR_MAX_WIDTH = 200;

    // main panels
    private JSplitPane verticalSplitPane;
    private JSplitPane horizontalSplitPane;
    private Sidebar sidebar;
    private PhotoTablePanel photoListPanel;
    private JPanel photoDetailsPanel;
    private SettingsGeneralExifDataPanel generalExifDataPanel;
    private JPanel mergePriorizationPanel;

    // photoDetailsPanel   
    private ExifDataPanel photoExifDataPanel;
    private PhotoViewPanel photoViewPanel;

    // =========================================================================
    public MainFrame() {
        initMainFrame();
        initMainPanels();
        initExifDataPanel();
        initGeneralExifDataPanel();
        initMergePriorizationPanel();

        initMenu();
    }
    
    // =========================================================================
    // update gui
    // =========================================================================
    public JSplitPane getHorizontalSplitPane() {
        return horizontalSplitPane;
    }

    public JSplitPane getVerticalSplitPane() {
        return verticalSplitPane;
    }

    public Sidebar getSidebar() {
        return sidebar;
    }

    public SettingsGeneralExifDataPanel getGeneralExifDataPanel() {
        return generalExifDataPanel;
    }

    public JPanel getMergePriorizationPanel() {
        return mergePriorizationPanel;
    }
    
    public ExifDataPanel getExifDataPanel() {
        return photoExifDataPanel;
    }
    
    public PhotoViewPanel getPhotoViewPanel() {
        return this.photoViewPanel;
    }
    
    public PhotoTablePanel getPhotoTablePanel() {
        return photoListPanel;
    }

    // =========================================================================
    // listener
    // =========================================================================
    
    // =========================================================================
    // init
    // =========================================================================
    private void initMainFrame() {

        setTitle("Exif Merger Desktop");
        setSize(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Zentriert das Fenster auf dem Bildschirm
    }

    private void initMainPanels() {

        // create main panels for displaying photos and exif data: =
        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        photoListPanel = new PhotoTablePanel(this);
        photoDetailsPanel = new JPanel(new BorderLayout());
        verticalSplitPane.setTopComponent(photoListPanel);
        verticalSplitPane.setBottomComponent(photoDetailsPanel);
//        verticalSplitPane.setResizeWeight(1.0); // photosPanel height grows higher with main frame
        verticalSplitPane.setOneTouchExpandable(true);

        // create sidebar: |=
        horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        sidebar = new Sidebar(this);
        sidebar.setMinimumSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        sidebar.setPreferredSize(new Dimension(SIDEBAR_MAX_WIDTH, 0));
        horizontalSplitPane.setLeftComponent(sidebar);
        horizontalSplitPane.setRightComponent(verticalSplitPane);
        horizontalSplitPane.setOneTouchExpandable(true);

        // set default position
        horizontalSplitPane.setDividerLocation(200);
        verticalSplitPane.setDividerLocation(getHeight() - 550);

        getContentPane().add(horizontalSplitPane);
    }

    private void initExifDataPanel() {

        photoExifDataPanel = new ExifDataPanel();
        JScrollPane dataScrollPane = new JScrollPane(photoExifDataPanel);

        photoViewPanel = new PhotoViewPanel(this);
        JScrollPane photoViewScrollPane = new JScrollPane(photoViewPanel);

        // add panels
        photoDetailsPanel.add(dataScrollPane, BorderLayout.CENTER);
        photoDetailsPanel.add(photoViewScrollPane, BorderLayout.EAST);
    }

    private void initGeneralExifDataPanel() {
        generalExifDataPanel = new SettingsGeneralExifDataPanel(this);
    }

    private void initMergePriorizationPanel() {

        mergePriorizationPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Merge Prio");
        mergePriorizationPanel.add(label, BorderLayout.CENTER);
    }

    private void initMenu() {
        setJMenuBar(new Menu(this));
    }
}
