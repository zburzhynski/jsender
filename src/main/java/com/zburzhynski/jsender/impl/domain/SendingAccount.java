package com.zburzhynski.jsender.impl.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Sending account.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Entity
@Table(name = "sending_account")
public class SendingAccount extends Domain {

    public static final String P_SENDING_SERVICE = "sendingService";

    public static final String P_ACCOUNT_PARAMS = "accountParams";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sending_service_id")
    private SendingService sendingService;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sending_account_id", nullable = false)
    private List<SendingAccountParam> accountParams;

    public SendingService getSendingService() {
        return sendingService;
    }

    public void setSendingService(SendingService sendingService) {
        this.sendingService = sendingService;
    }

    /**
     * Gets service params.
     *
     * @return service params
     */
    public List<SendingAccountParam> getAccountParams() {
        if (accountParams == null) {
            accountParams = new ArrayList<>();
        }
        return accountParams;
    }

    public void setAccountParams(List<SendingAccountParam> accountParams) {
        this.accountParams = accountParams;
    }

}