/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.merger;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;

/**
 *
 * @author Michael Stappert
 */
public class Photo implements Comparable<Photo> {

    private static final FileFilter ACCEPTED_FILES = (File file)
            -> file.getName().toLowerCase().endsWith(".jpg")
            || file.getName().toLowerCase().endsWith(".jpeg");

    private static final SimpleDateFormat DATE_TIME_FORMAT
            = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");

    private static final long BYTE_TO_MB = 1024L * 1024;

    private final File file;

    private File referencePhoto;

    private String recordingDateTime;

    private JpegImageMetadata jpegMetadataOriginal;

    public Photo(File file) {
        this.file = file;
        try {
            ImageMetadata metadata = Imaging.getMetadata(file);
            jpegMetadataOriginal = (JpegImageMetadata) metadata;
        } catch (IOException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // file ====================================================================
    public File getFile() {
        return file;
    }

    public String getLastModified() {
        return DATE_TIME_FORMAT.format(new Date(file.lastModified()));
    }

    public String getCreationTime() {
        try {
            BasicFileAttributes fileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            return DATE_TIME_FORMAT.format(new Date(fileAttributes.creationTime().toMillis()));
        } catch (IOException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
            return "could not be determined";
        }
    }

    public String getLength() {
        return String.format("%.2f", (double) file.length() / BYTE_TO_MB) + " MB";
    }

    // description =============================================================
    // source ==================================================================
    public String getRecordingDateTime() {
        if (recordingDateTime == null) {
            recordingDateTime = getTagValue(jpegMetadataOriginal,
                    ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        }
        return recordingDateTime;
    }

    public String[] getRecordingDateTimeValues() {
        List<String> values = new ArrayList<>();
        values.add(getRecordingDateTime());

        String recordingDateTimeOriginal = getTagValue(jpegMetadataOriginal,
                ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        if (!recordingDateTimeOriginal.isEmpty()) {
            values.add(recordingDateTimeOriginal);
        }

        return values.toArray(new String[values.size()]);
    }

    public void setRecordingDateTime(String recordingDateTime) {
        this.recordingDateTime = recordingDateTime;
    }

    // =========================================================================
    @Override
    public int compareTo(Photo photo) {
        return this.file.getAbsolutePath().compareTo(photo.getFile().getAbsolutePath());
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Photo other = (Photo) obj;
        return Objects.equals(this.file, other.file);
    }

    @Override
    public String toString() {
        return file.getAbsolutePath();
    }

    // utilities ===============================================================
    public static List<Photo> loadPhotosFromDir(File directory) {

        return directory != null && directory.isDirectory()
                ? Arrays.asList(directory.listFiles(ACCEPTED_FILES))
                        .stream()
                        .map(file -> new Photo(file))
                        .collect(Collectors.toList())
                : new ArrayList<>();
    }

    public static List<Photo> loadPhotos(File... files) {

        return loadPhotos(Arrays.asList(files));
    }

    public static List<Photo> loadPhotos(Collection<File> files) {

        return files
                .stream()
                .filter(file -> ACCEPTED_FILES.accept(file))
                .map(file -> new Photo(file))
                .collect(Collectors.toList());
    }

    // private =================================================================
    private String getTagValue(JpegImageMetadata jpegMetadata, TagInfo tagInfo) {
        return Optional.ofNullable(jpegMetadata)
                .map(metadata -> metadata.findExifValueWithExactMatch(tagInfo))
                .map(TiffField::getValueDescription)
                .orElse("");
    }
}
