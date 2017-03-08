package com.zburzhynski.jsender.impl.repository;

import com.zburzhynski.jsender.api.repository.IEmployeeSendingServiceParamRepository;
import com.zburzhynski.jsender.impl.domain.EmployeeSendingServiceParam;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link IEmployeeSendingServiceParamRepository} interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Repository
public class EmployeeSendingServiceParamRepository extends AbstractBaseRepository<String, EmployeeSendingServiceParam>
    implements IEmployeeSendingServiceParamRepository<String, EmployeeSendingServiceParam> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends EmployeeSendingServiceParam> getDomainClass() {
        return EmployeeSendingServiceParam.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
