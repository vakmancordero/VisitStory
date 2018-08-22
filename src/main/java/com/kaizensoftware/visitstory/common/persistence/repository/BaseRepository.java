package com.kaizensoftware.visitstory.common.persistence.repository;

import com.kaizensoftware.visitstory.common.persistence.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    @Query("select e from #{#entityName} e where e.id = ?1 and e.isActive = true")
    Optional<T> findById(ID id);

    @Query("select e from #{#entityName} e where e.id = ?1 and e.isActive = false")
    Optional<T> findInactiveById(ID id);

    @Override
    @Query("select e from #{#entityName} e where e.isActive = true")
    List<T> findAll();

    @Query("select e from #{#entityName} e where e.isActive = false")
    List<T> findAllInactive();

    @Modifying
    default void softDeleteAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "The given Iterable of entities not be null!");
        for (T entity : entities) { softDelete(entity); }
    }

    @Modifying
    @Query("update #{#entityName} e set e.isActive = false where e.id = ?1")
    void softDeleteById(Long id);

    default void softDelete(T entity) {
        this.softDeleteById(entity.getId());
    }

}

