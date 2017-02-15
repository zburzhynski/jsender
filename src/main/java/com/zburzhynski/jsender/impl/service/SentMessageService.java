package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.repository.ISentMessageRepository;
import com.zburzhynski.jsender.api.service.ISentMessageService;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ISentMessageService} interface.
 * <p/>
 * Date: 16.02.2017
 *
 * @author Nikita Shevtsov
 */
@Service("sentMessageService")
@Transactional(readOnly = true)
public class SentMessageService implements ISentMessageService<String, SentMessage> {

    @Autowired
    private ISentMessageRepository<String, SentMessage> sentMessageRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public SentMessage getById(String id) {
        return sentMessageRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(SentMessage sentMessage) {
        boolean result = false;
        if (sentMessage != null) {
            sentMessageRepository.saveOrUpdate(sentMessage);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(SentMessage sentMessage) {
        sentMessageRepository.delete(sentMessage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SentMessage> getAll() {
        return sentMessageRepository.findAll();
    }

}
