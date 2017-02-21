package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.criteria.MessageTemplateSearchCriteria;
import com.zburzhynski.jsender.api.repository.IMessageTemplateRepository;
import com.zburzhynski.jsender.api.service.IMessageTemplateService;
import com.zburzhynski.jsender.impl.domain.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IMessageTemplateService} interface.
 * <p/>
 * Date: 21.02.2017
 *
 * @author Nikita Shevtsou
 */
@Service("messageTemplateService")
@Transactional(readOnly = true)
public class MessageTemplateService implements IMessageTemplateService<String, MessageTemplate> {

    @Autowired
    private IMessageTemplateRepository<String, MessageTemplate> messageTemplateRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageTemplate getById(String id) {
        return messageTemplateRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessageTemplate> getByCriteria(MessageTemplateSearchCriteria searchCriteria, Long start, Long end) {
        return messageTemplateRepository.findByCriteria(searchCriteria, start, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(MessageTemplateSearchCriteria searchCriteria) {
        return messageTemplateRepository.countByCriteria(searchCriteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(MessageTemplate messageTemplate) {
        boolean result = false;
        if (messageTemplate != null) {
            messageTemplateRepository.saveOrUpdate(messageTemplate);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(MessageTemplate messageTemplate) {
        messageTemplateRepository.delete(messageTemplate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MessageTemplate> getAll() {
        return messageTemplateRepository.findAll();
    }

}
