package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.dto.Message;
import com.zburzhynski.jsender.api.dto.SendingStatus;
import com.zburzhynski.jsender.api.exception.SendingException;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * @return sending status
     * @throws SendingException if any
     */
    public List<SendingStatus> send(Message message) throws SendingException {
        SendingAccount account = (SendingAccount) accountService.getById(message.getSendingAccountId());
        return senderHolder.getSender(account.getSendingService().getName(), message.getSendingType()).send(message);
    }

}
