package com.landleaf.ibsaas.web.web.service.leo.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.ibsaas.common.dao.leo.ResourceDao;
import com.landleaf.ibsaas.common.domain.leo.Resource;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.exception.BusinessException;
import com.landleaf.ibsaas.common.service.leo.ICommonResourceService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.constant.MessageConstants;
import com.landleaf.ibsaas.web.web.context.RequestContext;
import com.landleaf.ibsaas.web.web.context.user.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.dto.response.SelectorResultDto;
import com.landleaf.ibsaas.web.web.exception.ResourceException;
import com.landleaf.ibsaas.web.web.service.leo.IResourceService;
import com.landleaf.ibsaas.web.web.vo.TreeNodeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourceService implements IResourceService {


    @Autowired
    private ICommonResourceService<Resource> commonResourceService;

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private LocaleResolver localeResolver;
    /**
     * @param systemCode
     * @return java.util.List<Resource>
     * @description 加载所有的子菜单
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    public List<Resource> listSystemAllResources(String systemCode) {
        return null;
    }
    /**
     * @param systemCode
     * @param userCode
     * @return java.util.List<TreeNodeVO>
     * @description 加载用户所有可用菜单
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    public List<TreeNodeVO> listUserAllResources(String systemCode, String userCode) {
        //根据当前cookie获取语言种类
        String language = getCurrentLanguage();
        Set<Resource> allResource = commonResourceService.listUserAllResources(systemCode, userCode);
        getResourcesBoundLanguage(language, allResource);
        //将菜单进行转换成前台菜单需要的
        return convertResources2Tree(allResource);
    }
    /**
     * @param resourceCode
     * @param belongSystem
     * @return com.landleaf.leo.common.domain.Resource
     * @description 根据权限编码查询权限详细信息
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    public Resource findResourceByCode(String resourceCode, String belongSystem) {
        Resource resource = new Resource();
        resource.setResourceCode(resourceCode);
        resource.setBelongSystem(belongSystem);
        resource.setActive(1);
        return resourceDao.selectOne(resource);
    }
    /**
     * @param systemCode
     * @param resourceName
     * @return java.util.Map<java.lang.String,java.util.List<java.util.List<java.util.Map<java.lang.String,java.lang.String>>>>
     * @description 根据所属系统编码权限名称模糊查询权限列表
     * @author wyl
     * @date 2019/3/20 0020 17:20
     * @version 1.0
     */
    @Override
    public Map<String, List<List<Map<String, String>>>> findResourceByNameToDisplay(String systemCode, String resourceName) {
        Map<String, List<List<Map<String, String>>>> map = Maps.newHashMap();
        List results = Lists.newArrayList();
        map.put("list", results);
        Set<Resource> allResource = findUsersResourceByCode(systemCode);
        String language = getCurrentLanguage();
        getResourcesBoundLanguage(language, allResource);
        List<Resource> resources = new ArrayList();
        if (!CollectionUtils.isEmpty(allResource) && !StringUtils.isBlank(resourceName)) {
            resources = allResource.stream().filter(item -> item.getResourceName().contains(resourceName)
                    || StringUtils.equals(item.getResourceName(), resourceName)).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(resources)) {
            resources.forEach(item -> {
                Map<String, String> hasmap = new HashMap<>();
                hasmap.put("value", item.getResourceCode());
                hasmap.put("displayName", item.getResourceName());
                results.add(hasmap);
            });
        }
        return map;
    }
    /**
     * @param resource
     * @return com.landleaf.leo.common.domain.Resource
     * @description 新增权限
     * @author wyl
     * @date 2019/3/20 0020 17:21
     * @version 1.0
     */
    @Transactional
    public Resource addResource(Resource resource) {
        //校验必须参数
        if (resource == null
                || StringUtils.isBlank(resource.getResourceCode())
                || StringUtils.isBlank(resource.getResourceName())
                || resource.getResourceLevel() == null
                || resource.getResourceType() == null) {
            throw new BusinessException(MessageConstants.COMMON_ADD_RESOURCE_BADREQUEST);
        }
        Resource existResByCode = findResourceByCode(resource.getResourceCode(), resource.getBelongSystem());
        //权限编码与所属系统确定唯一性
        if (existResByCode != null) {
            throw new BusinessException(MessageConstants.COMMON_ADD_RESOURCE_CONFLICT);
        }
        //需增加校验,如果entry-uri不为空，需保证该系统下该uri唯一
        if (!StringUtils.isBlank(resource.getEntryUri())) {
            Resource existResByUri = findResourceBySystemAndUri(resource.getBelongSystem(), resource.getEntryUri());
            if (existResByUri != null) {
                throw new ResourceException(ResourceException.BUSINESS_RESOURCE_ENTRY_URI_CONFLICT);
            }
        }
        //新增初始化参数
        Date oprTime = new Date();
        String id = idGenerator.nextId();
        resource.setId(id);
        resource.setCreateTime(oprTime);
        resource.setCreateUserCode("1");
        resource.setActive(1);
        resource.setVersionNo(oprTime.getTime());
        commonResourceService.save(resource);
        return resource;
    }


    @Override
    @Transactional
    public Resource updateResource(String id, Resource resource) {
        /**
         * 更新逻辑
         * 1.先删除后插入
         * 2.删除时必须是从可用状态改为不可用状态
         * 3.更新影响行数必须不等于0
         */
        Resource existResource =  findResourceByCode(resource.getResourceCode(),resource.getBelongSystem());
        if(existResource==null){
            throw new ResourceException(ResourceException.BUSINESS_RESOURCE_UPDATE_RESOURCE_NOT_EXISTS);
        }

        //更新先删除
        Resource updateResource = new Resource();
        initUpdateParams(updateResource);
        updateResource.setId(resource.getId());
        updateResource.setActive(IbsaasWebConstants.INACTIVE);
        Example example = new Example(Resource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE).andCondition("id=", resource.getId());
        int res = commonResourceService.updateByExampleSelective(updateResource, example);
        if (res == 0) {
            throw new ResourceException(ResourceException.BUSINESS_RESOURCE_UPDATE_RESOURCE_NOT_EXISTS);
        }
        //需增加校验,如果entry-uri不为空，需保证该系统下该uri唯一

        if (!StringUtils.isBlank(resource.getEntryUri())) {
            Resource existResByUri = findResourceBySystemAndUri(resource.getBelongSystem(), resource.getEntryUri());
            if (existResByUri != null)
                throw new ResourceException(ResourceException.BUSINESS_RESOURCE_ENTRY_URI_CONFLICT);
        }

        //重新插入
        Resource targetResource = new Resource();
        packageUpdateResource(existResource,resource,targetResource);

        commonResourceService.save(targetResource);
        //返回新插入数据
        Resource queryParam = new Resource();
        queryParam.setId(targetResource.getId());
        return commonResourceService.selectOne(queryParam);
    }

    private void packageUpdateResource(Resource existResource, Resource sourceResource, Resource targetResource) {
        BeanUtils.copyProperties(sourceResource, targetResource);
        Date oprtime = new Date();
        targetResource.setId(idGenerator.nextId());
        targetResource.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        targetResource.setModifyTime(oprtime);
        targetResource.setVersionNo(oprtime.getTime());
        targetResource.setActive(IbsaasWebConstants.ACTIVE);
        targetResource.setCreateTime(existResource.getCreateTime());
        targetResource.setCreateUserCode(existResource.getCreateUserCode());
    }



    @Override
    @Transactional
    public void deleteResource(String id) {
        Date oprTime = new Date();
        Resource updateParam = new Resource();
        updateParam.setId(id);
        //更新状态
        updateParam.setActive(IbsaasWebConstants.INACTIVE);
        updateParam.setModifyTime(oprTime);
        updateParam.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        updateParam.setVersionNo(oprTime.getTime());
        commonResourceService.updateByPrimaryKeySelective(updateParam);
    }
    @Override
    @Transactional
    public void deleteResourceByCodes(String[] resourceCodes) {
        resourceDao.deleteResourceByCodes(resourceCodes);
    }

    @Override
    public List<TreeNodeVO> listAllResourcesBySystem(String systemCode) {
        List<TreeNodeVO> results = Lists.newArrayList();
        Set<Resource> allResource = new HashSet<Resource>(); //所有菜单的去重集合
        allResource = findUsersResourceByCode(systemCode);
        if (!CollectionUtils.isEmpty(allResource)) {
            //将菜单进行转换成前台菜单需要的
            String language = getCurrentLanguage();//根据当前cookie获取语言种类
            getResourcesBoundLanguage(language, allResource);
            results = convertResources2Tree(allResource);
        }
        return results;
    }

    @Override
    public PageInfo queryResource(SelectorParamsDto paramsDto) {
        PageHelper.startPage(paramsDto.getPage(), paramsDto.getLimit(), true);
        Example example = new Example(Resource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE);
        if (StringUtils.isNotBlank(paramsDto.getQuery())) {
            criteria.andLike("resourceName", "%" + paramsDto.getQuery() + "%"); //模糊查询
        }
        //渲染选择器使用
        List<Resource> resourceList = commonResourceService.selectByExample(example);
        List<SelectorResultDto> resultDtoList = new ArrayList<>(resourceList.size());
        if (!CollectionUtils.isEmpty(resourceList)) {
            resourceList.forEach(item -> {
                SelectorResultDto resultDto = new SelectorResultDto();
                resultDto.setValue(item.getResourceCode());
                resultDto.setDisplayName(item.getResourceName());
                resultDtoList.add(resultDto);
            });
        }
        return new PageInfo(resultDtoList);
    }

    private Set<Resource> findUsersResourceByCode(String systemCode) {
        Set<Resource> allResource = new HashSet<>(); //所有菜单的去重集合
        Resource queryParam = new Resource();
        queryParam.setActive(IbsaasWebConstants.ACTIVE);
        queryParam.setBelongSystem(systemCode);
        List<Resource> resources = commonResourceService.select(queryParam);
        if (resources != null) {
            allResource.addAll(resources);
        }
        return allResource;
    }

    private Resource findResourceBySystemAndUri(String belongSystem, String entryUri) {
        Resource queryParam = new Resource();
        queryParam.setActive(IbsaasWebConstants.ACTIVE);
        queryParam.setEntryUri(entryUri);
        queryParam.setBelongSystem(belongSystem);
        return commonResourceService.selectOne(queryParam);
    }

    private void initAddParams(Resource resource) {
        String oprUser = ((User) UserContext.getCurrentUser()).getUserCode();
        Date oprTime = new Date();
        resource.setId(idGenerator.nextId());
        resource.setCreateUserCode(oprUser);
        resource.setCreateTime(oprTime);
        resource.setModifyUserCode(oprUser);
        resource.setModifyTime(oprTime);
        resource.setActive(IbsaasWebConstants.ACTIVE);
        resource.setVersionNo(oprTime.getTime());
        resource.setModifyTime(null);
        resource.setModifyUserCode(null);
    }

    private void initUpdateParams(Resource resource) {
        Date oprtime = new Date();
        resource.setModifyTime(oprtime);
        resource.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        resource.setVersionNo(oprtime.getTime());
    }


    private void getResourcesBoundLanguage(String language, Set<Resource> allResource) {
       //TODO
    }


    private String getCurrentLanguage() {
        String language = "";
        if (localeResolver instanceof CookieLocaleResolver) {
            CookieLocaleResolver cookieLocaleResolver = (CookieLocaleResolver) localeResolver;
            String cookieName = cookieLocaleResolver.getCookieName();
            Cookie[] cookies = RequestContext.getRequest().getCookies();
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookieName, cookie.getName())) {
                    language = cookie.getValue();
                }
            }
        }
        return language;
    }


    private List<TreeNodeVO> convertResources2Tree(Set<Resource> resources) {
        if (resources == null) {
            return null;
        }
        List<TreeNodeVO> tree = new ArrayList<TreeNodeVO>(resources.size());
        setChildren(tree, resources, null, null);
        Collections.sort(tree);
        return tree;
    }

    private void setChildren(List<TreeNodeVO> tree, Set<Resource> resources, Resource parentResource, TreeNodeVO parentNode) {
        for (Resource resource : resources) {
            //添加第一层目录
            if (parentResource == null) {
                if (StringUtils.isEmpty(resource.getParentCode())) {
                    TreeNodeVO node = convertResource2TreeNode(resource);
                    tree.add(node);
                    //同时递归设置下层目录
                    setChildren(tree, resources, resource, node);
                }
            } else {
                //将节点的上层目录和给的的父级目录相同时，添加为其子节点
                if (StringUtils.equals(parentResource.getResourceCode(), resource.getParentCode())) {
                    TreeNodeVO node = convertResource2TreeNode(resource);
                    parentNode.addChild(node);
                    //如果当前的类型为按钮，则将此权限地址添加到父节点的meta中
                    if (resource.getResourceType() == 3) {
                        parentNode.addAvailableButton(resource.getEntryUri());
                    }
                    //同时递归设置下层
                    setChildren(tree, resources, resource, node);
                }
            }
        }
    }

    private TreeNodeVO convertResource2TreeNode(Resource resource) {
        if (resource == null) {
            return null;
        }
        TreeNodeVO node = new TreeNodeVO();
        node.setPath(resource.getEntryUri());
        node.setName(resource.getResourceName());
        node.setComponent(resource.getComponentPath());
        node.setLeaf(resource.getLeaf());
        node.setIcon(resource.getIcon());
        node.setDisplayOrder(resource.getDisplayOrder());
        node.setNodeType(resource.getResourceType());
        node.setId(resource.getResourceCode());
        return node;
    }


}
