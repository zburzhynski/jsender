package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATE;
import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATES;
import com.zburzhynski.jsender.api.criteria.MessageTemplateSearchCriteria;
import com.zburzhynski.jsender.api.service.IMessageTemplateService;
import com.zburzhynski.jsender.impl.domain.MessageTemplate;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Message template bean.
 * <p/>
 * Date: 21.02.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class MessageTemplateBean {

    private MessageTemplate messageTemplate;

    private LazyDataModel<MessageTemplate> messageTemplateModel;

    @ManagedProperty(value = "#{messageTemplateService}")
    private IMessageTemplateService<String, MessageTemplate> messageTemplateService;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        messageTemplateModel = new LazyDataModel<MessageTemplate>() {
            @Override
            public List<MessageTemplate> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                              Map<String, Object> filters) {
                MessageTemplateSearchCriteria searchCriteria = new MessageTemplateSearchCriteria();
                int count = messageTemplateService.countByCriteria(searchCriteria);
                setRowCount(count);
                return messageTemplateService.getByCriteria(searchCriteria, Long.valueOf(first),
                    Long.valueOf(first + pageSize));
            }
        };
    }


    /**
     * Adds message template.
     *
     * @return path for navigating
     */
    public String addMessageTemplate() {
        messageTemplate = new MessageTemplate();
        return MESSAGE_TEMPLATE.getPath();
    }

    /**
     * Saves message template.
     *
     * @return path for navigating
     */
    public String saveMessageTemplate() {
        messageTemplateService.saveOrUpdate(messageTemplate);
        return MESSAGE_TEMPLATES.getPath();
    }

    /**
     * Removes message template.
     *
     * @return path for navigating
     */
    public String removeMessageTemplate() {
        messageTemplateService.delete(messageTemplate);
        return MESSAGE_TEMPLATES.getPath();
    }

    public Integer getRowCount() {
        return settingBean.getMessageTemplatesPerPage();
    }

    public MessageTemplate getMessageTemplate() {
        return messageTemplate;
    }

    public void setMessageTemplate(MessageTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public LazyDataModel<MessageTemplate> getMessageTemplateModel() {
        return messageTemplateModel;
    }

    public void setMessageTemplateModel(LazyDataModel<MessageTemplate> messageTemplateModel) {
        this.messageTemplateModel = messageTemplateModel;
    }

    public void setMessageTemplateService(IMessageTemplateService<String, MessageTemplate> messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
