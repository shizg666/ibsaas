package com.landleaf.ibsaas.web.web.service.leo.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.ibsaas.common.dao.leo.UserDao;
import com.landleaf.ibsaas.common.dao.leo.UserRoleDao;
import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.domain.leo.UserRole;
import com.landleaf.ibsaas.common.service.leo.ICommonUserRoleService;
import com.landleaf.ibsaas.common.service.leo.ICommonUserService;
import com.landleaf.ibsaas.web.web.constant.IbsaasWebConstants;
import com.landleaf.ibsaas.web.web.context.UserContext;
import com.landleaf.ibsaas.web.web.dataprovider.IdGenerator;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.dto.response.SelectorResultDto;
import com.landleaf.ibsaas.web.web.exception.UserException;
import com.landleaf.ibsaas.web.web.service.leo.IUserService;
import com.landleaf.ibsaas.web.web.vo.UserQueryInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wyl
 * @version V1.0
 * @Title: UserService
 * @Description: 用户信息操作实现
 * @date 2017/8/8 15:55
 */
@Service
public class UserService implements IUserService {

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ICommonUserService<User> commonUserService;
    @Autowired
    private ICommonUserRoleService<UserRole> commonUserRoleService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 新增用户
     *
     * @param user
     * @return
     * @author wyl
     * @date 2017年08月09日08:55:18
     */
    public User addUser(User user) {
        initAddParams(user);
        commonUserService.save(user);
        User queryUser = new User();
        queryUser.setId(user.getId());
        return commonUserService.selectOne(queryUser);
    }

    /**
     * 根据用户名获取用户
     *
     * @param userCode
     * @return
     * @author wyl
     * @date 2017年08月09日16:59:27
     */
    public User getUser(String userCode) {
        return userDao.getUser(userCode);
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     * @author wyl
     * @date 2017年08月13日14:51:02
     */
    @Transactional
    public User updateUser(User user) {
        /**
         * 更新逻辑
         * 1.先删除后插入
         * 2.删除时必须是从可用状态改为不可用状态
         * 3.更新影响行数必须不等于0
         */
        User updateUser = new User();
        updateUser.setId(user.getId());
        initUpdateParams(updateUser);
        updateUser.setActive(IbsaasWebConstants.INACTIVE);

        User queryParam = new User();
        queryParam.setId(user.getId());
        User existUsr = commonUserService.selectOne(queryParam);
        if (existUsr == null) throw new UserException(UserException.BUSINESS_USER_UPDATE_USER_NOT_EXISTS);
        //密码处理
        if (!StringUtils.equals(user.getPassword(), existUsr.getPassword())) {
//            String pwd = CryptoUtil.getInstance().getMD5ofStr(user.getPassword());
            user.setPassword(user.getPassword());
        }

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE).andCondition("id=", user.getId());
        int effect = commonUserService.updateByExampleSelective(updateUser, example);
        if (effect == 0) {
            throw new UserException(UserException.BUSINESS_USER_UPDATE_USER_NOT_EXISTS);
        }

        //插入新值
        User targetUser = new User();
        packageUpdateUser(user, targetUser);

        commonUserService.save(targetUser);

        //返回插入后新值
        User queryUser = new User();
        queryUser.setId(targetUser.getId());
        return commonUserService.selectOne(queryUser);
    }

    /**
     * @param:existUser
     * @param:targetUser
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 18:28
     * @description:组装修改时重新插入对象
     */
    private void packageUpdateUser(User user, User targetUser) {
        BeanUtils.copyProperties(user, targetUser);
        Date oprtime = new Date();
        targetUser.setId(idGenerator.nextId());
        targetUser.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
        targetUser.setModifyTime(oprtime);
        targetUser.setVersionNo(oprtime.getTime());
        targetUser.setActive(IbsaasWebConstants.ACTIVE);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     * @author wyl
     * @date 2017年08月13日14:51:33
     */
    @Transactional
    public void deleteUser(String id) {
        Date oprTime = new Date();
        User updateParam = new User();
        updateParam.setActive(IbsaasWebConstants.INACTIVE);
        updateParam.setModifyTime(oprTime);
        updateParam.setModifyUserCode((((User) UserContext.getCurrentUser()).getUserCode()));
        updateParam.setVersionNo(oprTime.getTime());
        updateParam.setId(id);
        commonUserService.updateByPrimaryKeySelective(updateParam);
    }

    /**
     * 根据名称模糊查询用户中文姓名列表 ,供前端用户管理页面搜索姓名下拉框
     *
     * @params:selectorParamsDto 名称对应参数对象中请求字段query
     * @return:
     * @author:
     */
    @Override
    public PageInfo queryDisplayUser(SelectorParamsDto selectorParamsDto) {
        PageHelper.startPage(selectorParamsDto.getPage(), selectorParamsDto.getLimit(), true);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE);
        if (StringUtils.isNotBlank(selectorParamsDto.getQuery())) {
            criteria.andLike("userChineseName", "%" + selectorParamsDto.getQuery() + "%");
        }
        //渲染选择器使用
        List<User> users = commonUserService.selectByExample(example);
        List<SelectorResultDto> selectorResultDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            users.forEach((User item) -> {
                SelectorResultDto vo = new SelectorResultDto();
                vo.setValue(item.getUserChineseName());
                vo.setDisplayName(item.getUserChineseName());
                selectorResultDtos.add(vo);
            });
        }
        return new PageInfo(selectorResultDtos);
    }

    /**
     * 根据条件分页获取用户列表
     *
     * @params:　用户名，用户中文姓名
     * @return:
     * @author:wyl
     */
    @Override
    public PageInfo listUserQueryInfos(UserQueryInfoVO userQueryInfo) {
        PageHelper.startPage(userQueryInfo.getPage(), userQueryInfo.getLimit(), true);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(userQueryInfo.getUserCode())) {
            criteria.andCondition("user_code=", userQueryInfo.getUserCode());
        }
        if (!StringUtils.isEmpty(userQueryInfo.getUserChineseName())) {
            criteria.andCondition("user_ch_name=", userQueryInfo.getUserChineseName());
        }
        criteria.andCondition("is_active=", IbsaasWebConstants.ACTIVE);
        example.setOrderByClause("create_time desc");
        List<User> users = commonUserService.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            users = Lists.newArrayList();
        }
        return new PageInfo(users);
    }

    /**
     * @param:
     * @return:true 可用
     * @author:温奕禄
     * @date 2017/9/22 17:51
     * @description:校验用户名是否可用
     */
    @Override
    public Boolean checkUserCode(String userCode) {
        Boolean flag = false;
        User queryUser = new User();
        queryUser.setUserCode(userCode);
        queryUser.setActive(IbsaasWebConstants.ACTIVE);
        List<User> existUsers = commonUserService.select(queryUser);
        if (CollectionUtils.isEmpty(existUsers)) flag = true;
        return flag;
    }

    /**
     * @param:id
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:52
     * @description:根据主键id查询用户信息
     */
    @Override
    public User queryUserInfo(String id) {
        User queryUser = new User();
        queryUser.setId(id);
        queryUser.setActive(IbsaasWebConstants.ACTIVE);
        return commonUserService.selectOne(queryUser);
    }


    /**
     * @param:roleCodes
     * @param:userCode
     * @param:systemCode
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:52
     * @description:关联角色信息
     */
    @Override
    @Transactional
    public void updateUserRole(String userCode, String systemCode, String[] roleCodes) {
        Set<String> deleteUserRoleIds = new HashSet<String>();//待删除对象主键集合
        List<UserRole> addUserRoles = Lists.newArrayList();//待新增关联实体对象
        //查询已存在关联实体对象，即为待删除
        UserRole queryParam = new UserRole();
        queryParam.setUserCode(userCode);
        queryParam.setBelongSystem(systemCode);
        queryParam.setActive(IbsaasWebConstants.ACTIVE);
        List<UserRole> queryUserRoles = commonUserRoleService.select(queryParam);
        if (!CollectionUtils.isEmpty(queryUserRoles)) {
            queryUserRoles.forEach(item -> deleteUserRoleIds.add(item.getId()));
        }
        //生成新增实体对象，addUserRoles
        convertDealUserRole(addUserRoles, roleCodes, userCode, systemCode);
        if (!CollectionUtils.isEmpty(addUserRoles)) userRoleDao.saveUserRoles(addUserRoles);//插入
        if (!CollectionUtils.isEmpty(deleteUserRoleIds)) {
            int res = userRoleDao.updateUserRoles(IbsaasWebConstants.INACTIVE, new Date(), (((User) UserContext.getCurrentUser()).getUserCode()), userCode, systemCode, deleteUserRoleIds);//删除
            if (res == 0) throw new UserException(UserException.BUSINESS_USER_RELEVANCE_ROLE_UPDATE_NOT_EXISTS);
        }
        clearCachKey(userCode, systemCode);//清除缓存
    }

    /**
     * @param:
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:52
     * @description:清除缓存
     */
    private void clearCachKey(String userCode, String systemCode) {
    }

    /**
     * @param:roleCodes
     * @param:userCode
     * @param:systemCode
     * @param:addUserRoles
     * @return:
     * @author:温奕禄
     * @date 2017/9/22 17:52
     * @description:组装待新成用户角色实体对象集合
     */
    private void convertDealUserRole(List<UserRole> addUserRoles, String[] roleCodes, String userCode, String systemCode) {
        if (roleCodes == null || roleCodes.length == 0) return;
        Set<String> addUserRoleIds = new HashSet<String>();
        addUserRoleIds.addAll(Arrays.asList(roleCodes));
        if (!CollectionUtils.isEmpty(addUserRoleIds)) {
            //新增操作
            addUserRoleIds.forEach(item -> {
                UserRole userRole = new UserRole();
                Date oprTime = new Date();
                String id = idGenerator.nextId();
                userRole.setId(id);
                userRole.setCreateTime(oprTime);
                userRole.setCreateUserCode((((User) UserContext.getCurrentUser()).getUserCode()));
                userRole.setActive(IbsaasWebConstants.ACTIVE);
                userRole.setRoleCode(item);
                userRole.setUserCode(userCode);
                userRole.setBelongSystem(systemCode);
                addUserRoles.add(userRole);
            });
        }
    }

    private void filterUserRoles(String[] roleCodes, Set<String> existUserRoleIds, Set<String> updateUserRoleIds, Set<String> deleteUserRoleIds, Set<String> addUserRoleIds) {
        if (roleCodes == null || roleCodes.length == 0) {
            deleteUserRoleIds.addAll(existUserRoleIds);
        } else {
            List<String> requestRoleCodes = Arrays.asList(roleCodes);
            if (CollectionUtils.isEmpty(existUserRoleIds)) {
                addUserRoleIds.addAll(requestRoleCodes);
            } else {
                updateUserRoleIds.addAll(existUserRoleIds.stream().filter(item -> requestRoleCodes.contains(item)).collect(Collectors.toList()));
                deleteUserRoleIds.addAll(existUserRoleIds.stream().filter(item -> !requestRoleCodes.contains(item)).collect(Collectors.toList()));
                addUserRoleIds.addAll(requestRoleCodes.stream().filter(item -> !existUserRoleIds.contains(item)).collect(Collectors.toList()));
            }
        }
    }

    /**
     * 初始化新增用户相关信息
     *
     * @param user
     * @author wyl
     * @date 2017年08月09日09:01:27
     */
    private void initAddParams(User user) {
        String oprUser = ((User) UserContext.getCurrentUser()).getUserCode();
        Date oprTime = new Date();
        user.setId(idGenerator.nextId());
        user.setCreateUserCode(oprUser);
        user.setCreateTime(oprTime);
        user.setModifyUserCode(oprUser);
        user.setModifyTime(oprTime);
        user.setVersionNo(oprTime.getTime());
        user.setActive(IbsaasWebConstants.ACTIVE);
        user.setModifyUserCode(null);
        user.setModifyTime(null);
        if (StringUtils.isNotBlank(user.getUserChineseName())) {
            //中文姓名拼音转换
            try {
//                if(StringUtils.isBlank(user.getUserChineseShortSpell())){user.setUserChineseShortSpell(ChineseNameTransferUtil.getFirstLettersUp(user.getUserChineseName()));}
//                if(StringUtils.isBlank(user.getUserChineseSpell())){user.setUserChineseSpell(ChineseNameTransferUtil.toHanyuPinyin(user.getUserChineseName()));}
            } catch (Exception e) {
                log.error("BadHanyuPinyinOutputFormatCombination");
            }
        }
    }

    /**
     * 初始化修改用户相关信息
     *
     * @param user
     */
    private void initUpdateParams(User user) {
        user.setModifyTime(new Date());
        user.setModifyUserCode(((User) UserContext.getCurrentUser()).getUserCode());
    }
}
