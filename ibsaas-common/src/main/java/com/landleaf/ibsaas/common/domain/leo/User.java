package com.landleaf.ibsaas.common.domain.leo;

import com.landleaf.ibsaas.common.domain.BasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;

/**
* @Title: User 
* @Description: 用户信息
* @author wyl
* @date 2017/8/3 17:32
* @version V1.0   
*/
@ApiModel(value = "用户信息实体")
@Table(name = "t_base_user")
public class User extends BasicEntity {

    private static final long serialVersionUID = 4450758489889109392L;

    /**
     * 用户登录名
     */
    @ApiModelProperty(value = "用户编码", required = true, dataType = "String", example = "279716")
    @Column(name = "user_code")
    private String userCode;

    /**
     * 用户登录密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 版本控制号
     */
    @Column(name = "version_no")
    private Long versionNo;

    /**
     * 用户是否可用，0：不可以，1：可用
     */
    @Column(name = "is_active")
    private Integer active;

    /**
     * 中文姓名
     */
    @Column(name = "user_ch_name")
    private String userChineseName;

    /**
     * 中文名全拼
     */
    @Column(name = "user_ch_spell")
    private String userChineseSpell;

    /**
     * 中文名首字母
     */
    @Column(name = "user_ch_short_spell")
    private String userChineseShortSpell;

    /**
     * 英文名
     */
    @Column(name = "user_en_name")
    private String userEnglishName;

    /**
     * 手机
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 邮箱
     */
    @Column(name = "email")
    private String email;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUserChineseName() {
        return userChineseName;
    }

    public void setUserChineseName(String userChineseName) {
        this.userChineseName = userChineseName;
    }

    public String getUserChineseSpell() {
        return userChineseSpell;
    }

    public void setUserChineseSpell(String userChineseSpell) {
        this.userChineseSpell = userChineseSpell;
    }

    public String getUserChineseShortSpell() {
        return userChineseShortSpell;
    }

    public void setUserChineseShortSpell(String userChineseShortSpell) {
        this.userChineseShortSpell = userChineseShortSpell;
    }

    public String getUserEnglishName() {
        return userEnglishName;
    }

    public void setUserEnglishName(String userEnglishName) {
        this.userEnglishName = userEnglishName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
