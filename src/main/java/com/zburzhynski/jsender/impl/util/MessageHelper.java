package com.zburzhynski.jsender.impl.util;

import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Faces message helper.
 * <p/>
 * Date: 06.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class MessageHelper {

    @Autowired
    private PropertyReader reader;

    /**
     * Adds localisation message to context.
     *
     * @param localisationMessage localisation message
     */
    public void addMessage(String localisationMessage) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(reader.readProperty(localisationMessage), StringUtils.EMPTY);
        message.setSeverity(SEVERITY_INFO);
        context.addMessage(null, message);
    }

    /**
     * Adds localisation message to context.
     *
     * @param localisationMessage localisation message
     * @param params              parameters
     */
    public void addMessage(String localisationMessage, Object... params) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(reader.readProperty(localisationMessage, params), StringUtils.EMPTY);
        message.setSeverity(SEVERITY_INFO);
        context.addMessage(null, message);
    }

    public void setReader(PropertyReader reader) {
        this.reader = reader;
    }

}
