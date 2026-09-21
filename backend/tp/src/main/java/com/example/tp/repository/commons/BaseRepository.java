package com.example.tp.repository.commons;



import com.example.tp.models.commons.AbstractPersistableWithIdSetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends AbstractPersistableWithIdSetter<Long>> extends JpaRepository<T, Long> {
}

