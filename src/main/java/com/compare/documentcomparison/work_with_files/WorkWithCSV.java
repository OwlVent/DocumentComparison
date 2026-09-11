package com.compare.documentcomparison.work_with_files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jspecify.annotations.NonNull;

public class WorkWithCSV {
    private @NonNull List<String[]> UploadFileCSV(String path) {
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

    public void ComparisonCSV(String pathFirstFile, String pathSecondFile){
        List<String[]> dataFirstFile = UploadFileCSV(pathFirstFile);
        List<String[]> dataSecondFile = UploadFileCSV(pathSecondFile);

        if (dataFirstFile.isEmpty() || dataSecondFile.isEmpty()) {
            System.out.println("Ошибка загрузки одного из файлов.");
        } else{
            for(int i = 0; i < dataFirstFile.size(); i++){
                String[] rowFirstFile = dataFirstFile.get(i);
                String[] rowSecondFile = dataSecondFile.get(i);

                if(!Arrays.equals(rowFirstFile, rowSecondFile)){
                    System.out.println("The difference in the line #" + (i+1));
                    System.out.println(Arrays.toString(rowFirstFile) + " -> " + Arrays.toString(rowSecondFile));
                }
            }
        }
    }
}
