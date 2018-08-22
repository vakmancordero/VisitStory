package com.kaizensoftware.visitstory.common.service;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import com.kaizensoftware.visitstory.common.persistence.repository.BaseRepository;
import com.kaizensoftware.visitstory.common.util.ConvertUtil;
import com.kaizensoftware.visitstory.common.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public abstract class BaseService<R extends BaseRepository, E extends BaseEntity> {

    @Autowired
    protected R repository;

    @Autowired
    protected ConvertUtil convertUtils;

    @Autowired
    protected ObjectUtil objectUtils;

    private Class<E> clazz;

    public Class<E> clazz() {

        if (this.clazz == null) {

            Class<?> actualClass = this.getClass();

            ParameterizedType pt = (ParameterizedType) actualClass.getGenericSuperclass();

            this.clazz = (Class<E>) pt.getActualTypeArguments()[1];
        }

        return this.clazz;
    }

    protected <T, K> K create(T createDTO, Class<K> clazz) throws Exception {

        requireNonNull(createDTO);

        E entity = convertUtils.convert(createDTO, this.clazz());
        K outDTO = convertUtils.convert(repository.saveAndFlush(entity), clazz);

        return outDTO;
    }

    protected <T> T findById(long id, Class<T> clazz) throws Exception {

        checkArgument(id != 0, "The given id must not be null!");

        Optional<E> optionalEntity = this.repository.findById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();
        T optionalDTO = convertUtils.convert(entity, clazz);

        return optionalDTO;
    }

    protected <T> T findInactiveById(long id, Class<T> clazz) throws Exception {

        checkArgument(id != 0, "The given id must not be null!");

        Optional<E> optionalEntity = this.repository.findInactiveById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();
        T optionalDTO = convertUtils.convert(entity, clazz);

        return optionalDTO;
    }

    protected <T> List<T> findAll(Class<T> clazz) {

        List<T> outDTOList = convertUtils.convert(repository.findAll(), clazz);

        return outDTOList;
    }

    protected <T> List<T> findAllInactive(Class<T> clazz) {

        List<T> outDTOList = convertUtils.convert(repository.findAllInactive(), clazz);

        return outDTOList;
    }

    protected <T, K> T update(long id, K updateDTO, Class<T> clazz, boolean partialUpdate) throws Exception {

        checkArgument(id != 0);
        requireNonNull(updateDTO);

        Optional<E> optionalEntity = this.repository.findById(id);

        optionalEntity.orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "does not exists"));

        E entity = optionalEntity.get();

        if (partialUpdate) {
            objectUtils.merge(updateDTO, entity);
        } else {
            convertUtils.map(updateDTO, entity);
        }

        repository.saveAndFlush(entity);

        T outDTO = convertUtils.convert(entity, clazz);
        return outDTO;
    }

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

        T outDTO = convertUtils.convert(entity, clazz);

        return outDTO;
    }

    protected void deleteAll() throws Exception {
        this.repository.deleteAll();
    }

}
