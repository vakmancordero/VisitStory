package com.kaizensoftware.visitstory.common.persistence.repository;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    @Transactional
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = false")
    Optional<T> findById(ID id);

    @Transactional
    @Query("select e from #{#entityName} e where e.id = ?1 and e.deleted = true")
    Optional<T> findInactiveById(ID id);

    @Override
    @Transactional
    @Query("select e from #{#entityName} e where e.deleted = true")
    List<T> findAll();

    @Query("select e from #{#entityName} e where e.deleted = false")
    List<T> findAllInactive();

    @Override
    @Transactional
    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleted = false")
    List<T> findAllById(Iterable<ID> ids);

    @Transactional
    @Query("select e from #{#entityName} e where e.id in ?1 and e.deleted = true")
    List<T> findAllDeletedById(Iterable<ID> ids);

    @Transactional
    @Modifying
    default void softDeleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        for (T entity : entities) { softDelete(entity); }
    }

    @Modifying
    @Query("update #{#entityName} e set e.deleted = false where e.id = ?1")
    void softDeleteById(Long id);

    default void softDelete(T entity) {
        this.softDeleteById(entity.getId());
    }

}

