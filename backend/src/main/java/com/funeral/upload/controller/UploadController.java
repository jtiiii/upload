package com.funeral.upload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 上传controller
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 2:26 PM
 */
@RestController
public class UploadController {

    private static final String FILE_DIR = "/files/";

    @PostMapping("/uploadFile.login")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file == null || file.isEmpty()){
            return "failed";
        }
        String filePath = request.getServletContext().getRealPath("/files/");
        String fileName = file.getOriginalFilename();
        File newFile = new File(filePath,fileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            return "Failed. "+e.getMessage();
        }

        return "/files/"+fileName;

    }
}
