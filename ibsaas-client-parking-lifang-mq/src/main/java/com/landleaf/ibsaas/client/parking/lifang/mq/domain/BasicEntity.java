package com.landleaf.ibsaas.client.parking.lifang.mq.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1392162988610720992L;


    /**
     * 创建时间
     */
    @Column(name = "CreateDate")
    private Date CreateDate;

    /**
     * 创建人
     */
    @Column(name = "CreatePeople")
    private String CreatePeople;

    /**
     * 修改时间
     */
    @Column(name = "UpdateDate")
    private Date UpdateDate;

    /**
     * 修改人
     */
    @Column(name = "UpdatePeople")
    private String UpdatePeople;

}