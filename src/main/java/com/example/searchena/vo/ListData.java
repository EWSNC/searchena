package com.example.searchena.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListData {
    private String code;
    private String msg;
    private int count;
    private List<FileData> data;
}
