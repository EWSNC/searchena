package com.example.searchena.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileData {
    private static final String DEFAULT_OPT = "<a onClick=\"doSearch()\">发起</a>";

    private String id;
    private String fileName;
    private String filePath;
    private String uploadTime;

}
