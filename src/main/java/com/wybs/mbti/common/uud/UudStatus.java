package com.wybs.mbti.common.uud;

import com.wybs.mbti.common.data.StatusCode;

/**
 * <p>uud状态映射</p>
 *
 * <p>Date：2017-12-05</p>
 *
 * @author Mumus
 */
public class UudStatus {
    public static StatusCode getStatusCode(Integer uudRetCode) {
        if (uudRetCode == null) {
            return StatusCode.USER_UNKOWN_ERROR;
        }
        StatusCode status = null;

        switch (uudRetCode) {
            case 0:
                status = StatusCode.OK;
                break;
            case 1000:
                status = StatusCode.USER_EXISTED;
                break;
            case 1001:
                status = StatusCode.USER_PASSWORD_INVALID;
                break;
            case 1002:
                status = StatusCode.USER_VERIFY_CODE_ERROR;
                break;
            case 1003:
                status = StatusCode.USER_NOT_FOUND;
                break;
            case 1004:
                status = StatusCode.USER_LOGIN_ERROR;
                break;
            case 1005:
                status = StatusCode.USER_FORBIDDEN;
                break;
            case 1006:
                status = StatusCode.USER_SIGN_ERROR;
                break;
            case 1007:
                status = StatusCode.USER_PARAM_ERROR;
                break;
            case 1008:
                status = StatusCode.USER_IS_UNACTIVE;
                break;
            case 1009:
                status = StatusCode.USER_IS_FROZEN;
                break;
            case 2000:
                status = StatusCode.USER_UNKOWN_ERROR;
                break;
            case 3000:
                status = StatusCode.USER_NETWORK_ERROR;
                break;
            default:
                status = StatusCode.USER_UNKOWN_ERROR;
        }
        return status;
    }

    private UudStatus() {
        // private
    }
}
