package com.zburzhynski.jsender.impl.util;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * Class has common methods for working with jsf beans.
 * <p/>
 * Date: 30.03.13
 *
 * @author Vladimir Zburzhynski
 */
public class BeanUtils {

    /**
     * Gets bean from jsf session context by name.
     *
     * @param name bean name
     * @param <T>  type
     * @return bean from context
     */
    public static <T> T getSessionBean(String name) {
        return (T) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
    }

    /**
     * Gets bean from jsf view context by name.
     *
     * @param name bean name
     * @param <T>  bean  type
     * @return bean from context
     */
    public static <T> T getViewBean(String name) {
        return (T) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get(name);
    }

    /**
     * Force refresh page.
     */
    public static void refreshPage() {
        FacesContext context = FacesContext.getCurrentInstance();
        String viewId = context.getViewRoot().getViewId();
        ViewHandler handler = context.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(context, viewId);
        root.setViewId(viewId);
        context.setViewRoot(root);
    }

}
