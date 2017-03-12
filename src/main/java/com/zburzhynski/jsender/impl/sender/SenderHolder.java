package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.sender.ISender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Message sender holder.
 * <p/>
 * Date: 12.03.2017
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class SenderHolder {

    @Autowired
    private List<ISender> allSenders;

    /**
     * Gets message sender by sending service.
     *
     * @param serviceName sending service name
     * @return message sender
     */
    public ISender getSender(String serviceName) {
        Map<SendingServices, ISender> senders = new HashMap<>();
        for (ISender sender : allSenders) {
            for (SendingServices service : sender.getSendingServices()) {
                senders.put(service, sender);
            }
        }
        return senders.get(SendingServices.getBySite(serviceName));
    }

}
