package com.landleaf.ibsaas.client.knight.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "人员类型")
@Table(name = "pb_employeetype")
public class EmplyType {
    @ApiModelProperty(value = "用户类型", dataType = "String")
    @Column(name = "emply_type")
    private String emplyType;
    @ApiModelProperty(value = "用户类型名称",  dataType = "String")
    @Column(name = "emply_type_name")
    private String emplyTypeName;
    @Column(name = "join_cost")
    private BigDecimal joinCost;
    @Column(name = "subsidy")
    private BigDecimal subsidy;
    @Column(name = "work_meal")
    private Integer workMeal;
    @Column(name = "card_cost")
    private BigDecimal cardCost;
    @Column(name = "deposit")
    private BigDecimal deposit;
    @Column(name = "rabate")
    private BigDecimal rabate;
    @Column(name = "ration")
    private BigDecimal ration;
    @Column(name = "sub_card_cost")
    private BigDecimal subCardCost;
    @Column(name = "sub_deposit")
    private BigDecimal subDeposit;

    public String getEmplyType() {
        return emplyType;
    }

    public void setEmplyType(String emplyType) {
        this.emplyType = emplyType;
    }

    public String getEmplyTypeName() {
        return emplyTypeName;
    }

    public void setEmplyTypeName(String emplyTypeName) {
        this.emplyTypeName = emplyTypeName;
    }

    public BigDecimal getJoinCost() {
        return joinCost;
    }

    public void setJoinCost(BigDecimal joinCost) {
        this.joinCost = joinCost;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public Integer getWorkMeal() {
        return workMeal;
    }

    public void setWorkMeal(Integer workMeal) {
        this.workMeal = workMeal;
    }

    public BigDecimal getCardCost() {
        return cardCost;
    }

    public void setCardCost(BigDecimal cardCost) {
        this.cardCost = cardCost;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getRabate() {
        return rabate;
    }

    public void setRabate(BigDecimal rabate) {
        this.rabate = rabate;
    }

    public BigDecimal getRation() {
        return ration;
    }

    public void setRation(BigDecimal ration) {
        this.ration = ration;
    }

    public BigDecimal getSubCardCost() {
        return subCardCost;
    }

    public void setSubCardCost(BigDecimal subCardCost) {
        this.subCardCost = subCardCost;
    }

    public BigDecimal getSubDeposit() {
        return subDeposit;
    }

    public void setSubDeposit(BigDecimal subDeposit) {
        this.subDeposit = subDeposit;
    }
}
