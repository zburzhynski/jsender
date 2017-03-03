package com.zburzhynski.jsender.api.dto;

import static com.zburzhynski.jsender.api.domain.CommonConstant.SPACE;

import java.util.ArrayList;
import java.util.List;

/**
 * Recipient.
 * <p/>
 * Date: 16.02.2017
 *
 * @author Nikita Shevtsov
 */
public class Recipient {

    private String id;

    private String surname;

    private String name;

    private String patronymic;

    private List<String> phones;

    private List<String> emails;

    /**
     * Adds phone number.
     *
     * @param phone phone number to add
     */
    public void addPhone(String phone) {
        getPhones().add(phone);
    }

    /**
     * Removes phone number.
     *
     * @param phone phone number to remove
     */
    public void removePhone(String phone) {
        getPhones().remove(phone);
    }

    /**
     * Adds email address.
     *
     * @param email email address to add
     */
    public void addEmail(String email) {
        getEmails().add(email);
    }

    /**
     * Removes email address.
     *
     * @param email email address to remove
     */
    public void removeEmail(String email) {
        getEmails().remove(email);
    }

    /**
     * Gets recipient full name.
     *
     * @return recipient full name
     */
    public String getFullName() {
        return surname.concat(SPACE).concat(name).concat(SPACE).concat(patronymic);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Gets phone numbers.
     *
     * @return phone numbers
     */
    public List<String> getPhones() {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    /**
     * Gets email addresses.
     *
     * @return mail addresses
     */
    public List<String> getEmails() {
        if (emails == null) {
            emails = new ArrayList<>();
        }
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

}
