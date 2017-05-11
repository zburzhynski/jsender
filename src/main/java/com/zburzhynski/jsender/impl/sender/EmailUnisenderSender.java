package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingResponse;
import com.zburzhynski.jsender.api.sender.ISender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Set;

/**
 * Email unisender.by sender.
 * <p/>
 * Date: 19.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class EmailUnisenderSender implements ISender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailUnisenderSender.class);

    @Override
    public SendingResponse send(Message message) {
        LOGGER.info("Email unisender sent message");
        return null;
    }

    @Override
    public Set<SendingServices> getSendingServices() {
        return EnumSet.of(SendingServices.EMAIL_UNISENDER_BY);
    }

    @Override
    public SendingType getSendingType() {
        return SendingType.EMAIL;
    }

}
