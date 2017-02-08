package com.zburzhynski.jsender.impl.jsf.validator;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jsender.impl.util.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Base validator.
 * <p/>
 * Date: 09.05.15
 *
 * @author Vladimir Zburzhynski
 */
public abstract class BaseValidator implements Serializable {

    @Autowired
    private PropertyReader reader;

    /**
     * Sets property reader.
     *
     * @param reader property reader to set
     */
    public void setReader(PropertyReader reader) {
        this.reader = reader;
    }

    /**
     * Adds localisation message to context.
     *
     * @param message localisation message
     */
    protected void addMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(reader.readProperty(message), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

    /**
     * Adds localisation message to context.
     *
     * @param message localisation message
     * @param params  parameters
     */
    protected void addMessage(String message, Object... params) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(reader.readProperty(message, params), StringUtils.EMPTY);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

}
