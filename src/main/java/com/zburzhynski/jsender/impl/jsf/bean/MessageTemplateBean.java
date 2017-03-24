package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATE;
import static com.zburzhynski.jsender.api.domain.View.MESSAGE_TEMPLATES;
import static com.zburzhynski.jsender.api.domain.View.SENDING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import com.zburzhynski.jsender.api.criteria.MessageTemplateSearchCriteria;
import com.zburzhynski.jsender.api.domain.TemplateTag;
import com.zburzhynski.jsender.api.domain.View;
import com.zburzhynski.jsender.api.service.IMessageTemplateService;
import com.zburzhynski.jsender.impl.domain.MessageTemplate;
import com.zburzhynski.jsender.impl.jsf.validator.MessageTemplateSelectValidator;
import com.zburzhynski.jsender.impl.jsf.validator.MessageTemplateValidator;
import com.zburzhynski.jsender.impl.util.BeanUtils;
import com.zburzhynski.jsender.impl.util.PropertyReader;
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

    private static final String SENDING_BEAN = "sendingBean";

    private static final String BR_HTML_TAG = "<br>";

    private static final String DIV_HTML_CLOSE_TAG = "</div>";

    private View redirectFrom = MESSAGE_TEMPLATES;

    private MessageTemplate messageTemplate;

    private MessageTemplate selectedMessageTemplate;

    private LazyDataModel<MessageTemplate> messageTemplateModel;

    private List<MessageTemplate> datasource;

    @ManagedProperty(value = "#{propertyReader}")
    private PropertyReader reader;

    @ManagedProperty(value = "#{messageTemplateValidator}")
    private MessageTemplateValidator messageTemplateValidator;

    @ManagedProperty(value = "#{messageTemplateSelectValidator}")
    private MessageTemplateSelectValidator templateSelectValidator;

    @ManagedProperty(value = "#{messageTemplateService}")
    private IMessageTemplateService messageTemplateService;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        messageTemplateModel = new MessageTemplateDataModel();
    }

    /**
     * Inserts template tag.
     *
     * @param tag tag to insert
     */
    public void insertTag(TemplateTag tag) {
        String text = isNotBlank(messageTemplate.getText()) ? messageTemplate.getText() : EMPTY;
        int insertIndex = text.lastIndexOf(BR_HTML_TAG + DIV_HTML_CLOSE_TAG);
        if (insertIndex != -1) {
            messageTemplate.setText(new StringBuilder(text).insert(
                insertIndex, reader.readProperty(tag.getValue())).toString());
            return;
        }
        insertIndex = text.lastIndexOf(DIV_HTML_CLOSE_TAG);
        if (insertIndex != -1) {
            messageTemplate.setText(new StringBuilder(text).insert(
                insertIndex, reader.readProperty(tag.getValue())).toString());
            return;
        }
        messageTemplate.setText(text + reader.readProperty(tag.getValue()));
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
        boolean valid = messageTemplateValidator.validate(messageTemplate);
        if (!valid) {
            return null;
        }
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

    /**
     * Select message template.
     *
     * @return path for navigation
     */
    public String selectMessageTemplate() {
        boolean valid = templateSelectValidator.validate(selectedMessageTemplate);
        if (!valid) {
            return null;
        }
        selectToSendingForm();
        return redirectFrom.getPath();
    }

    /**
     * Cancel select message template.
     *
     * @return path for navigation
     */
    public String cancelMessageTemplate() {
        return redirectFrom.getPath();
    }

    public Integer getRowCount() {
        return settingBean.getMessageTemplatesPerPage();
    }

    public View getRedirectFrom() {
        return redirectFrom;
    }

    public void setRedirectFrom(View redirectFrom) {
        this.redirectFrom = redirectFrom;
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

    public MessageTemplate getSelectedMessageTemplate() {
        return selectedMessageTemplate;
    }

    public void setSelectedMessageTemplate(MessageTemplate selectedMessageTemplate) {
        this.selectedMessageTemplate = selectedMessageTemplate;
    }

    public void setReader(PropertyReader reader) {
        this.reader = reader;
    }

    public void setMessageTemplateValidator(MessageTemplateValidator messageTemplateValidator) {
        this.messageTemplateValidator = messageTemplateValidator;
    }

    public void setTemplateSelectValidator(MessageTemplateSelectValidator templateSelectValidator) {
        this.templateSelectValidator = templateSelectValidator;
    }

    public void setMessageTemplateService(IMessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

    private void selectToSendingForm() {
        if (SENDING.equals(redirectFrom)) {
            SendingBean sendingBean = BeanUtils.getSessionBean(SENDING_BEAN);
            if (sendingBean != null) {
                sendingBean.getMessageToSend().setSubject(selectedMessageTemplate.getSubject());
                sendingBean.getMessageToSend().setText(selectedMessageTemplate.getText());
            }
        }
    }

    private class MessageTemplateDataModel extends LazyDataModel<MessageTemplate> {

        /**
         * {@inheritDoc}
         */
        @Override
        public List<MessageTemplate> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                          Map<String, Object> filters) {
            MessageTemplateSearchCriteria searchCriteria = new MessageTemplateSearchCriteria();
            int count = messageTemplateService.countByCriteria(searchCriteria);
            setRowCount(count);
            datasource = messageTemplateService.getByCriteria(searchCriteria, Long.valueOf(first),
                Long.valueOf(first + pageSize));
            return datasource;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MessageTemplate getRowData(String rowKey) {
            for (MessageTemplate object : datasource) {
                if (object.getId().equals(rowKey)) {
                    return object;
                }
            }
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Object getRowKey(MessageTemplate object) {
            return object.getId();
        }

    }

}
