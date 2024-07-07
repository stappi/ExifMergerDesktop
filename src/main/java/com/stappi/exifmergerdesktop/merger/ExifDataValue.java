/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stappi.exifmergerdesktop.merger;

import com.stappi.exifmergerdesktop.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author Michael Stappert
 */
@Builder
@Setter
public class ExifDataValue {
    
    @NonNull
    private List<ExifDataSource> mergePrio;
    private String value;
    private String original;
    private String reference;
    private String settingsValue;
    
    public String[] getValues() {
        
        List<String> values = new ArrayList<>();
        
        addValueToListIfNotEmpty(values, value);
        addValueToListIfNotEmpty(values, original);
        addValueToListIfNotEmpty(values, reference);
        addValueToListIfNotEmpty(values, settingsValue);
        
        return values.toArray(String[]::new);
    }
    
    public String getValue() {
        if (!Utilities.isEmpty(value)) {
            return value;
        }
        for (ExifDataSource source : mergePrio) {
            if (ExifDataSource.ORIGINAL.equals(source) && !Utilities.isEmpty(original)) {
                return original;
            } else if (ExifDataSource.REFERENCE.equals(source) && !Utilities.isEmpty(reference)) {
                 return reference;
            } else if (ExifDataSource.SETTING.equals(source) && !Utilities.isEmpty(settingsValue)) {
                 return settingsValue;
            }
        }
        return "";
    }
    
    private void addValueToListIfNotEmpty(List<String> values, String value) {
        if (value != null && !value.replace(" ", "").isEmpty()) {                        
            values.add(value);
        }
    }   
}
