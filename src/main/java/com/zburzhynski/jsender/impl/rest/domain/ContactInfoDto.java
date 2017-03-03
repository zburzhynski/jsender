package com.zburzhynski.jsender.impl.rest.domain;

import java.util.ArrayList;
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

    /**
     * Gets phones.
     *
     * @return phones
     */
    public List<ContactInfoPhoneDto> getPhones() {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        return phones;
    }

    public void setPhones(List<ContactInfoPhoneDto> phones) {
        this.phones = phones;
    }

    /**
     * Gets emails.
     *
     * @return emails
     */
    public List<ContactInfoEmailDto> getEmails() {
        if (emails == null) {
            emails = new ArrayList<>();
        }
        return emails;
    }

    public void setEmails(List<ContactInfoEmailDto> emails) {
        this.emails = emails;
    }

}
