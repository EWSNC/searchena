package com.example.searchena.comm.filetype.impl;

import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.searchena.util.CPathUtil.PATH_FILE;
import static com.example.searchena.util.CPathUtil.PATH_ROOT;

@Slf4j
@Component
public class PdfFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_FILE + File.separator + "pdf" + File.separator;

    @Override
    public String getType() {
        return "pdf";
    }

    @Override
    public String getFilePath() {
        log.info("PdfFile:{}", path);
        return path;
    }
}
