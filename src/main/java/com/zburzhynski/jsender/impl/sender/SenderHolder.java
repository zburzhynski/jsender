package com.zburzhynski.jsender.impl.sender;

import com.zburzhynski.jsender.api.domain.SendingServices;
import com.zburzhynski.jsender.api.domain.SendingType;
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
     * @param sendingType sending type
     * @return message sender
     */
    public ISender getSender(String serviceName, SendingType sendingType) {
        Map<SendingServices, Map<SendingType, ISender>> senderMap = new HashMap<>();
        for (ISender sender : allSenders) {
            for (SendingServices service : sender.getSendingServices()) {
                if (!senderMap.containsKey(service)) {
                    senderMap.put(service, new HashMap<SendingType, ISender>());
                }
                senderMap.get(service).put(sender.getSendingType(), sender);
            }
        }
        return senderMap.get(SendingServices.getBySite(serviceName)).get(sendingType);
    }

}
