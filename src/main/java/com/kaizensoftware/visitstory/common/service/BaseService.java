package com.kaizensoftware.visitstory.common.service;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import com.kaizensoftware.visitstory.common.persistence.repository.SoftDeleteRepository;
import com.kaizensoftware.visitstory.common.util.ConvertUtil;
import com.kaizensoftware.visitstory.common.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public abstract class BaseService<R extends SoftDeleteRepository, E extends BaseEntity> {

    @Autowired
    protected R repository;

    @Autowired
    protected ConvertUtil convertUtil;

    @Autowired
    protected ObjectUtil objectUtil;

    private Class<E> clazz;

    @SuppressWarnings("unchecked")
    private Class<E> clazz() {

        if (this.clazz == null) {

            Class<?> actualClass = this.getClass();

            ParameterizedType pt = (ParameterizedType) actualClass.getGenericSuperclass();

            this.clazz = (Class<E>) pt.getActualTypeArguments()[1];
        }

        return this.clazz;
    }

    protected <T, K> K create(T createDTO, Class<K> clazz) throws Exception {

        requireNonNull(createDTO);

        E entity = convertUtil.convert(createDTO, this.clazz());
        K outDTO = convertUtil.convert(repository.saveAndFlush(entity), clazz);

        return outDTO;
    }

    @SuppressWarnings("unchecked")
    protected <T> T findById(long id, Class<T> clazz) throws Exception {

        checkArgument(id != 0, "The given id must not be null!");

        Optional<E> optionalEntity = this.repository.findById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();

        return convertUtil.convert(entity, clazz);
    }

    @SuppressWarnings("unchecked")
    protected <T> T findInactiveById(long id, Class<T> clazz) throws Exception {

        checkArgument(id != 0, "The given id must not be null!");

        Optional<E> optionalEntity = this.repository.findInactiveById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();

        return convertUtil.convert(entity, clazz);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findAll(Class<T> clazz) {
        return convertUtil.convert(repository.findAll(), clazz);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> findAllInactive(Class<T> clazz) {
        return convertUtil.convert(repository.findAllInactive(), clazz);
    }

    @SuppressWarnings("unchecked")
    protected <T, K> T update(long id, K updateDTO, Class<T> clazz, boolean partialUpdate) throws Exception {

        checkArgument(id != 0);
        requireNonNull(updateDTO);

        Optional<E> optionalEntity = this.repository.findById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();

        if (partialUpdate) {
            objectUtil.merge(updateDTO, entity);
        } else {
            convertUtil.map(updateDTO, entity);
        }

        repository.saveAndFlush(entity);

        return convertUtil.convert(entity, clazz);
    }

    @SuppressWarnings("unchecked")
    protected <T> T delete(long id, Class<T> clazz, boolean hardDelete) throws Exception {

        checkArgument(id != 0, "The given id must not be null!");

        Optional<E> optionalEntity = this.repository.findById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();

        if (hardDelete) {
            repository.delete(entity);
        } else {
            repository.softDelete(entity);
        }

        return convertUtil.convert(entity, clazz);
    }

    @SuppressWarnings("unchecked")
    protected boolean existsById(Long id) {
        return repository.existsById(id);
    }

    protected void deleteAll() throws Exception {
        this.repository.deleteAll();
    }

}
