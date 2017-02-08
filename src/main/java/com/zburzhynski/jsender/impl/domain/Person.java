package com.zburzhynski.jsender.impl.domain;

import static com.zburzhynski.jsender.api.domain.CommonConstant.DOT;
import static com.zburzhynski.jsender.api.domain.Gender.M;
import static javax.xml.stream.XMLStreamConstants.SPACE;
import com.zburzhynski.jsender.api.domain.Gender;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Person.
 * <p/>
 * Date: 04.02.2017
 *
 * @author Nikita Shevtsov
 */
@Entity
@Table(name = "person")
public class Person extends Domain {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "birthday")
    private Date birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender = M;

    /**
     * Gets full name.
     *
     * @return full name
     */
    public String getFullName() {
        return new StringBuilder()
            .append(surname)
            .append(SPACE)
            .append(name)
            .append(SPACE)
            .append(patronymic).toString();
    }

    /**
     * Gets short name.
     *
     * @return short name
     */
    public String getShortName() {
        return new StringBuilder()
            .append(surname)
            .append(SPACE)
            .append(name.substring(0, 1))
            .append(DOT)
            .append(patronymic.substring(0, 1))
            .append(DOT).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Person)) {
            return false;
        }

        Person that = (Person) o;
        return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(name, that.name)
            .append(surname, that.surname)
            .append(patronymic, that.patronymic)
            .append(gender, that.gender)
            .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .appendSuper(super.hashCode())
            .append(name)
            .append(surname)
            .append(patronymic)
            .append(gender)
            .toHashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("name", name)
            .append("surname", surname)
            .append("patronymic", patronymic)
            .append("gender", gender)
            .toString();
    }

}
