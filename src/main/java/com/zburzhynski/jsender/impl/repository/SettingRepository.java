package com.zburzhynski.jsender.impl.repository;

import com.zburzhynski.jsender.api.domain.ISetting;
import com.zburzhynski.jsender.api.domain.SettingCategory;
import com.zburzhynski.jsender.api.domain.Settings;
import com.zburzhynski.jsender.api.repository.ISettingRepository;
import com.zburzhynski.jsender.impl.domain.Setting;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.annotation.Resource;

/**
 * Implementation of {@link ISettingRepository} interface.
 * <p/>
 * Date: 03.08.2016
 *
 * @author Vladimir Zburzhynski
 */
@Repository("settingRepository")
public class SettingRepository implements ISettingRepository {

    @Resource
    private SessionFactory sessionFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public ISetting findByName(Settings name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Setting.class);
        criteria.add(Restrictions.eq(Setting.P_NAME, name.name()).ignoreCase());
        return (ISetting) criteria.uniqueResult();
    }

    @Override
    public List<ISetting> findByCategory(SettingCategory category) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Setting.class);
        criteria.add(Restrictions.eq(Setting.P_CATEGORY, category));
        return criteria.list();
    }

}
