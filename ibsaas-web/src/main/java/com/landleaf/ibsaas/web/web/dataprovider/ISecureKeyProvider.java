package com.landleaf.ibsaas.web.web.dataprovider;

/**
 * @Title: ISecureKeyProvider
 * @Description: 根据apiKey获取私钥数据
 */
public interface ISecureKeyProvider {

    /**
     * 根据apiKey获取secureKey
     * @param apiKey
     */
    String getSecureKey(String apiKey);

}
