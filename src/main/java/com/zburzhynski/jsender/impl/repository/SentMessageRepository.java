package com.zburzhynski.jsender.impl.repository;

import com.zburzhynski.jsender.api.repository.ISentMessageRepository;
import com.zburzhynski.jsender.impl.domain.SentMessage;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link ISentMessageRepository} interface.
 * <p/>
 * Date: 16.02.2017
 *
 * @author Nikita Shevtsov
 */
@Repository
public class SentMessageRepository extends AbstractBaseRepository<String, SentMessage>
    implements ISentMessageRepository<String, SentMessage> {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<? extends SentMessage> getDomainClass() {
        return SentMessage.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, Boolean> getDefaultSorting() {
        return new HashMap<>();
    }

}
