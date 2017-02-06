package com.zburzhynski.jsender.impl.jsf.bean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Program version bean, represents information about program version.
 * <p/>
 * Date: 05.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@ApplicationScoped
public class VersionBean {

    private String version = "1.0.0";

    private String versionDate = "05.02.2017";

    /**
     * Gets program version.
     *
     * @return program version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets version date.
     *
     * @return version date
     */
    public String getVersionDate() {
        return versionDate;
    }

}
