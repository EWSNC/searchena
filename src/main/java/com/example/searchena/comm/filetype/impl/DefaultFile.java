package com.example.searchena.comm.filetype.impl;


import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static com.example.searchena.util.CPathUtil.PATH_FILE;
import static com.example.searchena.util.CPathUtil.PATH_ROOT;

@Slf4j
public class DefaultFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_FILE + File.separator + "default" + File.separator;

    @Override
    public String getType() {
        return "default";
    }

    @Override
    public String getFilePath() {
        log.info("DefaultFile:{}", path);
        return path;
    }
}
