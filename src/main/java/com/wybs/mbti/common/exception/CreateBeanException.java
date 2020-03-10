package com.wybs.mbti.common.exception;

/**
 * <p>创建bean异常</p>
 *
 * <p>Date：2017-12-01</p>
 *
 * @author mumus
 */
public class CreateBeanException extends RuntimeException {
    private static final long serialVersionUID = 6808214687972016467L;

    public CreateBeanException() {
        super();
    }

    public CreateBeanException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CreateBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateBeanException(String message) {
        super(message);
    }

    public CreateBeanException(Throwable cause) {
        super(cause);
    }

}
