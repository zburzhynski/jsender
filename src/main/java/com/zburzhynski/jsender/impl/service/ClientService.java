package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.criteria.ClientSearchCriteria;
import com.zburzhynski.jsender.api.repository.IClientRepository;
import com.zburzhynski.jsender.api.service.IClientService;
import com.zburzhynski.jsender.impl.domain.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IClientService} interface.
 * <p/>
 * Date: 08.02.2017
 *
 * @author Nikita Shevtsov
 */
@Service("clientService")
@Transactional(readOnly = true)
public class ClientService implements IClientService<String, Client> {

    @Autowired
    private IClientRepository<String, Client> clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client getById(String id) {
        return clientRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> getByCriteria(ClientSearchCriteria searchCriteria) {
        return clientRepository.findByCriteria(searchCriteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(ClientSearchCriteria searchCriteria) {
        return clientRepository.countByCriteria(searchCriteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Client client) {
        boolean result = false;
        if (client != null) {
            clientRepository.saveOrUpdate(client);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Client client) {
        clientRepository.delete(client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

}
