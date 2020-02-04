package com.depromeet.watni.domain.accession.repository;

import com.depromeet.watni.domain.accession.domain.Accession;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessionRepository extends JpaRepository<Accession, Long> {
    Optional<Accession> findOneByGroupAndMember(Group group, Member member);
    Optional<List<Accession>> findAllByMember (Member member);
}
