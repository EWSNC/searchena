package com.example.searchena.comm.filetype.impl;

import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.searchena.util.CPathUtil.PATH_FILE;
import static com.example.searchena.util.CPathUtil.PATH_ROOT;


@Slf4j
@Component
public class ZipFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_FILE + File.separator + "zip" + File.separator;

    @Override
    public String getType() {
        return "zip";
    }

    @Override
    public String getFilePath() {
        log.info("ZipFile:{}", path);
        return path;
    }
}
