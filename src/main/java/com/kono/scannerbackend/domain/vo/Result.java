package com.kono.scannerbackend.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kono.scannerbackend.exception.BaseException;
import com.kono.scannerbackend.exception.ErrorType;
import com.kono.scannerbackend.exception.SystemErrorType;
import lombok.Getter;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author Administrator
 */
@Getter
public class Result<T> {

    public static final String SUCCESSFUL_CODE = "200";
    public static final String SUCCESSFUL_MSG = "success";

    private String code;
    private String msg;
    private Instant time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Result() {
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param errorType
     */
    public Result(ErrorType errorType) {
        this.code = errorType.getCode();
        this.msg = errorType.getMesg();
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param msg
     */
    public Result(String msg) {
        this.code = SUCCESSFUL_CODE;
        this.msg = msg;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * @param msg
     */
    public Result(String msg, T data) {
        this.code = SUCCESSFUL_CODE;
        this.msg = msg;
        this.time = ZonedDateTime.now().toInstant();
        this.data = data;
    }

    /**
     * @param errorType
     * @param data
     */
    public Result(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    /**
     * 内部使用，用于构造成功的结果
     *
     * @param code
     * @param msg
     * @param data
     */
    private Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = ZonedDateTime.now().toInstant();
    }

    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result success(Object data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MSG, data);
    }

    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @return Result
     */
    public static Result fail() {
        return new Result(SystemErrorType.SYSTEM_ERROR);
    }

    /**
     * 系统异常类没有返回数据
     *
     * @param baseException
     * @return Result
     */
    public static Result fail(BaseException baseException) {
        return fail(baseException, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result fail(BaseException baseException, Object data) {
        return new Result<>(baseException.getErrorType(), data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @param data
     * @return Result
     */
    public static Result fail(ErrorType errorType, Object data) {
        return new Result<>(errorType, data);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param errorType
     * @return Result
     */
    public static Result fail(ErrorType errorType) {
        return Result.fail(errorType, null);
    }

    /**
     * 系统异常类并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result fail(Object data) {
        return new Result<>(SystemErrorType.SYSTEM_ERROR, data);
    }


    /**
     * 成功code=000000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }
}
