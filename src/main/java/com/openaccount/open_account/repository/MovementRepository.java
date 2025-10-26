package com.openaccount.open_account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openaccount.open_account.data.model.Movement;

public interface MovementRepository extends JpaRepository<Movement,Long>{
	
	public List<Movement> findByCustomerId(Long id);

}
