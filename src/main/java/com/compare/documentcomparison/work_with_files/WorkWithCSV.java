package com.compare.documentcomparison.work_with_files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.compare.documentcomparison.exception_handler.BusinessException;
import com.compare.documentcomparison.exception_handler.ErrorType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jspecify.annotations.NonNull;

public class WorkWithCSV {
    private @NonNull List<String[]> uploadFileCSV(String path) {
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Path.of(path))) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    list.add(line);
                }
            } catch (IOException | CsvValidationException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<String> comparisonCSV(String pathFirstFile, String pathSecondFile){
        List<String[]> dataFirstFile = uploadFileCSV(pathFirstFile);
        List<String[]> dataSecondFile = uploadFileCSV(pathSecondFile);

        if (dataFirstFile.size() == 1 || dataSecondFile.size() == 1) {
            throw new BusinessException(ErrorType.NO_DATA);
        } else{
            List<String> result = new ArrayList<>();
            for(int i = 0; i < dataFirstFile.size(); i++){
                String[] rowFirstFile = dataFirstFile.get(i);
                String[] rowSecondFile = dataSecondFile.get(i);

                if (rowFirstFile != null && rowFirstFile.length > 0) {
                    rowFirstFile[0] = rowFirstFile[0].replaceAll("[\\uFEFF\\u200B\\u2060]", "");
                }
                if (rowSecondFile != null && rowSecondFile.length > 0) {
                    rowSecondFile[0] = rowSecondFile[0].replaceAll("[\\uFEFF\\u200B\\u2060]", "");
                }

                if(!Arrays.equals(rowFirstFile, rowSecondFile)){
                    result.add("The difference in the line #" + (i+1) + " "
                            + Arrays.toString(rowFirstFile) + " -> " + Arrays.toString(rowSecondFile));
                }
            }
            return result;
        }
    }
}
