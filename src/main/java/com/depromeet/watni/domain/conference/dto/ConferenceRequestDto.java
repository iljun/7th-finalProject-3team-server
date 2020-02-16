package com.depromeet.watni.domain.conference.dto;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ConferenceRequestDto {
    public interface create{}
    public interface update{}

    @NotNull(groups = update.class)
    private Long conferenceId;

    @NotNull(groups = create.class)
    private String name;

    @NotNull(groups = create.class)
    private String description;

    @NotNull(groups = create.class)
    private String locationInfo;

    @NotNull(groups = create.class)
    private LocalDateTime startAt;

    @NotNull(groups = create.class)
    private LocalDateTime endAt;

    private String base64Image;

    private String notice;

    private Group group;

    private Conference.ConferenceBuilder toEntity() {
        return Conference
                .builder()
                .name(this.name)
                .description(this.description)
                .locationInfo(this.locationInfo)
                .startAt(this.startAt)
                .endAt(this.endAt)
                .notice(this.notice == null ? null : this.notice);
    }

    public Conference toEntity(Group group) {
        return this.toEntity()
                .group(group)
                .build();
    }

    public Conference toEntity(Conference conference) {
        return Conference
                .builder()
                .conferenceId(conference.getConferenceId())
                .group(conference.getGroup())
                .name(this.name == null ? conference.getName() : this.name)
                .description(this.description == null ? conference.getDescription() : this.description)
                .locationInfo(this.locationInfo == null ? conference.getLocationInfo() : this.locationInfo)
                .startAt(this.startAt == null ? conference.getStartAt() : this.startAt)
                .endAt(this.endAt == null ? conference.getEndAt() : this.endAt)
                .photoUrl(this.base64Image == null ? conference.getPhotoUrl() : this.base64Image)
                .notice(this.notice == null ? conference.getNotice() : this.notice)
                .build();
    }
}
