package com.kulushev.app.repository;

import com.kulushev.app.entity.GoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<GoodEntity, Long> {
}