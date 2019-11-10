package com.landleaf.ibsaas.web.web.service.leo;

import com.github.pagehelper.PageInfo;
import com.landleaf.ibsaas.common.domain.leo.SubSystem;
import com.landleaf.ibsaas.web.web.dto.request.SelectorParamsDto;
import com.landleaf.ibsaas.web.web.vo.SubSystemQueryVO;

import java.util.List;
import java.util.Map;

/**
 * @description 子系统管理服务
 * @author wyl
 * @date 2019/3/20 0020 17:15
 * @version 1.0
*/
public interface ISubSystemService {

    /**
     * @description 根据条件查询子系统列表
     * @param queryVO
     * @author wyl
     * @date 2019/3/20 0020 17:15
     * @return com.github.pagehelper.PageInfo
     * @version 1.0
    */
    PageInfo listSubSystems(SubSystemQueryVO queryVO);

    /**
     * @description 新增子系统
     * @param subSystem
     * @author wyl
     * @date 2019/3/20 0020 17:16
     * @return com.landleaf.leo.common.domain.SubSystem
     * @version 1.0
    */
    SubSystem addSubSystem(SubSystem subSystem);

    /**
     * @description 修改子系统
     * @param id
     * @param subSystem
     * @author wyl
     * @date 2019/3/20 0020 17:16
     * @return com.landleaf.leo.common.domain.SubSystem
     * @version 1.0
    */
    SubSystem updateSubSystem(String id, SubSystem subSystem);

    /**
     * @description 删除子系统
     * @param id
     * @author wyl
     * @date 2019/3/20 0020 17:16
     * @return void
     * @version 1.0
    */
    void deleteSubSystem(String id);

    /**
     * 按id查询子系统对象信息
     * @param id
     * @return
     * @author Dy
     * @createDate 2017/8/22
     */
    SubSystem querySubSystemById(String id);

   /**
    * @description 提供给系统选择下拉框公共组件
    * @author wyl
    * @date 2019/3/20 0020 17:16
    * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
    * @version 1.0
   */
    List<Map<String, String>> listSystemsToDisplay();

    /**
     * @description 通用分页模糊查询系统名称列表
     * @param paramsDto
     * @author wyl
     * @date 2019/3/20 0020 17:16
     * @return com.github.pagehelper.PageInfo
     * @version 1.0
    */
   PageInfo querySubsystemList(SelectorParamsDto paramsDto);

    /**
     * @description 查询系统所有编码列表
     * @author wyl
     * @date 2019/3/20 0020 17:17
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @version 1.0
    */
    List<Map<String, String>> querySubsystemCodeList();
}
