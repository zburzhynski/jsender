package com.zburzhynski.jsender.impl.jsf.bean;

import static com.zburzhynski.jsender.api.domain.View.CLIENT;
import static com.zburzhynski.jsender.api.domain.View.CLIENTS;
import com.zburzhynski.jsender.api.criteria.ClientSearchCriteria;
import com.zburzhynski.jsender.api.service.IClientService;
import com.zburzhynski.jsender.impl.domain.Client;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Client bean.
 * <p/>
 * Date: 08.02.2017
 *
 * @author Nikita Shevtsov
 */
@ManagedBean
@SessionScoped
public class ClientBean {

    private Client client;

    private LazyDataModel<Client> clientModel;

    @ManagedProperty(value = "#{clientService}")
    private IClientService clientService;

    @ManagedProperty(value = "#{settingBean}")
    private SettingBean settingBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        clientModel = new LazyDataModel<Client>() {
            @Override
            public List<Client> load(int first, int pageSize, String sortField,
                                     SortOrder sortOrder, Map<String, Object> filters) {
                ClientSearchCriteria searchCriteria = new ClientSearchCriteria();
                searchCriteria.setStart(Long.valueOf(first));
                searchCriteria.setEnd(Long.valueOf(first + pageSize));
                List<Client> clients = clientService.getByCriteria(searchCriteria);
                int count = clientService.countByCriteria(searchCriteria);
                setRowCount(count);
                return clients;
            }
        };
    }

    /**
     * Adds client.
     *
     * @return path for navigating
     */
    public String addClient() {
        client = new Client();
        return CLIENT.getPath();
    }

    /**
     * Edits client.
     *
     * @return path for navigating
     */
    public String editClient() {
        return CLIENT.getPath();
    }

    /**
     * Saves client.
     *
     * @return path for navigating
     */
    public String saveClient() {
        clientService.saveOrUpdate(client);
        return CLIENTS.getPath();
    }

    /**
     * Removes client.
     *
     * @return path for navigating
     */
    public String removeClient() {
        clientService.delete(client);
        return CLIENTS.getPath();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LazyDataModel<Client> getClientModel() {
        return clientModel;
    }

    public void setClientModel(LazyDataModel<Client> clientModel) {
        this.clientModel = clientModel;
    }

    public Integer getRowCount() {
        return settingBean.getClientsPerPageCount();
    }

    public void setClientService(IClientService clientService) {
        this.clientService = clientService;
    }

    public void setSettingBean(SettingBean settingBean) {
        this.settingBean = settingBean;
    }

}
