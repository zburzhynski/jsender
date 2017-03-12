package com.zburzhynski.jsender.api.domain;

import java.util.EnumSet;

/**
 * Represents sending services.
 * <p/>
 * Date: 12.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public enum SendingServices {

    GMAIL_COM("gmail.com"),

    YANDEX_RU("yandex.ru"),

    MAIL_RU("mail.ru"),

    UNISENDER_BY("unisender.by");

    private String site;

    /**
     * Constructor.
     *
     * @param site sending service site
     */
    SendingServices(String site) {
        this.site = site;
    }

    /**
     * Gets sending service by site.
     *
     * @param site service site
     * @return sending service
     */
    public static SendingServices getBySite(String site)  {
        EnumSet<SendingServices> all = EnumSet.allOf(SendingServices.class);
        for (SendingServices service : all) {
            if (service.getSite().equals(site)) {
                return service;
            }
        }
        return null;
    }

    public String getSite() {
        return site;
    }

}
