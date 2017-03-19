package com.zburzhynski.jsender.impl.rest.domain.unisender;

/**
 * Check sms request.
 * <p/>
 * Date: 17.03.2017
 *
 * @author Vladimir Zburzhynski
 */
public class CheckSmsRequest extends BaseUnisenderRequest {

    private Integer smsId;

    public Integer getSmsId() {
        return smsId;
    }

    public void setSmsId(Integer smsId) {
        this.smsId = smsId;
    }

}
