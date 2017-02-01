package com.zburzhynski.jsender.api.domain;

/**
 * ISetting interface represents application setting.
 * <p/>
 * Date: 02.08.2016
 *
 * @author Vladimir Zburzhynski
 */
public interface ISetting extends IDomain, Comparable<ISetting> {

    /**
     * Gets name.
     *
     * @return name
     */
    String getName();

    /**
     * Sts name.
     *
     * @param name name to set
     */
    void setName(String name);

    /**
     * Gets setting category.
     *
     * @return setting category
     */
    SettingCategory getCategory();

    /**
     * Sets setting category.
     *
     * @param category setting category to set
     */
    void setCategory(SettingCategory category);

    /**
     * Gets value.
     *
     * @return value
     */
    String getValue();

    /**
     * Sets value.
     *
     * @param value value to set
     */
    void setValue(String value);

    /**
     * Gets setting value type.
     *
     * @return setting value type
     */
    SettingValueType getType();

    /**
     * Sets setting value type.
     *
     * @param type setting value type to set
     */
    void setType(SettingValueType type);

    /**
     * Gets description.
     *
     * @return description
     */
    String getDescription();

    /**
     * Sets description.
     *
     * @param description description to set
     */
    void setDescription(String description);

}
