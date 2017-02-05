package com.zburzhynski.jsender.impl.domain;

import static com.zburzhynski.jsender.api.domain.CommonConstants.LEFT_PARENTHESIS;
import static com.zburzhynski.jsender.api.domain.CommonConstants.RIGHT_PARENTHESIS;
import static com.zburzhynski.jsender.api.domain.CommonConstants.SPACE;
import com.zburzhynski.jsender.api.domain.PhoneNumberType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Contact info phone.
 * <p/>
 * Date: 04.02.2017
 *
 * @author Nikita Shevtsov
 */
@Entity
@Table(name = "contact_info_phone")
public class ContactInfoPhone extends Domain implements Comparable<ContactInfoPhone> {

    public static final String P_COUNTRY_CODE = "countryCode";

    public static final String P_CITY_CODE = "cityCode";

    public static final String P_PHONE_NUMBER = "phoneNumber";

    public static final String P_PHONE_NUMBER_TYPE = "phoneNumberType";

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_number_type")
    @Enumerated(value = EnumType.STRING)
    private PhoneNumberType phoneNumberType;

    @Column(name = "mobile_operator")
    private String mobileOperator;

    @Column(name = "description")
    private String description;

    @Column(name = "sort_order")
    private Long sortOrder;

    /**
     * Gets full phone number.
     *
     * @return full phone number
     */
    public String getFullNumber() {
        String fullNumber = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(countryCode)) {
            fullNumber += countryCode;
        }
        if (StringUtils.isNotBlank(cityCode)) {
            fullNumber += LEFT_PARENTHESIS + cityCode + RIGHT_PARENTHESIS + SPACE;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(ContactInfoPhone o) {
        return sortOrder.compareTo(o.getSortOrder());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ContactInfoPhone)) {
            return false;
        }

        ContactInfoPhone that = (ContactInfoPhone) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(countryCode, that.countryCode)
            .append(cityCode, that.cityCode)
            .append(phoneNumber, that.phoneNumber)
            .append(phoneNumberType, that.phoneNumberType)
            .append(description, that.description)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(countryCode)
            .append(cityCode)
            .append(phoneNumber)
            .append(phoneNumberType)
            .append(description)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("countryCode", countryCode)
            .append("cityCode", cityCode)
            .append("phoneNumber", phoneNumber)
            .append("phoneNumberType", phoneNumberType)
            .append("description", description)
            .toString();
    }

}
