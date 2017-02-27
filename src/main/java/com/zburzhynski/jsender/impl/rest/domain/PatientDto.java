package com.zburzhynski.jsender.impl.rest.domain;

/**
 * Person.
 * <p/>
 * Date: 21.05.15
 *
 * @author Vladimir Zburzhynski
 */
public class PatientDto extends PersonDto {

    private Integer number;

    private String address;

    private ContactInfoDto contactInfo;

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

    public ContactInfoDto getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDto contactInfo) {
        this.contactInfo = contactInfo;
    }

}
