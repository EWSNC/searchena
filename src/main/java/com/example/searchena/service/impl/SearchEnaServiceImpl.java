package com.example.searchena.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.searchena.annation.SysLog;
import com.example.searchena.comm.filetype.FileTypeSupport;
import com.example.searchena.comm.filetype.impl.ExcelXlsxFile;
import com.example.searchena.config.ConstantConfig;
import com.example.searchena.service.SearchEnaService;
import com.example.searchena.task.SearchEnaTask;
import com.example.searchena.util.CPathUtil;
import com.example.searchena.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Service
public class SearchEnaServiceImpl implements SearchEnaService {

    @Autowired
    private ConstantConfig config;
    @Autowired
    private CPathUtil cPathUtil;

    private ExecutorService executorService = ThreadUtil.newFixedExecutor(15, 1000, "T-Search", true);

    @Override
    @SysLog
    public String doSearchEna(List<String> txtFilePaths) {

        List<Map<String, Object>> modelList = new LinkedList<>();
        List<Future<Map<String, Object>>> futureList = new ArrayList<>(15);

        for (String txtFilePath : txtFilePaths) {
            if (!"txt".equals(FileUtil.extName(FileUtil.getName(txtFilePath)))) {
                log.info("文件格式不正确");
                continue;
            }

            ArrayList<String> assetList = getAssetListFromTextFile(txtFilePath);
            assetList.forEach(s -> {
                Future<Map<String, Object>> future = executorService.submit(new SearchEnaTask(s, config.getUrl()));
                futureList.add(future);
            });

            try {
                for (Future future : futureList) {
                    Map<String, Object> searchResult = (Map<String, Object>) future.get();
                    modelList.add(searchResult);
                }
            } catch (Exception e) {
                log.error("执行异常", e);
            }

            log.info("generate data size:{}", modelList.size());

            FileTypeSupport excelXlsxFile = new ExcelXlsxFile();
            String filePath = cPathUtil.getFilePath(excelXlsxFile) + FileUtil.mainName(DateUtil.currentSeconds() + "_" + FileUtil.getName(txtFilePath)) + "." + excelXlsxFile.getType();
            File file = FileUtil.file(filePath);
            try (ExcelWriter writer = ExcelUtil.getWriter(file)) {
                writer.write(modelList);
                writer.flush();
            } catch (Exception e) {
                log.error("Excel write error", e);
            }
        }

        return JsonResult.ok();
    }

    private ArrayList<String> getAssetListFromTextFile(String txtFilePath) {
        ArrayList<String> srcList = new ArrayList<>(300);
        try (BufferedInputStream inputStream = FileUtil.getInputStream(txtFilePath);
             InputStreamReader isr = new InputStreamReader(inputStream);
             BufferedReader br = new BufferedReader(isr)) {

            String line = null;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                if (lineNum <= 1) {
                    if (!line.contains("sample_accession")) {
                        log.info("数据格式不正确:{}", txtFilePath);
                        break;
                    }
                } else {
                    srcList.add(line);
                }
                lineNum++;
            }

        } catch (IOException e) {
            log.error("IO异常", e);
        }

        ArrayList<String> assetList = new ArrayList<>();
        if (srcList.isEmpty()) {
            return assetList;
        }
        srcList.forEach(s -> {
            assetList.add(s.split("\t")[1].trim());
        });
        return assetList;
    }
}
