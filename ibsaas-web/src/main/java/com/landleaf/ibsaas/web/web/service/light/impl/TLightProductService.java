package com.landleaf.ibsaas.web.web.service.light.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.light.TLightDeviceDao;
import com.landleaf.ibsaas.common.dao.light.TLightProductDao;
import com.landleaf.ibsaas.common.domain.light.TLightDevice;
import com.landleaf.ibsaas.common.domain.light.TLightProduct;
import com.landleaf.ibsaas.common.domain.light.TLightType;
import com.landleaf.ibsaas.common.domain.light.vo.ProductReponseVO;
import com.landleaf.ibsaas.common.domain.light.vo.TLightProductVO;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.datasource.mybatis.service.AbstractBaseService;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.service.light.ITLightProductService;
import com.landleaf.ibsaas.common.domain.light.vo.QueryLightProductVO;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TLightProductService extends AbstractBaseService<TLightProductDao, TLightProduct> implements ITLightProductService<TLightProduct> {
    @Autowired
    private TLightDeviceDao tLightDeviceDao;
    @Autowired
    private TLightTypeService tLightTypeService;


    @Override
    public TLightProduct addOrUpdateProduct(TLightProduct tLightProduct) {
        TLightProduct tLightProduct1;
        if (tLightProduct.getId() == null || tLightProduct.getId() == 0L){
            tLightProduct1 = addProduct(tLightProduct);
        }else {
            tLightProduct1 = updateProduct(tLightProduct);
        }
        return tLightProduct1;
    }

    @Override
    public Integer deleteProduct(Long id) {
        TLightDevice tLightDevice = tLightDeviceDao.getDeviceByProductId(id);
        if (tLightDevice != null){
            throw new BusinessException("该产品下面尚有设备存在！");
        }
        Integer result = deleteByPrimaryKey(id);
        if (result < 0 ) {
            throw new BusinessException("产品删除失败");
        }
        return result;
    }

    @Override
    public PageInfo<TLightProductVO> getProductRecordByCondition(QueryLightProductVO requestBody) {
        PageHelper.startPage(requestBody.getPage(), requestBody.getLimit(), true);
        Example example = new Example(TLightProduct.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(requestBody.getType())) {
            criteria.andEqualTo("type", requestBody.getType());
        }
        if (StringUtils.isNotBlank(requestBody.getProtocol())) {
            criteria.andEqualTo("protocol", requestBody.getProtocol());
        }
        if (StringUtils.isNotBlank(requestBody.getBrand())) {
            criteria.andEqualTo("brand", requestBody.getBrand());
        }
        if (StringUtils.isNotBlank(requestBody.getModel())) {
            criteria.andEqualTo("model", requestBody.getModel());
        }
        if (StringUtils.isNotBlank(requestBody.getName())) {
            criteria.andLike("name", "%" + requestBody.getName() + "%"); //模糊查询
        }
        List<TLightProduct> lightProducts = selectByExample(example);
        if (CollectionUtils.isEmpty(lightProducts)) {
            return  new PageInfo(Lists.newArrayListWithCapacity(0));
        }
        List<TLightProductVO> tLightProductVOS = Lists.newArrayListWithCapacity(lightProducts.size());
        List<Long> typeIds = lightProducts.stream().map(TLightProduct::getTypeId).collect(Collectors.toList());
        List<TLightType> lightTypes = tLightTypeService.getTypeListByIds(typeIds);
        Map<Long, String> collect = lightTypes.stream().collect(Collectors.toMap(TLightType::getId,TLightType::getName));

        lightProducts.forEach(obj->{
            TLightProductVO tLightProductVO = new TLightProductVO();
            BeanUtils.copyProperties(obj,tLightProductVO);
            tLightProductVO.setType(collect.get(obj.getTypeId()));
            tLightProductVOS.add(tLightProductVO);
        });

        return new PageInfo(tLightProductVOS);
    }

    @Override
    public List<TLightProduct> getProducList() {
        List<TLightProduct> tLightProducts = selectAll();
        if (tLightProducts == null){
            return Lists.newArrayList();
        }
        return tLightProducts;
    }

    @Override
    public ProductReponseVO getProcutById(Long id) {
        TLightProduct tLightProduct = selectByPrimaryKey(id);
        if (tLightProduct == null){
            throw  new BusinessException("产品不存在！id:{}",id);
        }
        ProductReponseVO productReponseVO = new ProductReponseVO();
        BeanUtils.copyProperties(tLightProduct,productReponseVO);
        if (tLightProduct.getTypeId() != null){
            TLightType tLightType = tLightTypeService.selectByPrimaryKey(tLightProduct.getTypeId());
            productReponseVO.setType(tLightType.getName());
        }
        return productReponseVO;
    }


    public TLightProduct addProduct(TLightProduct tLightProduct) {
        Date date = new Date();
        tLightProduct.setCtime(date);
        tLightProduct.setUtime(date);
        Integer result = saveSelective(tLightProduct);
        if (result < 0 ){
            throw new BusinessException("灯光产品添加失败");
        }
        return tLightProduct;
    }
    public TLightProduct updateProduct(TLightProduct tLightProduct) {
        Integer result = updateByPrimaryKeySelective(tLightProduct);
        if (result < 0 ){
            throw new BusinessException("灯光产品修改失败");
        }
        return tLightProduct;
    }
}
