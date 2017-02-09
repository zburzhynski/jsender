package com.zburzhynski.jsender.api.service;

import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Base service that represent base operations (CRUD) for entity.
 * <p/>
 * Date: 24.04.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of objects managed by service.
 * @author Vladimir Zburzhynski
 */
public interface IBaseService<ID, T extends IDomain> {

    /**
     * Gets object by id.
     *
     * @param id unique identifier of entity
     * @return object
     */
    T getById(ID id);

    /**
     * Saves or updates existing entity.
     *
     * @param object entity to savePatient or update
     * @return true if success, false otherwise
     */
    boolean saveOrUpdate(T object);

    /**
     * Deletes entity.
     *
     * @param object entity to delete
     */
    void delete(T object);

    /**
     * Gets list of all entities from database.
     *
     * @return list of entities
     */
    List<T> getAll();

}
