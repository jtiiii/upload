package com.funeral.upload.controller;

import com.funeral.upload.entity.UploadState;
import com.funeral.upload.listener.UploadProgressListener;
import com.funeral.upload.resolver.UploadStateMultipartResolver;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 上传controller
 *
 * @author FuneralObjects 张峰
 * CreateTime 2018/6/5 2:26 PM
 */
@RestController
public class UploadController {

    private static final String FILE_DIR = "/files/";

    @PostMapping("/uploadFile")
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
