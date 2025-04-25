package com.example.dawasyu.repository;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.entity.Menu;
import com.example.dawasyu.domain.store.entity.Store;
import com.example.dawasyu.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    int countByOwner(User owner);

	default Store findStoreByIdOrElseThrow(Long storeId){
		return findById(storeId).orElseThrow(()->new CustomException(ErrorCode.STORE_NOT_FOUND));
	}
}
