package com.example.searchena.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.example.searchena.annation.SysLog;
import com.example.searchena.comm.filetype.impl.TxtFile;
import com.example.searchena.service.FileChannelService;
import com.example.searchena.util.CPathUtil;
import com.example.searchena.vo.FileData;
import com.example.searchena.vo.JsonResult;
import com.example.searchena.vo.ListData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileChannelServiceImpl implements FileChannelService {
    @Autowired
    private CPathUtil cPathUtil;

    @Override
    public String getFileList() {
        String filePath = cPathUtil.getFilePath(new TxtFile());
        if (!FileUtil.exist(filePath)) {
            return JSONUtil.toJsonStr(ListData.builder().count(0).code("1").msg("无文件").data(null).build());
        }
        List<File> files = Arrays.asList(FileUtil.ls(filePath));
        List<FileData> fileList = files.stream().map(file -> new FileData(
                String.valueOf(file.hashCode()),
                file.getName(),
                file.getAbsolutePath(),
                DateUtil.date(file.lastModified()).toString("yyyy-MM-dd hh:mm:ss"))
        ).collect(Collectors.toList());

        ListData resultList = ListData.builder().count(fileList.size()).code("0").msg("").data(fileList).build();

        return JSONUtil.toJsonStr(resultList);
    }

    @Override
    @SysLog("uploadFile")
    public String uploadFile(HttpServletRequest request) throws IOException {

        MultipartResolver resolver = new StandardServletMultipartResolver();
        MultipartHttpServletRequest mRequest = resolver.resolveMultipart(request);
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            saveFile(entry.getValue());
        }

        return JsonResult.ok().toString();
    }


    private void saveFile(MultipartFile value) throws IOException {
        String originalFilename = value.getOriginalFilename();
        log.info("save originalFilename:{}", originalFilename);

        String filePath = cPathUtil.getFilePath(originalFilename);
        String legalFileName = CPathUtil.popLegalFileName(filePath, originalFilename, FileUtil.mainName(originalFilename), FileUtil.extName(originalFilename), 0);

        FileUtil.writeBytes(value.getBytes(), FileUtil.file(filePath, legalFileName));
    }

}
