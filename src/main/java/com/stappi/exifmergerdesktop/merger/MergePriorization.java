/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.merger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Michael Stappert
 */
@Setter
@Getter
public class MergePriorization {
    
    private final static List<ExifDataSource> DEFAULT_ORDER = 
            new ArrayList<>(Arrays.asList(
                    ExifDataSource.ORIGINAL, 
                    ExifDataSource.REFERENCE, 
                    ExifDataSource.SETTING)
    );
    private List<ExifDataSource> title = DEFAULT_ORDER;
    private List<ExifDataSource> subject = DEFAULT_ORDER;
    private List<ExifDataSource> rating = DEFAULT_ORDER;
    private List<ExifDataSource> keywords = DEFAULT_ORDER;
    private List<ExifDataSource> comment = DEFAULT_ORDER;
    private List<ExifDataSource> authors = DEFAULT_ORDER;
    private List<ExifDataSource> recordingDate = DEFAULT_ORDER;
    private List<ExifDataSource> software = DEFAULT_ORDER;
    private List<ExifDataSource> copyRight = DEFAULT_ORDER;  
}
