package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingResponse;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Message sender.
 * <p/>
 * Date: 12.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class MessageSender {

    @Autowired
    private ISendingAccountService accountService;

    @Autowired
    private SenderHolder senderHolder;

    /**
     * Send message.
     *
     * @param message message to send
     * @return {@link SendingResponse} sending response
     */
    public SendingResponse send(Message message) {
        SendingAccount account = (SendingAccount) accountService.getById(message.getSendingAccountId());
        return senderHolder.getSender(account.getSendingService().getName(), message.getSendingType()).send(message);
    }

}
