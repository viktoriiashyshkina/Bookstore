
package com.project.repository;

import com.project.entity.BasketDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketDetailsRepository extends JpaRepository<BasketDetails, Long> {

}
