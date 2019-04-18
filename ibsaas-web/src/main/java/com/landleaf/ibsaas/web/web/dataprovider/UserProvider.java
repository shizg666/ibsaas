package com.landleaf.ibsaas.web.web.dataprovider;

import com.landleaf.ibsaas.common.domain.leo.User;
import com.landleaf.ibsaas.common.service.leo.ICommonUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyl
 * @version 1.0
 * @date 2019年03月23日 14:58
 */
@Service
public class UserProvider implements IUserProvider {

    @Autowired
    private ICommonUserService commonUserService;

    @Override
    public IUser getUserById(String userid) {
        User query = new User();
        query.setId(userid);
        User user = (User) commonUserService.selectOne(query);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }
}
