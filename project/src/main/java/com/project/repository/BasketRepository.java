package com.project.repository;

import com.project.entity.OrderEntity;
import com.project.entity.basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<basket, Long> {

}
