package com.depromeet.watni.domain.conference.repository;

import com.depromeet.watni.domain.conference.domain.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
}
