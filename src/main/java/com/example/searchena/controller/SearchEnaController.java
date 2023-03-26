package com.example.searchena.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.searchena.service.SearchEnaService;
import com.example.searchena.util.IdempotentUtils;
import com.example.searchena.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequestMapping("searchena")
@Controller
public class SearchEnaController {

    @Autowired
    private SearchEnaService searchEnaService;


    @RequestMapping("beforeSearchEna")
    @ResponseBody
    public String beforeSearchEna(String params) {
        JSONArray array = JSONUtil.parseArray(params);
        for (Object o : array) {
            JSONObject jobj = new JSONObject(o);
            if (!IdempotentUtils.judge(String.valueOf(jobj.getStr("filePath")), this.getClass())) {
                return JsonResult.fail("正在执行");
            }
        }
        return JsonResult.ok();
    }


    @RequestMapping("doSearchEna")
    @ResponseBody
    public String doSearchEna(String params) {
        List<String> pathList = new ArrayList<>();
        JSONArray array = JSONUtil.parseArray(params);
        try {
            for (Object obj : array) {
                JSONObject jobj = (JSONObject) obj;
                pathList.add(jobj.getStr("filePath", ""));
            }
            return searchEnaService.doSearchEna(pathList);
        } catch (Exception e) {
            log.error("查询异常", e);
            return JsonResult.fail("查询异常");
        } finally {
            pathList.forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    IdempotentUtils.pop(s, this.getClass());
                }
            });
        }
    }
}
