package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.sender.ISender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Sms unisender.by sender.
 * <p/>
 * Date: 16.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class SmsUnisenderSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsUnisenderSender.class);

    @Override
    public List<SendingStatus> send(Message message) {
        LOGGER.info("Sms unisender sent message");
        return null;
    }

    @Override
    public Set<SendingServices> getSendingServices() {
        return EnumSet.of(SendingServices.UNISENDER_BY);
    }

    @Override
    public SendingType getSendingType() {
        return SendingType.SMS;
    }

}
