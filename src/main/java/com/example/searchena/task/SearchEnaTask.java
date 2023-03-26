package com.example.searchena.task;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
public class SearchEnaTask implements Callable {
    private String searchCode;
    private String enaUrl;
    private String searchUrl;

    public SearchEnaTask(String searchCode, String enaUrl) {
        this.searchCode = searchCode;
        this.enaUrl = enaUrl;
        searchUrl = enaUrl + searchCode;
    }

    @Override
    public Object call() {
        return doSearch();
    }

    private Object doSearch() {
        log.info("=>" + searchCode);
        String result = HttpUtil.get(searchUrl, StandardCharsets.UTF_8);

        Map<String, Object> dataMap = XmlUtil.xmlToMap(result);
        Map<String, Object> sample = MapUtil.get(dataMap, "SAMPLE", Map.class);
        Map<String, Object> sample_attributes = MapUtil.get(sample, "SAMPLE_ATTRIBUTES", Map.class);
        ArrayList<Map<String, Object>> sample_attribute = MapUtil.get(sample_attributes, "SAMPLE_ATTRIBUTE", ArrayList.class);

        Map<String, Object> datas = new HashMap<>();
        for (Map<String, Object> valueMap : sample_attribute) {
            MapUtils.safeAddToMap(datas, MapUtils.getString(valueMap, "TAG"), valueMap.get("VALUE"));
        }
        log.info(datas.toString());

        return datas;
    }
}
