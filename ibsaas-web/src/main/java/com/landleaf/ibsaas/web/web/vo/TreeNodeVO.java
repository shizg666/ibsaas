package com.landleaf.ibsaas.web.web.vo;


import com.alibaba.druid.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wyl
 * @version 1.0
 * @description 前台展示树形控件的数据模型
 * @date 2019/3/20 0020 17:01
 * @return
 */
public class TreeNodeVO implements Serializable, Comparable<TreeNodeVO> {

    private static final long serialVersionUID = -6305345579122616174L;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 树节点显示名称
     */
    private String name;

    /**
     * 节点对应组件路径
     */
    private String component;

    /**
     * 是否叶子节点
     */
    private Boolean leaf;

    /**
     * 是否显示
     */
    private Boolean hidden;

    /**
     * 图标路径
     */
    private String icon;

    /**
     * 显示顺序
     */
    private Integer displayOrder;

    /**
     * 节点类型 0根节点、1模块、2菜单、3按钮
     */
    private Integer nodeType;


    //权限管理需要字段新增
    /**
     * 对应权限编码resourceCode
     */
    private String id;

    /**
     * 页面需要使用的额外信息
     */
    private String meta;

    /**
     * 子节点
     */
    private List<TreeNodeVO> children = new ArrayList<TreeNodeVO>();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TreeNodeVO)) {
            return false;
        }
        TreeNodeVO other = (TreeNodeVO) obj;
        return StringUtils.equals(this.path, other.path)
                && StringUtils.equals(this.name, other.name)
                && StringUtils.equals(this.component, other.component);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getNodeType() {
        return nodeType;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }

    public List<TreeNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVO> children) {
        this.children = children;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int compareTo(TreeNodeVO o) {
        if (this.displayOrder == null) {
            this.displayOrder = 0;
        }
        if (o.displayOrder == null) {
            o.displayOrder = 0;
        }
        return (int) Math.signum(this.displayOrder - o.displayOrder);
    }

    /**
     * 向当前节点添加子节点，添加完成后按显示顺序排序
     *
     * @param child
     * @author wyl
     * @date 2017年09月12日23:40:30
     */
    public void addChild(TreeNodeVO child) {
        if (child != null) {
            this.children.add(child);
            Collections.sort(this.children);
        }
    }

}
