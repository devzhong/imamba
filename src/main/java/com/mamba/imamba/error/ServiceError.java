package com.mamba.imamba.error;

import com.ne.boot.common.exception.IError;

public enum ServiceError implements IError{

    SERVICE_ERROR("9999", "内部服务错误"),
    USERNAME_EXIST("10000", "用户名已存在"),
    PHONE_EXIST("10001", "手机号已存在"),
    EMAIL_EXIST("10002", "邮箱已存在"),
    USERNAME_NULL("10003","用户名不能为空"),
    PASSWORD_NULL("10004", "密码不能为空"),
    STATUS_NULL("10005", "用户状态不能为空"),
    USER_NOT_EXIST("10006", "用户不存在"),
    USER_STATUS_ERROR("1007", "用户未启用"),
    PHONE_OR_PASSWORD_INCORECT("1008", "手机号或密码错误"),
    PHONE_NULL("1009", "手机号不能为空"),
    UID_NULL("1010", "UID不能为空"),
    VALUE_NULL("1011", "充值水滴数不能为空"),
    ;

    String errorCode;
    String errorMessage;
    private static final String ns = "usermanage";

    ServiceError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public String getNamespace() {
        return ns;
    }

    @Override
    public String getErrorCode() {
        return ns + "_" + errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }


}
