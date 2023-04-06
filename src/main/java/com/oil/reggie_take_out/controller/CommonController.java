package com.oil.reggie_take_out.controller;

import com.oil.reggie_take_out.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.filepath}")
    private String filepath;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> uploda(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String s = UUID.randomUUID().toString();
        String filename = s+originalFilename.substring(originalFilename.lastIndexOf("."));
        File dir = new File(filepath);
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            file.transferTo(new File(filepath+filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(filename);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath + name);
            ServletOutputStream outputStream = response.getOutputStream();
            int len=0;
            byte[] bytes = new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
