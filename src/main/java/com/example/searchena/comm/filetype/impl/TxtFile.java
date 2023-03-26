package com.example.searchena.comm.filetype.impl;

import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.searchena.util.CPathUtil.PATH_ROOT;
import static com.example.searchena.util.CPathUtil.PATH_TXT;

@Slf4j
@Component
public class TxtFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_TXT;

    @Override
    public String getType() {
        return "txt";
    }



    @Override
    public String getFilePath() {
        log.info("TxtFile:{}", path);
        return path;
    }
}
