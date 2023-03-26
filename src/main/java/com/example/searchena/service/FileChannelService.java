package com.example.searchena.service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileChannelService {
    String uploadFile(HttpServletRequest request) throws IOException;

    String getFileList();
}
