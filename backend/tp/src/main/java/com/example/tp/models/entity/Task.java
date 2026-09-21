package com.example.tp.models.entity;

import org.hibernate.validator.constraints.Length;

import com.example.tp.models.commons.AbstractPersistableWithIdSetter;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
//@ pas de hash code, pas d'élément unique, bug possible seulement si un même objet est récupéré plus d'une fois de la base de données, il ne serra pas égal a lui même
@ToString(callSuper = true, of = {"title", "description", "done"})
public class Task extends AbstractPersistableWithIdSetter<Long> {


    @Getter
    @Setter
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Length(max = 255, message = "Le titre ne peut pas dépasser 255 caractères")
    private String title;

    @Getter
    @Setter
    @Length(max = 130_560, message = "La description ne peut pas dépasser 130 560 caractères")
    private String description;

    @Getter
    @Setter
    private Boolean done = false;

}
