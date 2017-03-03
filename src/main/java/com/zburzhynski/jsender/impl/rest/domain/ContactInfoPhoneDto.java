package com.zburzhynski.jsender.impl.rest.domain;

import com.zburzhynski.jsender.api.domain.PhoneNumberType;
import org.apache.commons.lang3.StringUtils;

/**
 * Contact info phone.
 * <p/>
 * Date: 25.02.2017
 *
 * @author Nikita Shevtsou
 */
public class ContactInfoPhoneDto extends BaseDto {

    private String countryCode;

    private String cityCode;

    private String phoneNumber;

    private PhoneNumberType phoneNumberType;

    private String mobileOperator;

    private String description;

    private Long sortOrder;

    /**
     * Gets phone full number.
     *
     * @return phone full number
     */
    public String getFullNumber() {
        String fullNumber = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(countryCode)) {
            fullNumber += countryCode;
        }
        if (StringUtils.isNotBlank(cityCode)) {
            fullNumber += cityCode;
        }
        fullNumber += phoneNumber;
        return fullNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

    public String getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

}
