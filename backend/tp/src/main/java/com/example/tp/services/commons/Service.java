package com.example.tp.services.commons;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Service<T, ID> {
    T create(@Valid T entity) throws ServiceException;

    T update(@Valid T entity) throws ServiceException;

    Optional<T> findById(ID id) throws ServiceException;

    Page<T> findAll(Pageable pageable) throws ServiceException;

    void deleteById(ID id) throws ServiceException;

    void delete(T entity) throws ServiceException;

    void deleteAll() throws ServiceException;

    long count() throws ServiceException;

    boolean existsById(ID id) throws ServiceException;
}
