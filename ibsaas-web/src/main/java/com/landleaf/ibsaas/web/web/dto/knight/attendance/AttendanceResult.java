package com.landleaf.ibsaas.web.web.dto.knight.attendance;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "考勤核算DTO")
public class AttendanceResult {

    private Integer recordId;      //记录编号
    private String deptName;      //部门名称
    private String calDateString;      //核算日期
    private Integer sysNo;      //系统编号
    private String employeeId;      //人员编号
    private String empName;      //人员名称
    private Long calDate;      //核算日期   时间戳格式
    private String resultTypeName;      //出勤结论

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCalDateString() {
        return calDateString;
    }

    public void setCalDateString(String calDateString) {
        this.calDateString = calDateString;
    }

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Long getCalDate() {
        return calDate;
    }

    public void setCalDate(Long calDate) {
        this.calDate = calDate;
    }

    public String getResultTypeName() {
        return resultTypeName;
    }

    public void setResultTypeName(String resultTypeName) {
        this.resultTypeName = resultTypeName;
    }
}
