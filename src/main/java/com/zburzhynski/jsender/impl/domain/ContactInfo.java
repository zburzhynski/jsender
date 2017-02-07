package com.zburzhynski.jsender.impl.domain;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Contact info.
 * <p/>
 * Date: 04.02.2017
 *
 * @author Nikita Shevtsov
 */
@Entity
@Table(name = "contact_info")
public class ContactInfo extends Domain {

    public static final String P_PHONES = "phones";

    public static final String P_EMAILS = "emails";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contactInfo")
    @Sort(type = SortType.NATURAL)
    private Set<Client> clients = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_info_id", nullable = false)
    @Sort(type = SortType.NATURAL)
    private SortedSet<ContactInfoPhone> phones = new TreeSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contact_info_id", nullable = false)
    @Sort(type = SortType.NATURAL)
    private SortedSet<ContactInfoEmail> emails = new TreeSet<>();

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public SortedSet<ContactInfoPhone> getPhones() {
        return phones;
    }

    public void setPhones(SortedSet<ContactInfoPhone> phones) {
        this.phones = phones;
    }

    public SortedSet<ContactInfoEmail> getEmails() {
        return emails;
    }

    public void setEmails(SortedSet<ContactInfoEmail> emails) {
        this.emails = emails;
    }

}
