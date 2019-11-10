package com.landleaf.ibsaas.web.web.controller.light;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.ibsaas.common.domain.BasePageVO;
import com.landleaf.ibsaas.common.domain.ChoiceButton;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipSearchVO;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.common.domain.light.TLightType;
import com.landleaf.ibsaas.common.domain.light.vo.ProductReponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.QueryProductVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightProductVO;
import com.landleaf.ibsaas.common.enums.light.LightProcotolEnum;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ITLightProductService;
import com.landleaf.ibsaas.common.domain.light.vo.QueryLightProductVO;
import com.landleaf.ibsaas.web.web.service.light.ITLightTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光产品操作")
public class LightProductController extends BasicController {
    @Autowired
    private ITLightProductService itLightProductService;
    @Autowired
    private ITLightTypeService itLightTypeService;


    @GetMapping("/product/initQueryCondition")
    @ApiOperation(value = "初始化产品列表查询条件数据")
    public Response<QueryProductVO> initQueryCondition(){
        List<TLightProduct> tLightProducts = itLightProductService.getProducList();
        List<ChoiceButton> brand2 = Lists.newArrayList();
        List<ChoiceButton> model2 = Lists.newArrayList();
        tLightProducts.forEach(obj->{
                ChoiceButton choiceButtonB = new ChoiceButton();
                choiceButtonB.setChoiceKey(obj.getBrand());
                choiceButtonB.setChoiceValue(obj.getBrand());
                brand2.add(choiceButtonB);
                ChoiceButton choiceButtonM = new ChoiceButton();
                choiceButtonM.setChoiceKey(obj.getModel());
                choiceButtonM.setChoiceValue(obj.getModel());
                model2.add(choiceButtonM);
        });
        List<ChoiceButton> brand = brand2.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ChoiceButton::getChoiceKey))), ArrayList::new));
        List<ChoiceButton> model = model2.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ChoiceButton::getChoiceKey))), ArrayList::new));
        List<ChoiceButton> protocols = Lists.newArrayList();
        for (LightProcotolEnum procotolEnum : LightProcotolEnum.values()) {
            ChoiceButton choiceButton = new ChoiceButton();
            choiceButton.setChoiceKey(procotolEnum.getType());
            choiceButton.setChoiceValue(procotolEnum.getName());
            protocols.add(choiceButton);
        }
        QueryProductVO queryProductVO = new QueryProductVO();
        queryProductVO.setBrand(brand);
        queryProductVO.setModel(model);
        queryProductVO.setProtocol(protocols);
        List<TLightType> tLightTypes = itLightTypeService.getTypeList();
//        Map<Long, String> type = tLightTypes.stream().collect(Collectors.toMap(TLightType::getId, TLightType::getName));
        List<ChoiceButton> choiceButtons = Lists.newArrayList();
        tLightTypes.forEach(obj->{
            ChoiceButton choiceButton = new ChoiceButton();
            choiceButton.setChoiceKey(String.valueOf(obj.getId()));
            choiceButton.setChoiceValue(obj.getName());
            choiceButtons.add(choiceButton);
        });
        queryProductVO.setType(choiceButtons);
        return returnSuccess(queryProductVO);
    }


    @GetMapping("/product/getProductRecord")
    @ApiOperation(value = "灯光产品列表分页查询", notes = "灯光产品列表分页查询")
    public Response<TLightProductVO> getProductRecord(QueryLightProductVO requestBody) {
        PageInfo<TLightProductVO> data = itLightProductService.getProductRecordByCondition(requestBody);
        BasePageVO<TLightProductVO> result = new BasePageVO<>(data.getList(), data.getTotal());
        return returnSuccess(result);
    }

    @GetMapping("/product/getProducList")
    @ApiOperation(value = "获取灯光产品列表", notes = "获取灯光产品列表")
    public Response<List<ChoiceButton>> getProducList() {
        List<TLightProduct> tLightProducts = itLightProductService.getProducList();
//        Map<Long, String> data = tLightProducts.stream().collect(Collectors.toMap(TLightProduct::getId, TLightProduct::getName));
        List<ChoiceButton> choiceButtons = Lists.newArrayList();
        tLightProducts.forEach(obj->{
            ChoiceButton choiceButton = new ChoiceButton();
            choiceButton.setChoiceKey(String.valueOf(obj.getId()));
            choiceButton.setChoiceValue(obj.getName());
            choiceButtons.add(choiceButton);
        });
        return returnSuccess(choiceButtons);
    }

    @GetMapping("/type/getTypeList")
    @ApiOperation(value = "获取灯光类型列表", notes = "获取灯光产品列表")
    public Response<List<ChoiceButton>> getTypeList() {
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




    @GetMapping("/product/{id}")
    @ApiOperation(value = "根据产品id查询产品信息")
    public Response<ProductReponseVO> getProcutById(@PathVariable Long id){
        ProductReponseVO productReponseVO = itLightProductService.getProcutById(id);
        return returnSuccess(productReponseVO);
    }

    @ApiOperation(value = "添加或者修改灯光产品信息", notes = "添加或者修改灯光产品信息")
    @PostMapping(value = "/product/addOrUpdateProduct")
    public Response<TLightProduct> addOrUpdateProduct(@RequestBody @ApiParam TLightProduct tLightProduct) {
        TLightProduct result = itLightProductService.addOrUpdateProduct(tLightProduct);
        String message;
        if (tLightProduct.getId() == null || tLightProduct.getId() == 0L) {
            message = "添加成功！";
        } else {
            message = "修改成功！";
        }
        return returnSuccess(result, message);
    }

    @PostMapping("/product/delete/{id}")
    @ApiOperation(value = "根据id删除灯光产品", notes = "")
    public Response deleteProduct(@PathVariable @ApiParam(name = "id", value = "产品id", required = true) Long id) {
        Integer result = itLightProductService.deleteProduct(id);
        return returnSuccess(result, MessageConstants.COMMON_DELETE_SUCCESS_MESSAGE);
    }


}
