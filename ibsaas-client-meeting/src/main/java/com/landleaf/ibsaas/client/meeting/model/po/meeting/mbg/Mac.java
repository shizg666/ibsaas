package com.landleaf.ibsaas.client.meeting.model.po.meeting.mbg;

import java.io.Serializable;

public class Mac implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Mac.ID
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    private Integer ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Mac.MAC
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    private String MAC;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table Mac
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Mac.ID
     *
     * @return the value of Mac.ID
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    public Integer getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Mac.ID
     *
     * @param ID the value for Mac.ID
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Mac.MAC
     *
     * @return the value of Mac.MAC
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    public String getMAC() {
        return MAC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Mac.MAC
     *
     * @param MAC the value for Mac.MAC
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Mac
     *
     * @mbggenerated Tue Dec 17 16:25:25 GMT+08:00 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ID=").append(ID);
        sb.append(", MAC=").append(MAC);
        sb.append("]");
        return sb.toString();
    }
}