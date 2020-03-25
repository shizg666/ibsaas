package com.landleaf.ibsaas.client.parking.lifang.mq.enums;

import java.io.Serializable;

public interface BaseEnum extends Serializable {
	public static final String GET_NAME = "getName";
	public static final String GET_TYPE = "getType";

	public String getName();
	public int getType();
}
