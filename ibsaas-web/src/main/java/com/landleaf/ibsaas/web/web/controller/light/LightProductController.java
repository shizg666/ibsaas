package com.landleaf.ibsaas.web.web.controller.light;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.BasePageVO;
import com.landleaf.ibsaas.common.domain.Response;
import com.landleaf.ibsaas.common.domain.energy.vo.EnergyEquipSearchVO;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.controller.BasicController;
import com.landleaf.ibsaas.web.web.service.light.ITLightProductService;
import com.landleaf.ibsaas.common.domain.light.vo.QueryLightProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/light")
@Api(value = "/light", description = "灯光产品操作")
public class LightProductController extends BasicController {
    @Autowired
    private ITLightProductService itLightProductService;


    @PostMapping("/product/getProductRecord")
    @ApiOperation(value = "灯光产品列表分页查询", notes = "灯光产品列表分页查询")
    public Response getProductRecord(@RequestBody QueryLightProductVO requestBody) {
        PageInfo<TLightProduct> data = itLightProductService.getProductRecordByCondition(requestBody);
        BasePageVO<TLightProduct> result = new BasePageVO<>(data.getList(), data.getTotal());
        return returnSuccess(result);
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
