package com.eri.dal.repository;

import com.eri.dal.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarRepository extends JpaRepository<StarEntity, Long> {
}
