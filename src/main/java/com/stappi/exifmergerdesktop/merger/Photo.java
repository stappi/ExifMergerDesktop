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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Michael Stappert
 */
@Builder
@ToString
public class Photo implements Comparable<Photo> {

    private static final FileFilter ACCEPTED_FILES = (File file1) 
            -> file1.getName().endsWith(".jpg") || file1.getName().endsWith(".jpeg");
        
    private static final SimpleDateFormat DATE_TIME_FORMAT = 
            new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
    
    private static final long BYTE_TO_MB = 1024L * 1024;
      
    @Getter
    private final File file;
    
    @Getter
    @Setter
    private File referencePhoto;
    
    // file ====================================================================
    
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
        return Math.round(file.length() * 100.0 / BYTE_TO_MB) / 100.0 + " MB";                
    }

    // description =============================================================
    
//    public static void metadataExample(final File file) throws ImagingException, IOException {
//        // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
//        final ImageMetadata metadata = Imaging.getMetadata(file);
//
//        // System.out.println(metadata);
//
//        if (metadata instanceof JpegImageMetadata) {
//            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
//
//            // Jpeg EXIF metadata is stored in a TIFF-based directory structure
//            // and is identified with TIFF tags.
//            // Here we look for the "x resolution" tag, but
//            // we could just as easily search for any other tag.
//            //
//            // see the TiffConstants file for a list of TIFF tags.
//
//            System.out.println("file: " + file.getPath());
//
//            // print out various interesting EXIF tags.
//            printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION);
//            printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME);
//            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
//            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);
//            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO);
//            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
//            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
//            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE);
//            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
//            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE);
//            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
//            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
//
//            System.out.println();
//
//            // simple interface to GPS data
//            final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
//            if (null != exifMetadata) {
//                final TiffImageMetadata.GpsInfo gpsInfo = exifMetadata.getGpsInfo();
//                if (null != gpsInfo) {
//                    final String gpsDescription = gpsInfo.toString();
//                    final double longitude = gpsInfo.getLongitudeAsDegreesEast();
//                    final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
//
//                    System.out.println("    " + "GPS Description: " + gpsDescription);
//                    System.out.println("    " + "GPS Longitude (Degrees East): " + longitude);
//                    System.out.println("    " + "GPS Latitude (Degrees North): " + latitude);
//                }
//            }
//
//            System.out.println();
//
//            final List<ImageMetadataItem> items = jpegMetadata.getItems();
//            for (final ImageMetadataItem item : items) {
//                System.out.println("    " + "item: " + item);
//            }
//
//            System.out.println();
//        }
//    }
//
//    private static void printTagValue(final JpegImageMetadata jpegMetadata, final TagInfo tagInfo) {
//        final TiffField field = jpegMetadata.findExifValueWithExactMatch(tagInfo);
//        if (field == null) {
//            System.out.println(tagInfo.name + ": " + "Not Found.");
//        } else {
//            System.out.println(tagInfo.name + ": " + field.getValueDescription());
//        }
//    }
    
    // source ==================================================================
    
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

    // utilities ===============================================================
            
    public static List<Photo> loadPhotosFromDir(File directory) {
        
        return directory != null && directory.isDirectory()
                ? Arrays.asList(directory.listFiles(ACCEPTED_FILES))
                        .stream()
                        .map(file -> Photo.builder().file(file).build())
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
                .map(file -> Photo.builder().file(file).build())
                .collect(Collectors.toList());
    }
}
