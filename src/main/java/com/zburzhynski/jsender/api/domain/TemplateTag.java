package com.zburzhynski.jsender.api.domain;

/**
 * Represents template tags.
 * <p/>
 * Date: 22.02.2017
 *
 * @author Nikita Shevtsou
 */
public enum TemplateTag {

    CLIENT_SURNAME("templateTag.clientSurname"),
    CLIENT_NAME("templateTag.clientName"),
    CLIENT_PATRONYMIC("templateTag.clientPatronymic"),
    CLIENT_FULL_NAME("templateTag.clientFullName"),
    ORGANIZATION_NAME("templateTag.organizationName"),
    ORGANIZATION_ADDRESS("templateTag.organizationAddress"),
    ORGANIZATION_MOBILE_PHONE_NUMBER("templateTag.organizationMobilePhoneNumber");

    private String value;

    private TemplateTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
