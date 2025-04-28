package com.example.dawasyu.repository;

import com.example.dawasyu.common.error.CustomException;
import com.example.dawasyu.common.error.ErrorCode;
import com.example.dawasyu.domain.menu.entity.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	default Menu findMenuByIdOrElseThrow(Long menuId){
		return findById(menuId).orElseThrow(()->new CustomException(ErrorCode.MENU_NOT_FOUND));
	}
}
