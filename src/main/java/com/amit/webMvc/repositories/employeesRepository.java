package com.amit.webMvc.repositories;

import com.amit.webMvc.entity.EmployeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface employeesRepository extends JpaRepository<EmployeesEntity,Long> {
}
