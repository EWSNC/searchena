package com.example.searchena.comm.filetype.impl;

import com.example.searchena.comm.filetype.FileTypeSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.searchena.util.CPathUtil.PATH_EXE;
import static com.example.searchena.util.CPathUtil.PATH_ROOT;

@Slf4j
@Component
public class ExcelXlsFile implements FileTypeSupport {
    private final String path = PATH_ROOT + PATH_EXE;

    @Override
    public String getType() {
        return "xls";
    }


    @Override
    public String getFilePath() {
        log.info("ExcelXlsFile:{}", path);
        return path;
    }
}
