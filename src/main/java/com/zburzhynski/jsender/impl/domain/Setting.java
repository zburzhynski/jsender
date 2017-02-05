package com.zburzhynski.jsender.impl.domain;

import com.zburzhynski.jsender.api.domain.SettingCategory;
import com.zburzhynski.jsender.api.domain.SettingValueType;
import com.zburzhynski.jsender.api.domain.Settings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Application setting.
 * <p/>
 * Date: 02.08.2016
 *
 * @author Vladimir Zburzhynski
 */
@Entity
@Table(name = "setting")
public class Setting extends Domain implements Comparable<Setting> {

    public static final String P_NAME = "name";
    public static final String P_CATEGORY = "category";
    public static final String P_VALUE = "value";
    public static final String P_TYPE = "type";
    public static final String P_DESCRIPTION = "description";

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private SettingCategory category;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private SettingValueType type;

    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SettingCategory getCategory() {
        return category;
    }

    public void setCategory(SettingCategory category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SettingValueType getType() {
        return type;
    }

    public void setType(SettingValueType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(Setting o) {
        return Settings.valueOf(name.toUpperCase()).compareTo(Settings.valueOf(o.getName().toUpperCase()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Setting)) {
            return false;
        }

        Setting that = (Setting) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(category, that.category)
            .append(value, that.value)
            .append(type, that.type)
            .append(description, that.description)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(category)
            .append(value)
            .append(type)
            .append(description)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("category", category)
            .append("value", value)
            .append("type", type)
            .append("description", description)
            .toString();
    }

}
