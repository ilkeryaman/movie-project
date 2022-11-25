package com.eri.dal.service.impl;

import com.eri.dal.entity.StarEntity;
import com.eri.dal.repository.StarRepository;
import com.eri.dal.service.IStarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StarServiceImpl implements IStarService {

    @Resource
    private StarRepository starRepository;

    @Override
    public StarEntity saveStar(StarEntity starEntity) {
        return starRepository.save(starEntity);
    }

    @Override
    public List<StarEntity> getStarList() {
        return (List<StarEntity>) starRepository.findAll();
    }

    @Override
    public StarEntity getStarById(Long starId) {
        Optional<StarEntity> starEntity = starRepository.findById(starId);
        return starEntity.isPresent() ? starEntity.get() : null;
    }

    @Override
    public StarEntity updateStar(StarEntity starEntity, Long starId) {
        StarEntity starAtDB = starRepository.findById(starId).get();

        if (Objects.nonNull(starEntity.getName()) && !"".equalsIgnoreCase(starEntity.getName())) {
            starAtDB.setName(starEntity.getName());
        }

        if (Objects.nonNull(starEntity.getSurname()) && !"".equalsIgnoreCase(starEntity.getSurname())) {
            starAtDB.setSurname(starEntity.getSurname());
        }

        return starRepository.save(starAtDB);
    }
}
