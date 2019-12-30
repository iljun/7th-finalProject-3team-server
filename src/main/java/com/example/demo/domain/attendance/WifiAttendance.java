package com.example.demo.domain.attendance;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WIFI")
public class WifiAttendance extends BaseAttendance {

    @Column(name = "ip")
    private long ip;
}
