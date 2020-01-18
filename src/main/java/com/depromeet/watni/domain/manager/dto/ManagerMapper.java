package com.depromeet.watni.domain.manager.dto;

import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.domain.manager.domain.Manager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ManagerMapper {

    @Mapping(target = "managerId", source = "manager.managerId")
    @Mapping(target = "email", source = "manager.member.email")
    @Mapping(target = "username", source = "manager.member.name")
    @Mapping(target = "group", source = "group")
    MangerResponseDto convert(Manager manager, Group group);

    @Mapping(target = "managerId", source = "manager.managerId")
    @Mapping(target = "email", source = "manager.member.email")
    @Mapping(target = "username", source = "manager.member.name")
    MangerResponseDto convert(Manager manager);

}
