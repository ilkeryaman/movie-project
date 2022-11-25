package com.eri.dal.service;

import com.eri.dal.entity.DirectorEntity;

import java.util.List;

public interface IDirectorService {
    DirectorEntity saveDirector(DirectorEntity directorEntity);

    List<DirectorEntity> getDirectorList();

    DirectorEntity getDirectorById(Long directorId);

    DirectorEntity updateDirector(DirectorEntity directorEntity, Long directorId);
}
