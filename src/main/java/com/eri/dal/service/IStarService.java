package com.eri.dal.service;

import com.eri.dal.entity.StarEntity;

import java.util.List;

public interface IStarService {
    StarEntity saveStar(StarEntity starEntity);

    List<StarEntity> getStarList();

    StarEntity getStarById(Long starId);

    StarEntity updateStar(StarEntity starEntity, Long starId);
}
