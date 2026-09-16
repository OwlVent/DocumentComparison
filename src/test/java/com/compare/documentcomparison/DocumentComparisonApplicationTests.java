package com.compare.documentcomparison;

import com.compare.documentcomparison.work_with_files.WorkWithCSV;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DocumentComparisonApplicationTests {

    private final WorkWithCSV workWithCSV = new WorkWithCSV();

    @Test
    void testComparisonCSV() {
        String firstFile = "src/main/resources/files/FirstBookCSV.csv";
        String secondFile = "src/main/resources/files/SecondBookCSV.csv";
        List<String> result = List.of(new String[]{"The difference in the line #1 [12;1] -> [12;10]", "The difference in the line #7 [78;78] -> [78;67]"});
        assertEquals(result, workWithCSV.comparisonCSV(firstFile, secondFile));
    }

}
