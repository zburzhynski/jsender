package com.zburzhynski.jsender.impl.rest.domain;

import java.io.Serializable;

/**
 * Person.
 * <p/>
 * Date: 21.05.15
 *
 * @author Vladimir Zburzhynski
 */
public class PatientDto extends PersonDto implements Serializable {

    private String id;

    private Integer number;

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
