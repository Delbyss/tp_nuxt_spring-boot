package com.example.tp.services.commons;

import jakarta.validation.groups.Default;
import com.example.tp.models.commons.AbstractPersistableWithIdSetter;
import com.example.tp.models.validators.groups.CreateGroup;
import com.example.tp.models.validators.groups.UpdateGroup;
import com.example.tp.repository.commons.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public class AbstractService<T extends AbstractPersistableWithIdSetter<Long>, R extends BaseRepository<T>>
        implements Service<T, Long> {

    protected final R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    @Validated({ Default.class, CreateGroup.class })
    public T create(T entity) throws ServiceException {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Error saving entity", e);
        }
    }

    @Override
    @Transactional
    @Validated({ Default.class, UpdateGroup.class })
    public T update(T entity) throws ServiceException {
        if (entity.getId() == null) {
            throw new ServiceException("Entity ID must not be null for update");
        }
        if (!repository.existsById(entity.getId())) {
            throw new ServiceException("Entity with ID " + entity.getId() + " does not exist");
        }
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new ServiceException("Error updating entity", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(Long id) throws ServiceException {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) throws ServiceException {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws ServiceException {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(T entity) throws ServiceException {
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteAll() throws ServiceException {
        repository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long count() throws ServiceException {
        return repository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) throws ServiceException {
        return repository.existsById(id);
    }
}
