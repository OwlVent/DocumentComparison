package com.compare.documentcomparison;

import com.compare.documentcomparison.work_with_files.WorkWithCSV;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestsForWorkWithCSV {
    private final WorkWithCSV workWithCSV = new WorkWithCSV();
    private final String firstFile = "src/main/resources/files/FirstBookCSV.csv";
    private final String secondFile = "src/main/resources/files/SecondBookCSV.csv";
    private final String thridFile = "src/main/resources/files/ThridBookCSV.csv";
    private final String fourthFile = "src/main/resources/files/FourthBookCSV.csv";
    private final String fifthFile = "src/main/resources/files/FifthBookCSV.csv";
    private final String emptyFile = "src/main/resources/files/EmptyBookCSV.csv";

    // Сравнение двух файлов с отличиями возвращает правильный список отличий
    @Test
    void testComparisonCSV() {
        List<String> result = List.of(new String[]{"The difference in the line #1 [12;1] -> [12;10]", "The difference in the line #7 [78;78] -> [78;67]"});
        assertEquals(result, workWithCSV.comparisonCSV(firstFile, secondFile));
    }

    // У двух одинаковых файлов результат пустой
    @Test
    void testComparisonIdenticalFiles(){
        List<String> result = List.of(new String[]{"The files are identical"});
        assertEquals(result, workWithCSV.comparisonCSV(secondFile, thridFile));
    }

    // Сравнение файла с самим собой не находит отличий
    @Test
    void testComparisonSameFile(){
        List<String> result = List.of(new String[]{"The files are identical"});
        assertEquals(result, workWithCSV.comparisonCSV(secondFile, secondFile));
    }

    // Если отличаются все строки, отличие находится для каждой строки
    @Test
    void testAllRowsDifferent(){
        List<String> result = List.of(new String[]{"The difference in the line #1 [100] -> [101]",
                                                   "The difference in the line #2 [250] -> [255]",
                                                   "The difference in the line #3 [500] -> [525]"});
        assertEquals(result, workWithCSV.comparisonCSV(fourthFile, fifthFile));
    }
}
