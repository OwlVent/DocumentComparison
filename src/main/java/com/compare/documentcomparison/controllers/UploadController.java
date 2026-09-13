package com.compare.documentcomparison.controllers;

import com.compare.documentcomparison.exceptionhandler.BusinessException;
import com.compare.documentcomparison.exceptionhandler.ErrorType;
import com.compare.documentcomparison.work_with_files.WorkWithCSV;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UploadController {
    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    @ResponseBody
    public String error() {
        return "Ooops...";
    }

    @GetMapping("/comparison")
    public String showComparisonPage() {
        return "ComparisonCSV";
    }

    @PostMapping("/resultcsv")
    public String handleFileUpload(@RequestParam("firstFile") MultipartFile firstFile,
                                   @RequestParam("secondFile") MultipartFile secondFile,
                                   RedirectAttributes redirectAttributes, Model model){
        if (firstFile.isEmpty() || secondFile.isEmpty()){
            throw new BusinessException(ErrorType.FILE_IS_EMPTY);
        }

        try {

            Path pathFirst = Paths.get(UPLOAD_DIR + firstFile.getOriginalFilename());
            Files.createDirectories(pathFirst.getParent());
            Files.write(pathFirst, firstFile.getBytes());

            Path pathSecond = Paths.get(UPLOAD_DIR + secondFile.getOriginalFilename());
            Files.write(pathSecond, secondFile.getBytes());

            WorkWithCSV workWithCSV = new WorkWithCSV();
            List<String> result = workWithCSV.ComparisonCSV(pathFirst.toString(), pathSecond.toString());

            model.addAttribute("info", result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "resultCSV";
    }
}
