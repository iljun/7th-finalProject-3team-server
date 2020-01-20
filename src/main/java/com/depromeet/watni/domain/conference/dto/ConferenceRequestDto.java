package com.depromeet.watni.domain.conference.dto;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.group.domain.Group;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
public class ConferenceRequestDto {
    public interface create{}
    public interface update{}

    @NotNull(groups = create.class)
    private String name;

    @NotNull(groups = create.class)
    private String description;

    @NotNull(groups = create.class)
    private String locationInfo;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

    private Group group;

    public Conference toEntity(Group group) {
        return Conference
                .builder()
                .name(this.name)
                .description(this.description)
                .locationInfo(this.locationInfo)
                .startAt(this.startAt)
                .endAt(this.endAt)
                .group(group)
                .build();
    }

    public Conference toEntity(Conference conference) {
        return Conference
                .builder()
                .conferenceId(conference.getConferenceId())
                .name(this.name)
                .description(this.description)
                .locationInfo(this.locationInfo)
                .startAt(this.startAt)
                .endAt(this.endAt)
                .group(conference.getGroup())
                .build();
    }
}
