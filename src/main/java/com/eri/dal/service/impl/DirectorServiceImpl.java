package com.eri.dal.service.impl;

import com.eri.dal.entity.DirectorEntity;
import com.eri.dal.repository.DirectorRepository;
import com.eri.dal.service.IDirectorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements IDirectorService {

    @Resource
    private DirectorRepository directorRepository;

    @Override
    public DirectorEntity saveDirector(DirectorEntity directorEntity) {
        return directorRepository.save(directorEntity);
    }

    @Override
    public List<DirectorEntity> getDirectorList() {
        return (List<DirectorEntity>) directorRepository.findAll();
    }

    @Override
    public DirectorEntity getDirectorById(Long directorId) {
        Optional<DirectorEntity> directorEntity = directorRepository.findById(directorId);
        return directorEntity.isPresent() ? directorEntity.get() : null;
    }

    @Override
    public DirectorEntity updateDirector(DirectorEntity directorEntity, Long directorId) {
        DirectorEntity directorAtDB = directorRepository.findById(directorId).get();

        if (Objects.nonNull(directorEntity.getName()) && !"".equalsIgnoreCase(directorEntity.getName())) {
            directorAtDB.setName(directorEntity.getName());
        }

        if (Objects.nonNull(directorEntity.getSurname()) && !"".equalsIgnoreCase(directorEntity.getSurname())) {
            directorAtDB.setSurname(directorEntity.getSurname());
        }

        return directorRepository.save(directorAtDB);
    }
}
