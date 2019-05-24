package com.landleaf.ibsaas.web.web.exception;


import com.landleaf.ibsaas.common.exception.BusinessException;

/**
 * @Description: 停车业务操作相关异常
 */
public class ParkingException extends BusinessException {

    private static final long serialVersionUID = -4456686339387898066L;

    public static final String LOST_SESSION_WITH_TCP_SERVER = "lost_session_with_tcp_server";
    public static final String GET_DATA_TIMEOUT_FROM_SERVER = "get_data_timeout_from_server";


    public static final String PARKINGREALCOUNTINIT_UPDATE_NOT_EXISTS = "parkingrealcountinit_update_not_exists";



    public ParkingException(String errCode) {
        super(errCode);
        super.errCode = errCode;
    }

    public ParkingException(String errCode, String msg) {
        super(errCode, msg);
    }

    public ParkingException(String errCode, Object... args) {
        super(errCode, args);
    }
}
