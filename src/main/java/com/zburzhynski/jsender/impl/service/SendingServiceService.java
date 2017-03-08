package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.repository.ISendingServiceRepository;
import com.zburzhynski.jsender.api.service.ISendingServiceService;
import com.zburzhynski.jsender.impl.domain.SendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ISendingServiceService} interface.
 * <p/>
 * Date: 07.03.2017
 *
 * @author Nikita Shevtsou
 */
@Service("sendingServiceService")
@Transactional(readOnly = true)
public class SendingServiceService implements ISendingServiceService<String, SendingService> {

    @Autowired
    private ISendingServiceRepository<String, SendingService> sendingServiceRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public SendingService getById(String id) {
        return sendingServiceRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(SendingService service) {
        boolean result = false;
        if (service != null) {
            sendingServiceRepository.saveOrUpdate(service);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(SendingService service) {
        sendingServiceRepository.saveOrUpdate(service);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SendingService> getAll() {
        return sendingServiceRepository.findAll();
    }

}
