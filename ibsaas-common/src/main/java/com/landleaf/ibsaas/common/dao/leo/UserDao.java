package com.landleaf.ibsaas.common.dao.leo;

import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.datasource.mybatis.basedao.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/*
 * @Author wyl
 * @Description 用户信息数据库操作
 * @Date 16:03 2019/3/20 0020
 **/
@Repository
public interface UserDao extends BaseDao<User> {

    /**
     * 根据用户名获取用户信息
     * @param userCode
     * @return
     * @date 2017年08月07日16:13:38
     */
    User getUser(String userCode);

    /**
     *根据用户主键批量删除
     *@params:　id 主键
     *@return:
     *@author:wyl
     */
    void deleteUserByIds(@Param("userIds") String[] userIds);
}
