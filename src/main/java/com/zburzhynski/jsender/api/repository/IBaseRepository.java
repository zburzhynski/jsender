package com.zburzhynski.jsender.api.repository;

import com.zburzhynski.jsender.api.domain.IDomain;

import java.util.List;

/**
 * Base repository that represents basic operations for domain entity.
 * <p/>
 * Copyright (C) 2012 copyright.com
 * <p/>
 * Date: 23.04.15
 *
 * @param <ID> The type of unique identifier.
 * @param <T>  The type of objects managed by repository.
 * @author Uladzimir Zburzhynski
 */
public interface IBaseRepository<ID, T extends IDomain> {

    /**
     * Inserts object.
     *
     * @param object object to insert.
     * @return generated id
     */
    ID insert(T object);

    /**
     * Replicates object.
     *
     * @param object object to replicate
     */
    void replicate(T object);

    /**
     * Finds object by id.
     *
     * @param id object id
     * @return object
     */
    T findById(ID id);

    /**
     * Updates the object.
     *
     * @param object Object to be update
     */
    void update(T object);

    /**
     * Saves or updates object.
     *
     * @param object object to save or update
     */
    void saveOrUpdate(T object);

    /**
     * Finds all entities in database.
     *
     * @return list of entities
     */
    List<T> findAll();

}