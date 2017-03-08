package com.zburzhynski.jsender.impl.repository;

import com.zburzhynski.jsender.api.repository.ISendingServiceParamRepository;
import com.zburzhynski.jsender.impl.domain.SendingServiceParam;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Implementation of {@link SendingServiceParamRepository} interface.
 * <p/>
 * Date: 05.03.2017
 *
 * @author Nikita Shevtsou
 */
@Repository
public class SendingServiceParamRepository extends AbstractBaseRepository<String, SendingServiceParam>
    implements ISendingServiceParamRepository<String, SendingServiceParam> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends SendingServiceParam> getDomainClass() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return null;
    }

}
