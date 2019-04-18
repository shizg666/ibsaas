package com.landleaf.ibsaas.common.domain.leo;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 权限实体
 *
 * @author wyl
 */
@ApiModel(value = "权限信息实体")
@Table(name = "t_base_resource")
public class Resource extends BasicEntity {

    private static final long serialVersionUID = -1034557484044565664L;


    /**
     * 功能编码
     */
    @ApiModelProperty(value = "权限编码", required = true, dataType = "String", example = "web_1")
    private String resourceCode;

    /**
     * 功能名称
     */
    @ApiModelProperty(value = "权限名称", required = false, dataType = "String", example = "权限管理后台管理系统")
    private String resourceName;

    /**
     * 功能层次
     */
    @ApiModelProperty(value = "功能层次", required = true, dataType = "Integer", example = "1")
    private Integer resourceLevel;

    /**
     * 功能入口URI
     */
    @ApiModelProperty(value = "功能入口URI", required = false, dataType = "String", example = "/systemManage/subsystem")
    private String entryUri;

    /**
     * 菜单对应组件路径
     */
    @ApiModelProperty(value = "菜单对应组件路径", required = false, dataType = "String", example = "systemManage/childMgt/ChildSystemSetting")
    private String componentPath;

    /**
     * 父功能
     */
    @ApiModelProperty(value = "父权限编码", required = false, dataType = "String", example = "web_1")
    private String parentCode;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序", required = false, dataType = "Integer", example = "10")
    private Integer displayOrder;

    /**
     * 功能类型
     */
    @ApiModelProperty(value = "功能类型", required = true, dataType = "Integer", example = "3")
    private Integer resourceType;

    /**
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点", required = false, dataType = "Boolean", example = "true")
    @Column(name = "is_leaf")
    private Boolean leaf;

    /**
     * 所属系统类型
     */
    @ApiModelProperty(value = "所属系统", required = true, dataType = "String", example = "LEO")
    private String belongSystem;

    /**
     * 数据版本号
     */
    @ApiModelProperty(value = "数据版本号", required = true, dataType = "Long", example = "1503891045265")
    private Long versionNo;

    /**
     * 是否有效
     */
    @ApiModelProperty(value = "是否有效", required = true, dataType = "Integer", example = "0")
    @Column(name = "is_active")
    private Integer active;

    /**
     * 图标路径
     */
    @ApiModelProperty(value = "图标路径", required = false, dataType = "String", example = "systemSetting")
    private String icon;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource resource = (Resource) object;
        return this.belongSystem.equals(resource.belongSystem)
                && this.resourceCode.equals(resource.resourceCode)
                && this.resourceName.equals(resource.resourceName)
                && this.active.equals(resource.active);
    }

    @Override
    public int hashCode() {
        return new StringBuilder().append(this.belongSystem).append("+")
                .append(this.resourceCode).append("+")
                .append(this.resourceName).append("+")
                .append(this.active).toString().hashCode();
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Integer getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(Integer resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public String getEntryUri() {
        return entryUri;
    }

    public void setEntryUri(String entryUri) {
        this.entryUri = entryUri;
    }

    public String getComponentPath() {
        return componentPath;
    }

    public void setComponentPath(String componentPath) {
        this.componentPath = componentPath;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getBelongSystem() {
        return belongSystem;
    }

    public void setBelongSystem(String belongSystem) {
        this.belongSystem = belongSystem;
    }

    public Long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
