package com.kono.scannerbackend.exception;

import lombok.Getter;

/**
 * @author forlevinlee
 */

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR("-1", "系统异常"),
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),

    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    GATEWAY_ERROR("010500", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),

    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    INVALID_TOKEN("020001", "鉴权失败"),
    UPLOAD_FILE_SIZE_LIMIT("020010", "上传文件大小超过限制"),

    DUPLICATE_PRIMARY_KEY("030000","唯一键冲突"),

    UNAUTHORIZED("010401","用户未授权"),
    UNAUTHORIZED_ENABLED_EXPIRED("010402","不是有效用户或者账号已过期"),
    USER_NOT_FOUND("020404","用户未找到或已删除"),
    BIZ_ARGUMENT_INVALID_1("010405","终端标识code必填!"),
    BIZ_ARGUMENT_INVALID_2("010406","终端运行信息operatingParameters必填!"),
    BIZ_ARGUMENT_INVALID_3("010407","终端运行信息operatingParameters:JSON格式有误!"),
    BIZ_ARGUMENT_INVALID_4("010408","数据类型type必填!"),
    BIZ_ARGUMENT_INVALID_5("010409","数据类型type有误!"),
    BIZ_ARGUMENT_INVALID_6("010410","数据产生时间(或数据推送时间)engenderDate必填!"),
    BIZ_ARGUMENT_INVALID_7("010411","数据产生时间(或数据推送时间)engenderDate格式有误,正确格式为:yyyy-MM-dd HH:mm:ss,或者联系对接人员!"),

    /**
     * 成功
     */
    SUCCESS("200", "成功了"),
    /**
     * 失效或无效Token,此时需要登录
     */
    TOKEN_CHECK("302", "失效或无效Token,此时需要登录"),
    /**
     * 没有该用户,请联系管理员
     */
    USER_CHECK("303", "没有该用户,请联系管理员"),
    /**
     * 用户已被禁用,请联系管理员
     */
    USER_STATUS_CHECK("300", "用户已被禁用,请联系管理员"),
    /**
     * 参数(表单)校验失败
     */
    PARAM_CHECK("400", "参数(表单)校验失败"),
    /**
     * 后台处理业务逻辑失败或异常
     */
    ERROR("500", "后台处理业务逻辑失败或异常");

//    BIZ_ARGUMENT_INVALID("020405","节点代码officeCode必填!"),


    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String mesg;

    SystemErrorType(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }
}
