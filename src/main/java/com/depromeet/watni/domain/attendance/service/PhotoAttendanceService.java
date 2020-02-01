package com.depromeet.watni.domain.attendance.service;

import com.depromeet.watni.domain.attendance.constant.AttendanceStatus;
import com.depromeet.watni.domain.attendance.domain.BaseAttendance;
import com.depromeet.watni.domain.attendance.domain.PhotoAttendance;
import com.depromeet.watni.domain.attendance.dto.PhotoAttendanceRequestDto;
import com.depromeet.watni.domain.attendance.repository.AttendanceRepository;
import com.depromeet.watni.domain.conference.domain.Conference;
import com.depromeet.watni.domain.member.domain.Member;
import com.depromeet.watni.exception.BadRequestException;
import com.depromeet.watni.utils.AwsS3Service;
import com.depromeet.watni.utils.Base64Decoding;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class PhotoAttendanceService implements AttendanceService<PhotoAttendance, PhotoAttendanceRequestDto> {
    private final AttendanceRepository attendanceRepository;
    private final AwsS3Service awsS3Service;
    public PhotoAttendanceService(AttendanceRepository attendanceRepository,
                                  AwsS3Service awsS3Service) {
        this.attendanceRepository = attendanceRepository;
        this.awsS3Service = awsS3Service;
    }

    @Override
    public PhotoAttendance createAttendance(PhotoAttendanceRequestDto requestDto, Conference conference, Member member) {
        Optional<BaseAttendance> baseAttendance = attendanceRepository.findByConferenceAndMember(conference, member);
        if (baseAttendance.isPresent()) {
            throw new BadRequestException("Already exists attendance");
        }

        File file = null;
        try {
            file = Base64Decoding.decodingToImage(requestDto.getBase64Image(), conference.getConferenceId() + "/" + member.getMemberId());
        } catch (IOException e) {
            log.error("PhotoAttendance.createAttendance fileUpload fail : {}", requestDto.toString());
        }

        String imageUrl = awsS3Service.upload(conference.getConferenceId() + "/" + member.getName(), ContentType.IMAGE_JPEG, file);
        PhotoAttendance photoAttendance = PhotoAttendance
                .builder()
                .photoUrl(imageUrl)
                .conference(conference)
                .attendanceAt(LocalDateTime.now())
                .member(member)
                .attendanceStatus(AttendanceStatus.ACCEPT)
                .build();
        photoAttendance = (PhotoAttendance) attendanceRepository.save(photoAttendance);
        file.deleteOnExit();
        return photoAttendance;
    }

    @Override
    public AttendanceRepository getAttendanceRepository() {
        return this.attendanceRepository;
    }
}
