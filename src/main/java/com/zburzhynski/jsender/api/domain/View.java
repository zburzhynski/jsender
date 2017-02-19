package com.zburzhynski.jsender.api.domain;

/**
 * Contains view pages.
 * <p/>
 * Date: 05.02.2017
 *
 * @author Nikita Shevtsov
 */
public enum View {

    CLIENTS("/pages/client/clients?faces-redirect=true"),
    SENDING("/pages/sending/sending?faces-redirect=true"),
    SENDING_STATUS("/pages/sending/sending_status?faces-redirect=true"),
    SENT_MESSAGES("/pages/sent_message/sent_messages?faces-redirect=true"),
    CLIENT("/pages/client/client?faces-redirect=true"),
    CONTACT_INFO_EMAIL("/pages/client/email?faces-redirect=true"),
    CONTACT_INFO_PHONE("/pages/client/phone?faces-redirect=true"),
    SETTINGS("/pages/setting/settings?faces-redirect=true");

    private String path;

    /**
     * Constructor.
     *
     * @param path path
     */
    private View(String path) {
        this.path = path;
    }

    /**
     * Gets path.
     *
     * @return path
     */
    public String getPath() {
        return path;
    }

}
