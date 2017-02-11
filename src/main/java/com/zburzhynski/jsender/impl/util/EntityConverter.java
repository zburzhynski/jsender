package com.zburzhynski.jsender.impl.util;

import com.zburzhynski.jsender.api.domain.IDomain;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Entity converter.
 * <p/>
 * Date: 02.05.15
 *
 * @author Vladimir Zburzhynski
 */
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

    private static final String KEY = "com.zburzhynski.jsf.EntityConverter";

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return getViewMap(context).get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return StringUtils.EMPTY;
        }
        String id = ((IDomain) value).getId();
        getViewMap(context).put(id, value);
        return id;
    }

    private Map<String, Object> getViewMap(FacesContext context) {
        Map<String, Object> viewMap = context.getViewRoot().getViewMap();
        Map<String, Object> idMap = (Map) viewMap.get(KEY);
        if (idMap == null) {
            idMap = new HashMap<>();
            viewMap.put(KEY, idMap);
        }
        return idMap;
    }

}
