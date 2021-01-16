package com.HYK.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * http响应json数据，前后端统一约定的json格式
 * 前端响应状态码是200
 *
 *
 */
@Getter
@Setter
@ToString
public class JSONResponse {
    //业务操作是否成功
    private boolean success;
    //业务操作码信息
    private String code;
    //业务操作的错误信息 给客户看
    private String message;
    //业务数据
    private Object data;

}
