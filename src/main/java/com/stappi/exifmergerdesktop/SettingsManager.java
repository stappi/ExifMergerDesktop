/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.stappi.exifmergerdesktop.merger.ExifData;
import com.stappi.exifmergerdesktop.merger.MergePriorization;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael Stappert
 */
public final class SettingsManager {

    private static SettingsManager INSTANCE;

    private static final File FILE_EXIF_DATA
            = new File(getAppDirectory(), "generalExifData.json");
    private static final File FILE_MERGE_PRIO
            = new File(getAppDirectory(), "mergePrio.json");

    private SettingsManager() {
    }

    public static SettingsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SettingsManager();
        }
        return INSTANCE;
    }

    public ExifData getGeneralExifData() {
        return readJsonFile(FILE_EXIF_DATA, ExifData.class).orElse(new ExifData());
    }

    public void saveGeneralExifData(ExifData exifData) {
        saveJsonFile(FILE_EXIF_DATA, exifData);
    }

    public MergePriorization getMergePriorization() {
        return readJsonFile(FILE_MERGE_PRIO, MergePriorization.class).orElse(new MergePriorization());
    }

    public void saveMergePriorization(MergePriorization mergePriorization) {
        saveJsonFile(FILE_MERGE_PRIO, mergePriorization);
    }

    private void saveJsonFile(File jsonFile, Object data) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting() // For nicely formatted output
                .create();
        try (FileWriter writer = new FileWriter(jsonFile)) {
            gson.toJson(data, writer);
        } catch (IOException ex) {
            Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private <T> Optional<T> readJsonFile(File jsonFile, Class<T> clazz) {
        if (jsonFile.exists()) {
            try {
                // Read the JSON data from the file
                JsonReader reader = new JsonReader(new FileReader(jsonFile));
                return Optional.of(new Gson().fromJson(reader, clazz));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SettingsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Optional.empty();
    }

    private static String getAppDirectory() {
        String userHome = System.getProperty("user.home");

        File appDir = new File(userHome, "ExifMerger");

        if (!appDir.exists() || !appDir.isDirectory()) {
            appDir.mkdir();
        }

        return appDir.getAbsolutePath();
    }
}
