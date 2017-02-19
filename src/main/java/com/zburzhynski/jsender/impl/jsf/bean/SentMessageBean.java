package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.SENT_MESSAGES;
import com.zburzhynski.jsender.api.criteria.SentMessageSearchCriteria;
import com.zburzhynski.jsender.api.service.ISentMessageService;
import com.zburzhynski.jsender.impl.domain.SentMessage;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Sent message bean.
 * <p/>
 * Date: 19.02.2017
 *
 * @author Nikita Shevtsou
 */
@ManagedBean
@SessionScoped
public class SentMessageBean {

    private SentMessage sentMessage;

    private LazyDataModel<SentMessage> sentMessageModel;

    private Dialog sentMessageDetailDialog = new Dialog();

    @ManagedProperty(value = "#{sentMessageService}")
    private ISentMessageService<String, SentMessage> sentMessageService;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        sentMessageModel = new LazyDataModel<SentMessage>() {
            @Override
            public List<SentMessage> load(int first, int pageSize, String sortField,
                                          SortOrder sortOrder, Map<String, Object> filters) {
                SentMessageSearchCriteria searchCriteria = new SentMessageSearchCriteria();
                searchCriteria.setStart(Long.valueOf(first));
                searchCriteria.setEnd(Long.valueOf(first + pageSize));
                int count = sentMessageService.countByCriteria(searchCriteria);
                setRowCount(count);
                return sentMessageService.getByCriteria(searchCriteria);
            }
        };
    }

    /**
     * Shows message detail dialog.
     */
    public void showMessageDetailDialog() {
        sentMessageDetailDialog.setVisible(true);
    }

    /**
     * Hides message detail dialog.
     */
    public void hideMessageDetailDialog() {
        sentMessageDetailDialog.setVisible(false);
    }

    /**
     * Removes sent message.
     *
     * @return path for navigating
     */
    public String removeSentMessage() {
        sentMessageService.delete(sentMessage);
        return SENT_MESSAGES.getPath();
    }

    public Integer getRowCount() {
        return settingBean.getSentMessagesPerPage();
    }

    public SentMessage getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(SentMessage sentMessage) {
        this.sentMessage = sentMessage;
    }

    public LazyDataModel<SentMessage> getSentMessageModel() {
        return sentMessageModel;
    }

    public void setSentMessageModel(LazyDataModel<SentMessage> sentMessageModel) {
        this.sentMessageModel = sentMessageModel;
    }

    public Dialog getSentMessageDetailDialog() {
        return sentMessageDetailDialog;
    }

    public void setSentMessageDetailDialog(Dialog sentMessageDetailDialog) {
        this.sentMessageDetailDialog = sentMessageDetailDialog;
    }

    public void setSentMessageService(ISentMessageService<String, SentMessage> sentMessageService) {
        this.sentMessageService = sentMessageService;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
