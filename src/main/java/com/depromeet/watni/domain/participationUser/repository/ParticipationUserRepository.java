package com.depromeet.watni.domain.participationUser.repository;

import com.depromeet.watni.domain.participationUser.domain.ParticipationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationUserRepository extends JpaRepository<ParticipationUser, Long> {
}
