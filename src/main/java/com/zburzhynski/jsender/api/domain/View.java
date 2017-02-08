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
