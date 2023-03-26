package com.example.searchena.controller;

import com.example.searchena.service.FileChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("fileChannel")
@Controller
public class FileChannelController {

    @Autowired
    private FileChannelService fileChannelService;

    @RequestMapping("getFileList")
    @ResponseBody
    public String getFileList() {
        return fileChannelService.getFileList();
    }


    @RequestMapping("uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request) throws Exception {
        return fileChannelService.uploadFile(request);
    }


}
