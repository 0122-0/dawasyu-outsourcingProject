package com.example.dawasyu.repository;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

	int countByOwner(User owner);

	default Store findStoreByIdOrElseThrow(Long storeId){
		return findById(storeId).orElseThrow(()->new CustomException(ErrorCode.STORE_NOT_FOUND));
	}

	@Query("""
    SELECT s 
    FROM Store s 
    WHERE s.deletedAt IS NULL 
      AND (
          (s.openTime <= s.closeTime AND :queryTime BETWEEN s.openTime AND s.closeTime)
          OR
          (s.openTime > s.closeTime AND (:queryTime >= s.openTime OR :queryTime <= s.closeTime))
      )
	""")
	List<Store> findStoresOpenAt(@Param("queryTime") LocalTime queryTime);

	List<Store> findByNameContainingAndDeletedAtIsNull(String name);
}