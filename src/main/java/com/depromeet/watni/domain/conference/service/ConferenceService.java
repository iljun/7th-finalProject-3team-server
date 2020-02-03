package com.depromeet.watni.domain.conference.service;

import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.conference.dto.ConferenceRequestDto;
import com.depromeet.watni.domain.conference.repository.ConferenceRepository;
import com.depromeet.watni.domain.group.domain.Group;
import com.depromeet.watni.utils.AwsS3Service;
import com.depromeet.watni.utils.Base64Decoding;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final AwsS3Service awsS3Service;
    public ConferenceService(ConferenceRepository conferenceRepository,
                             AwsS3Service awsS3Service) {
        this.conferenceRepository = conferenceRepository;
        this.awsS3Service = awsS3Service;
    }

    public Conference generateConference(ConferenceRequestDto conferenceRequestDto, Group group) throws IOException {
        Conference conference = conferenceRepository.save(conferenceRequestDto.toEntity(group));
        if (conferenceRequestDto.getBase64Image() != null) {
            conferenceRequestDto.setBase64Image(this.uploadConferencePhoto(conferenceRequestDto,  conference));
            conference = conferenceRepository.save(conferenceRequestDto.toEntity(conference));
        }
        return conference;
    }

    public Conference updateConference(ConferenceRequestDto conferenceRequestDto, Conference conference) throws IOException {
        System.out.println(conferenceRequestDto.toString());
        if (conferenceRequestDto.getBase64Image() != null) {
            conferenceRequestDto.setBase64Image(this.uploadConferencePhoto(conferenceRequestDto,  conference));
            conference = conferenceRepository.save(conferenceRequestDto.toEntity(conference));
        }
        return conference;
    }

    public void deleteConference(Conference conference) {
        conference.delete();
        conferenceRepository.save(conference);
    }

    // TODO Exception Handling
    private String uploadConferencePhoto(ConferenceRequestDto conferenceRequestDto, Conference conference) throws IOException {
        if (StringUtils.isNotEmpty(conferenceRequestDto.getBase64Image())) {
            // TODO refactoring
            String randomString = String.valueOf(Math.random());
            File file = Base64Decoding.decodingToImage(conferenceRequestDto.getBase64Image(), randomString);
            String photoUrl = awsS3Service.upload("/conference/" + conference.getConferenceId(), ContentType.IMAGE_JPEG, file);
            conferenceRequestDto.setBase64Image(photoUrl);
            file.deleteOnExit();
            return photoUrl;
        }
        return null;
    }
}
