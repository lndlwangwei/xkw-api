package com.xkw.common;

/**
 * @author wangwei
 * @since 1.0
 */
public class GatewayException extends RuntimeException {
    private static final int DEFAULT_CODE = 400;
    private int code = DEFAULT_CODE;
    private int subCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSubCode() {
        return subCode;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    public GatewayException(String message) {
        this(message, DEFAULT_CODE);
    }

    public GatewayException(String message, int code) {
        this(message, code, 0);
    }

    public GatewayException(String message, int code, int subCode) {
        super(message);
        this.code = code;
        this.subCode = subCode;
    }

    public GatewayException(String message, Throwable cause) {
        this(message, cause, DEFAULT_CODE);
    }

    public GatewayException(String message, Throwable cause, int code) {
        this(message, cause, code, 0);
    }

    public GatewayException(String message, Throwable cause, int code, int subCode) {
        super(message, cause);
        this.code = code;
        this.subCode = subCode;
    }
}
