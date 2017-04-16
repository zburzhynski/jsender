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
    CLIENT("/pages/client/client?faces-redirect=true"),
    SENDING("/pages/sending/sending?faces-redirect=true"),
    SENDING_PREVIEW("/pages/sending/sending_preview?faces-redirect=true"),
    MESSAGE_STATUS("/pages/message_status/message_status?faces-redirect=true"),
    MESSAGE_STATUS_DETAIL("/pages/message_status/status_detail?faces-redirect=true"),
    RECIPIENTS("/pages/sending/recipients?faces-redirect=true"),
    SENT_MESSAGES("/pages/sent_message/sent_messages?faces-redirect=true"),
    MESSAGE_TEMPLATES("/pages/message_template/message_templates?faces-redirect=true"),
    MESSAGE_TEMPLATE("/pages/message_template/message_template?faces-redirect=true"),
    CONTACT_INFO_EMAIL("/pages/client/email?faces-redirect=true"),
    CONTACT_INFO_PHONE("/pages/client/phone?faces-redirect=true"),
    SETTINGS("/pages/setting/settings?faces-redirect=true"),
    SETTING("/pages/setting/setting?faces-redirect=true"),
    ABOUT_VIEW("/pages/help/about?faces-redirect=true"),
    SENDING_ACCOUNT("/pages/setting/sending_account/sending_account?faces-redirect=true"),
    SENDING_SERVICE_PARAM("/pages/setting/sending_account/account_param?faces-redirect=true");

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
