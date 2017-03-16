package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.sender.ISender;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Unisender.by sender.
 * <p/>
 * Date: 3/16/2017
 *
 * @author Vladimir Zburzhynski
 */
public class UnisenderSender implements ISender {

    @Override
    public List<SendingStatus> send(Message message) {
        return null;
    }

    @Override
    public Set<SendingServices> getSendingServices() {
        return EnumSet.of(SendingServices.UNISENDER_BY);
    }

}
