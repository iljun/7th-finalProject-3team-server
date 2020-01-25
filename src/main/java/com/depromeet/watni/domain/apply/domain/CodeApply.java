package com.depromeet.watni.domain.apply.domain;

import com.depromeet.watni.domain.apply.constant.ApplyType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue(value = ApplyType.Values.CODE)
public class CodeApply extends BaseApply {

    @Column(name = "code")
    private String code;
}
