package com.depromeet.watni.domain.manager.repository;

import com.depromeet.watni.domain.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
