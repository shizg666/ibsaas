package com.landleaf.ibsaas.web.web.vo;

import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.domain.leo.Resource;

import java.util.List;
import java.util.Map;

/**
 * @description resource交互对象
 * @author wyl
 * @date 2019/3/20 0020 16:59
 * @version 1.0
*/
public class ResourceVO extends Resource {

    List<Map<String,String>> languages = Lists.newArrayList();

    public List<Map<String, String>> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Map<String, String>> languages) {
        this.languages = languages;
    }
}
