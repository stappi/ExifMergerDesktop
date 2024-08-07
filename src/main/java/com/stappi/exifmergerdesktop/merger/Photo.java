package com.stappi.exifmergerdesktop.merger;

import com.stappi.exifmergerdesktop.SettingsManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lombok.NonNull;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.GenericImageMetadata.GenericImageMetadataItem;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;

/**
 * @author Michael Stappert
 */
public class Photo implements Comparable<Photo> {

    private static final FileFilter ACCEPTED_FILES = (File file)
            -> file.getName().toLowerCase().endsWith(".jpg")
            || file.getName().toLowerCase().endsWith(".jpeg");

    private static final SimpleDateFormat DATE_TIME_FORMAT
            = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");

    private static final long BYTE_TO_MB = 1024L * 1024;

    private static final SettingsManager SETTINGS = SettingsManager.getInstance();

    @NonNull
    private final File file;

    private File referencePhoto;

    private ExifDataValue title;
    private ExifDataValue subject;
    private ExifDataValue rating;
    private ExifDataValue keywords;
    private ExifDataValue comment;
    private ExifDataValue recordingDateTime;
    private ExifDataValue authors;

    private JpegImageMetadata jpegMetadataOriginal;
    private JpegImageMetadata jpegMetadataReference;

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

    public void setReferencePhoto(File referencePhoto) {
        this.referencePhoto = referencePhoto;
        try {
            ImageMetadata metadata = Imaging.getMetadata(file);
            jpegMetadataReference = (JpegImageMetadata) metadata;
            this.title.setReference(getTagValue(
                    jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPTITLE));
            this.subject.setReference(getTagValue(
                    jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPSUBJECT));
            this.rating.setReference(getTagValue(
                    jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_RATING));
            this.keywords.setReference(getTagValue(
                    jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS));
            this.comment.setReference(getTagValue(
                    jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPCOMMENT));
            this.authors.setReference(getTagValue(
                    jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPAUTHOR));
            this.recordingDateTime.setReference(getTagValue(
                    jpegMetadataReference, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL));
        } catch (IOException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public File getReferencePhotoFile() {
        return referencePhoto;
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
    public String[] getTitleValues() {

        if (title == null) {
            title = ExifDataValue.builder()
                                 .mergePrio(SETTINGS.getMergePriorization().getTitle())
                                 .original(getTagValue(jpegMetadataOriginal, MicrosoftTagConstants.EXIF_TAG_XPTITLE))
                                 .reference(getTagValue(jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPTITLE))
                                 .settingsValue(SETTINGS.getGeneralExifData().getTitle())
                                 .build();
        }

        return title.getValues();
    }

    public void seTitle(String title) {
        this.title.setValue(title);
    }

    public String[] getSubjectValues() {

        if (subject == null) {
            subject = ExifDataValue.builder()
                                   .mergePrio(SETTINGS.getMergePriorization().getSubject())
                                   .original(getTagValue(jpegMetadataOriginal, MicrosoftTagConstants.EXIF_TAG_XPSUBJECT))
                                   .reference(getTagValue(jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPSUBJECT))
                                   .settingsValue(SETTINGS.getGeneralExifData().getSubject())
                                   .build();
        }

        return subject.getValues();
    }

    public void setSubject(String subject) {
        this.subject.setValue(subject);
    }

    public String[] getRating() {

        if (rating == null) {
            rating = ExifDataValue.builder()
                                  .mergePrio(SETTINGS.getMergePriorization().getRating())
                                  .original(getTagValue(jpegMetadataOriginal, MicrosoftTagConstants.EXIF_TAG_RATING))
                                  .reference(getTagValue(jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_RATING))
                                  .build();
        }

        return rating.getValues();
    }

    public void setRating(String rating) {
        this.rating.setValue(rating);
    }

    public String[] getKeywordValues() {

        if (keywords == null) {
            keywords = ExifDataValue.builder()
                                    .mergePrio(SETTINGS.getMergePriorization().getKeywords())
                                    .original(getTagValue(jpegMetadataOriginal, MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS))
                                    .reference(getTagValue(jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS))
                                    .settingsValue(SETTINGS.getGeneralExifData().getKeywords())
                                    .build();
        }

        return keywords.getValues();
    }

    public void setKeywords(String keywords) {
        this.keywords.setValue(keywords);
    }

    public String[] getCommentValues() {

        if (comment == null) {
            comment = ExifDataValue.builder()
                                   .mergePrio(SETTINGS.getMergePriorization().getComment())
                                   .original(getTagValue(jpegMetadataOriginal, MicrosoftTagConstants.EXIF_TAG_XPCOMMENT))
                                   .reference(getTagValue(jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPCOMMENT))
                                   .settingsValue(SETTINGS.getGeneralExifData().getSubject())
                                   .build();
        }

        return comment.getValues();
    }

    public void setComment(String comment) {
        this.comment.setValue(comment);
    }

    public String[] getAuthors() {

        if (authors == null) {
            authors = ExifDataValue.builder()
                                   .mergePrio(SETTINGS.getMergePriorization().getAuthors())
                                   .original(getTagValue(jpegMetadataOriginal, MicrosoftTagConstants.EXIF_TAG_XPAUTHOR))
                                   .reference(getTagValue(jpegMetadataReference, MicrosoftTagConstants.EXIF_TAG_XPAUTHOR))
                                   .settingsValue(SETTINGS.getGeneralExifData().getSubject())
                                   .build();
        }

        return authors.getValues();
    }

    public void setAuthors(String authors) {
        this.authors.setValue(authors);
    }

    // source ==================================================================
    public String[] getRecordingDateTimeValues() {

        if (recordingDateTime == null) {
            recordingDateTime = ExifDataValue.builder()
                                             .mergePrio(SETTINGS.getMergePriorization().getRecordingDate())
                                             .original(getTagValue(jpegMetadataOriginal, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL))
                                             .reference(getTagValue(jpegMetadataReference, ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL))
                                             .settingsValue(SETTINGS.getGeneralExifData().getRecordingDate())
                                             .build();
        }

        return recordingDateTime.getValues();
    }

    public void setRecordingDateTime(String recordingDateTime) {
        this.recordingDateTime.setValue(recordingDateTime);
    }

    // =========================================================================
    public Map<String, String> getMetadataAsMap() {

        Map<String, String> map = new LinkedHashMap<>();

        try {
            ImageMetadata metadata = Imaging.getMetadata(file);

            if (metadata instanceof JpegImageMetadata jpegImageMetadata) {
                final JpegImageMetadata jpegMetadata = jpegImageMetadata;

                final List<ImageMetadataItem> items = jpegMetadata.getItems();

                for (int i = 0; i < items.size(); i++) {
                    final ImageMetadataItem item = items.get(i);
                    if (item instanceof GenericImageMetadataItem gItem) {

                        map.put(gItem.getKeyword(), gItem.getText());
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public void save(boolean saveAsCopy) {
        File outputFile = saveAsCopy ? new File(file.getParentFile(), "copy_" + file.getName()) : file;

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
             OutputStream outputStream = new BufferedOutputStream(fileOutputStream)) {

            new ExifRewriter().updateExifMetadataLossless(file, outputStream, getMetadata());
        } catch (ImagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
//    private String getTagValue(JpegImageMetadata jpegMetadata, TagInfo tagInfo) {
//        return Optional.ofNullable(jpegMetadata)
//                .map(metadata -> metadata.findExifValueWithExactMatch(tagInfo))
//                .map(TiffField::getValueDescription)
//                .orElse("");
//    }
    private String getTagValue(JpegImageMetadata jpegMetadata, TagInfo tagInfo) {
        if (jpegMetadata == null) {
            return "";
        }

        try {
            Object fieldValue = jpegMetadata.getExif().getFieldValue(tagInfo);
            return fieldValue != null ? fieldValue.toString() : "";
        } catch (ImagingException ex) {
            Logger.getLogger(Photo.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    /**
     * See: https://github.com/apache/commons-imaging/blob/master/src/test/java/org/apache/commons/imaging/examples/WriteExifMetadataExample.java
     *
     * @return
     * @throws IOException
     */
    private TiffOutputSet getMetadata() throws IOException {

        TiffOutputSet outputSet = null;

        // note that metadata might be null if no metadata is found.
        final ImageMetadata metadata = Imaging.getMetadata(file);
        final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
        if (null != jpegMetadata) {
            final TiffImageMetadata exif = jpegMetadata.getExif();

            if (null != exif) {
                outputSet = exif.getOutputSet();
            }
        }
        // if file does not contain any exif metadata, we create an empty
        if (null == outputSet) {
            outputSet = new TiffOutputSet();
        }

        // remove old metadata and add new metadata
        final TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();

//        outputSet.addRootDirectory()
        outputSet.getRootDirectory().add(MicrosoftTagConstants.EXIF_TAG_XPTITLE, title.getValue());
        outputSet.getRootDirectory().add(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT, subject.getValue());
        outputSet.getRootDirectory().add(MicrosoftTagConstants.EXIF_TAG_RATING, rating.getValueShort());
        outputSet.getRootDirectory().add(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS, keywords.getValue());
        outputSet.getRootDirectory().add(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT, comment.getValue());
        outputSet.getRootDirectory().add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, recordingDateTime.getValue());
        outputSet.getRootDirectory().add(MicrosoftTagConstants.EXIF_TAG_XPAUTHOR, authors.getValue());

        // Example of how to add/update GPS info to output set.
        outputSet.setGpsInDegrees(-74.0, 40 + 43 / 60.0);

        return outputSet;
    }
}
