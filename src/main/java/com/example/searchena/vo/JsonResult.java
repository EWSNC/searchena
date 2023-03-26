package com.example.searchena.vo;

import cn.hutool.json.JSONUtil;
import lombok.Data;

@Data
public class JsonResult {

    enum Status {
        OK(0, "成功"), FAIL(1, "失败"), ERROR(2, "异常");
        private Integer code;
        private String alia;

        Status(int i, String alia) {
            this.code = i;
            this.alia = alia;
        }
    }

    public JsonResult(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Integer status;
    private String msg;

    public static String ok() {
        return JSONUtil.toJsonStr(new JsonResult(Status.OK.code, Status.OK.alia)) ;
    }

    public static String ok(String msg) {
        return JSONUtil.toJsonStr(new JsonResult(Status.OK.code, msg));
    }

    public static String fail() {
        return JSONUtil.toJsonStr(new JsonResult(Status.FAIL.code, Status.FAIL.alia));
    }

    public static String fail(String msg) {
        return JSONUtil.toJsonStr(new JsonResult(Status.FAIL.code, msg));
    }

    public static String error() {
        return JSONUtil.toJsonStr(new JsonResult(Status.ERROR.code, Status.ERROR.alia));
    }

    public static String error(String msg) {
        return JSONUtil.toJsonStr(new JsonResult(Status.ERROR.code, msg));
    }


}
