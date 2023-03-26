package com.example.searchena.util;

import cn.hutool.core.io.FileUtil;
import com.example.searchena.comm.filetype.FileTypeSupport;
import com.example.searchena.comm.filetype.impl.DefaultFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class CPathUtil {
    @Autowired
    private List<FileTypeSupport> supportList;

    public static final String PATH_ROOT = System.getProperty("user.dir");
    public static final String PATH_FILE = File.separator + "file";
    public static final String PATH_PIC = PATH_FILE + File.separator + "pic" + File.separator;
    public static final String PATH_TXT = PATH_FILE + File.separator + "txt" + File.separator;
    public static final String PATH_EXE = PATH_FILE + File.separator + "excel" + File.separator;


    public String getFilePath(String originalFilename) {
        String extName = FileUtil.extName(originalFilename);
        Optional<FileTypeSupport> optionalFileTypeSupport = supportList.stream().filter(fileTypeSupport -> (Objects.equals(fileTypeSupport.getType(), extName) || Objects.equals(fileTypeSupport.getType(), "default"))).findAny();
        String filePath = optionalFileTypeSupport.orElse(new DefaultFile()).getFilePath();
        return filePath;
    }

    public String getFilePath(FileTypeSupport typeIns) {
        return typeIns.getFilePath();
    }

    public static String popLegalFileName(String path, String targetFileName, String mainName, String extName, int times) {
        if (!FileUtil.file(path, targetFileName).exists()) {
            log.info("legalFilename:{}", targetFileName);
            return targetFileName;
        } else {
            log.info("file:{} exists", targetFileName);
            times++;
            targetFileName = mainName + "(" + times + ")" + "." + extName;
            return popLegalFileName(path, targetFileName, mainName, extName, times);
        }
    }
}
