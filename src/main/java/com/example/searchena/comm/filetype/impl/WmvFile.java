package com.example.searchena.comm.filetype.impl;

import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.example.searchena.util.CPathUtil.PATH_FILE;
import static com.example.searchena.util.CPathUtil.PATH_ROOT;

@Slf4j
@Component
public class WmvFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_FILE + File.separator + "wmv" + File.separator;

    @Override
    public String getType() {
        return "wmv";
    }

    @Override
    public String getFilePath() {
        log.info("WmvFile:{}", path);
        return path;
    }
}
