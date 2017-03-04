package com.zburzhynski.jsender.impl.jsf.bean;

import com.zburzhynski.jsender.api.domain.TemplateTag;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Template tag list bean.
 * <p/>
 * Date: 04.03.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@ViewScoped
public class TemplateTagListBean implements Serializable {

    /**
     * Gets template tag types.
     *
     * @return template tag types
     */
    public TemplateTag[] getTemplateTags() {
        return TemplateTag.values();
    }

}
