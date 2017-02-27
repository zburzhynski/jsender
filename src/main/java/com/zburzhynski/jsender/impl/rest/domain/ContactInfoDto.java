package com.zburzhynski.jsender.impl.rest.domain;

import java.util.List;

/**
 * Contact info.
 * <p/>
 * Date: 25.02.2017
 *
 * @author Nikita Shevtsou
 */
public class ContactInfoDto extends BaseDto {

    private List<ContactInfoPhoneDto> phones;

    private List<ContactInfoEmailDto> emails;

    public List<ContactInfoPhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactInfoPhoneDto> phones) {
        this.phones = phones;
    }

    public List<ContactInfoEmailDto> getEmails() {
        return emails;
    }

    public void setEmails(List<ContactInfoEmailDto> emails) {
        this.emails = emails;
    }

}
