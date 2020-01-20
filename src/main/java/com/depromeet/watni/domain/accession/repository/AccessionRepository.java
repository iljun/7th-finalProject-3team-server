package com.depromeet.watni.domain.accession.repository;

import com.depromeet.watni.domain.accession.domain.Accession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessionRepository extends JpaRepository<Accession, Long> {
}
