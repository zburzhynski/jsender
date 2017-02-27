package com.zburzhynski.jsender.impl.rest.domain;

/**
 * Contact info email dto.
 * <p/>
 * Date: 25.02.2017
 *
 * @author Nikita Shevtsou
 */
public class ContactInfoEmailDto extends BaseDto {

    private String address;

    private String description;

    private Long sortOrder;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
