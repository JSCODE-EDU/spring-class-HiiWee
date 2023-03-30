package com.jscode.spring.Store.repository;

import com.jscode.spring.Store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
