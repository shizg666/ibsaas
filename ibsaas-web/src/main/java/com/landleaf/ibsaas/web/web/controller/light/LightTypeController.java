package com.landleaf.ibsaas.web.web.controller.light;


import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.common.domain.light.TLightType;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ITLightTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光类型操作")

public class LightTypeController extends BasicController {
    @Autowired
    private ITLightTypeService itLightTypeService;

    @GetMapping("/type/getProducList")
    @ApiOperation(value = "获取灯光类型列表", notes = "获取灯光类型列表")
    public Response<List<ChoiceButton>>  getTypeList() {
        List<TLightType> tLightProducts = itLightTypeService.getTypeList();
//        Map<Long, String> data = tLightProducts.stream().collect(Collectors.toMap(TLightType::getId, TLightType::getName));
        List<ChoiceButton> choiceButtons = Lists.newArrayList();
        tLightProducts.forEach(obj->{
            ChoiceButton choiceButton = new ChoiceButton();
            choiceButton.setChoiceKey(String.valueOf(obj.getId()));
            choiceButton.setChoiceValue(obj.getName());
            choiceButtons.add(choiceButton);
        });
        return returnSuccess(choiceButtons);
    }
}
