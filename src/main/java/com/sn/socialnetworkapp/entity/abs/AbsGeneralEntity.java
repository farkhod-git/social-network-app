package com.sn.socialnetworkapp.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AbsIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

}
