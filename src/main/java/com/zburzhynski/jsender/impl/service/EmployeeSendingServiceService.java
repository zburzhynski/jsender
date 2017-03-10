package com.zburzhynski.jsender.impl.service;

import com.zburzhynski.jsender.api.criteria.EmployeeSendingServiceSearchCriteria;
import com.zburzhynski.jsender.api.repository.IEmployeeSendingServiceRepository;
import com.zburzhynski.jsender.api.service.IEmployeeSendingServiceService;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IEmployeeSendingServiceService} interface.
 * <p/>
 * Date: 07.03.2017
 *
 * @author Nikita Shevtsou
 */
@Service("employeeSendingServiceService")
@Transactional(readOnly = true)
public class EmployeeSendingServiceService implements IEmployeeSendingServiceService<String, EmployeeSendingService> {

    @Autowired
    private IEmployeeSendingServiceRepository<String, EmployeeSendingService> serviceRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployeeSendingService> getByCriteria(EmployeeSendingServiceSearchCriteria searchCriteria,
                                                      Long start, Long end) {
        return serviceRepository.findByCriteria(searchCriteria, start, end);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByCriteria(EmployeeSendingServiceSearchCriteria searchCriteria) {
        return serviceRepository.countByCriteria(searchCriteria);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeSendingService getById(String id) {
        return serviceRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(EmployeeSendingService service) {
        boolean result = false;
        if (service != null) {
            serviceRepository.saveOrUpdate(service);
            result = true;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(EmployeeSendingService service) {
        serviceRepository.delete(service);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EmployeeSendingService> getAll() {
        return serviceRepository.findAll();
    }

}
