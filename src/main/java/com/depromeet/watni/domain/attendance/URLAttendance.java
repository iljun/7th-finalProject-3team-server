package com.depromeet.watni.domain.attendance;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("URL")
public class URLAttendance extends BaseAttendance {


}
