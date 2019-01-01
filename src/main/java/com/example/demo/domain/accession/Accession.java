package com.example.demo.domain.accession;

import javax.persistence.*;

@Table
@Entity(name = "accession")
public class Accession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accession_id")
    private long id;

    @Column(name = "accession_type")
    @Enumerated(value = EnumType.STRING)
    private AccessionType accessionType;

    @Column(name = "accession_status")
    @Enumerated(value = EnumType.STRING)
    private AccessionStatus accessionStatus;

    @Column(name = "member_id")
    private long memeberId;

    // TODO createdAt, modifiedAt
}
