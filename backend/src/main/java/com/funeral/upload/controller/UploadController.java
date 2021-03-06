package com.funeral.upload.controller;

import com.funeral.upload.entity.UploadState;
import com.funeral.upload.resolver.UploadStateMultipartResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 上传controller
 *
 * @author FuneralObjects
 * CreateTime 2018/6/5 2:26 PM
 */
@RestController
public class UploadController {

    private static final String FILE_DIR = "/files";

    @PostMapping(value = "/uploadFile",produces = "application/json; charset=utf-8")
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        if(file == null || file.isEmpty()){
            return "failed";
        }

        String filePath = request.getServletContext().getRealPath(FILE_DIR);
        String fileName = file.getOriginalFilename();
        File folder = new File(filePath);
        if (!folder.exists()){
            folder.mkdir();
        }
        File newFile = new File(filePath,fileName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            return "Failed. "+e.getMessage();
        }
        UploadState state = (UploadState) request.getSession().getAttribute(UploadStateMultipartResolver.SESSION_KEY);
        state.setComplete(true);
        state.setPercent(new BigDecimal(1));
        return "/files/"+fileName;

    }

    @GetMapping("/uploadFile")
    public Integer getUploadState(HttpServletRequest request){
        UploadState state= (UploadState) request.getSession().getAttribute(UploadStateMultipartResolver.SESSION_KEY);
        return state == null? 0 : state.getPercent()
                .multiply(new BigDecimal(100))
                .intValue();
    }
}
