package com.landleaf.ibsaas.client.parking.lifang.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 进出记录
 */
@Table(name = "tc_usercrdtm")
public class Usercrdtm {
    /**
     * 标识ID
     */
    @Column(name = "RecordId")
    private Integer recordId;
    /**
     * 车主名称
     */
    @Column(name = "UserName")
    private String userName;
    /**
     */
    @Column(name = "CarCode")
    private String carCode;
    /**
     * 收费规则ID
     */
    @Column(name = "ChargeRuleID")
    private Integer chargeRuleID;

    //车辆品牌
    @Column(name = "CarLabel")
    private String carLabel;
    //车辆颜色
    @Column(name = "CarColor")
    private String carColor;
    //车辆类型
    @Column(name = "CarStyleid")
    private Integer carStyleid;
    /**
     * 进出类型(0进 1出)
     */
    @Column(name = "InOrOut")
    private Integer inOrOut;
    /**
     * 进场时间
     */
    @Column(name = "Crdtm")
    private Date crdtm;
    /**
     * 通道ID
     */
    @Column(name = "ChannelID")
    private Integer channelId;
    /**
     * 车辆图片路径
     */
    @Column(name = "ImagePath")
    private String imagePath;
    /**
     * 场内记录ID
     */
    @Column(name = "InRecordID")
    private Integer inRecordId;
    @Column(name = "OldCarCode")
    private String oldCarCode;
    @Column(name = "ParkingLotID")
    private Integer parkingLotId;
    @Column(name = "CarCode2")
    private String carCode2;
    @Column(name = "IsUpload")
    private Boolean upload;
    @Column(name = "Serial")
    private String serial;
    @Column(name = "IsUploadImage")
    private Boolean uploadImage;
    @Column(name = "CarType")
    private Integer carType;
    @Column(name = "GUID")
    private String guid;
    @Column(name = "Vehicle_Type")
    private Integer vehicleType;
    @Column(name = "Vehicle_Color")
    private String vehicleColor;
    @Column(name = "Plate_Color")
    private String plateColor;
    @Column(name = "CarBrand")
    private String carBrand;
    @Column(name = "Reliability")
    private String reliability;


    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public Integer getChargeRuleID() {
        return chargeRuleID;
    }

    public void setChargeRuleID(Integer chargeRuleID) {
        this.chargeRuleID = chargeRuleID;
    }

    public String getCarLabel() {
        return carLabel;
    }

    public void setCarLabel(String carLabel) {
        this.carLabel = carLabel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getCarStyleid() {
        return carStyleid;
    }

    public void setCarStyleid(Integer carStyleid) {
        this.carStyleid = carStyleid;
    }

    public Integer getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    public Date getCrdtm() {
        return crdtm;
    }

    public void setCrdtm(Date crdtm) {
        this.crdtm = crdtm;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getInRecordId() {
        return inRecordId;
    }

    public void setInRecordId(Integer inRecordId) {
        this.inRecordId = inRecordId;
    }

    public String getOldCarCode() {
        return oldCarCode;
    }

    public void setOldCarCode(String oldCarCode) {
        this.oldCarCode = oldCarCode;
    }

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getCarCode2() {
        return carCode2;
    }

    public void setCarCode2(String carCode2) {
        this.carCode2 = carCode2;
    }

    public Boolean getUpload() {
        return upload;
    }

    public void setUpload(Boolean upload) {
        this.upload = upload;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Boolean getUploadImage() {
        return uploadImage;
    }

    public void setUploadImage(Boolean uploadImage) {
        this.uploadImage = uploadImage;
    }

    public Integer getCarType() {
        return carType;
    }

    public void setCarType(Integer carType) {
        this.carType = carType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }
}
