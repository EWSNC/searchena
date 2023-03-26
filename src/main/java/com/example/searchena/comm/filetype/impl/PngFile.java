package com.example.searchena.comm.filetype.impl;

import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.searchena.util.CPathUtil.PATH_PIC;
import static com.example.searchena.util.CPathUtil.PATH_ROOT;

@Slf4j
@Component
public class PngFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_PIC + File.separator + "png" + File.separator;

    @Override
    public String getType() {
        return "png";
    }

    @Override
    public String getFilePath() {
        log.info("PngFile:{}", path);
        return path;
    }
}
