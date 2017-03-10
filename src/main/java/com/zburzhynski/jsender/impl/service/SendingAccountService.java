package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.criteria.SendingAccountSearchCriteria;
import com.zburzhynski.jsender.api.repository.ISendingAccountRepository;
import com.zburzhynski.jsender.api.service.ISendingAccountService;
import com.zburzhynski.jsender.impl.domain.SendingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ISendingAccountService} interface.
 * <p/>
 * Date: 07.03.2017
 *
 * @author Nikita Shevtsou
 */
@Service("sendingAccountService")
@Transactional(readOnly = true)
public class SendingAccountService implements ISendingAccountService<String, SendingAccount> {

    @Autowired
    private ISendingAccountRepository<String, SendingAccount> accountService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SendingAccount> getByCriteria(SendingAccountSearchCriteria searchCriteria,
                                              Long start, Long end) {
        return accountService.findByCriteria(searchCriteria, start, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(SendingAccountSearchCriteria searchCriteria) {
        return accountService.countByCriteria(searchCriteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SendingAccount getById(String id) {
        return accountService.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(SendingAccount service) {
        boolean result = false;
        if (service != null) {
            accountService.saveOrUpdate(service);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(SendingAccount service) {
        accountService.delete(service);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SendingAccount> getAll() {
        return accountService.findAll();
    }

}
