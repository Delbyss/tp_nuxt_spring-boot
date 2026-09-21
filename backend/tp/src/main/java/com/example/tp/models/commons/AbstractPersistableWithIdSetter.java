package com.example.tp.models.commons;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.io.Serializable;

@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class AbstractPersistableWithIdSetter<PK extends Serializable> extends AbstractPersistable<PK> {

    @Version
    @Column(name = "version", nullable = false)
    @Getter
    @Setter
    private Long version;

    @Override
    public void setId(PK id) {
        super.setId(id);
    }
}
