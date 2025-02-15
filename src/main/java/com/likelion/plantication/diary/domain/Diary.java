package com.likelion.plantication.diary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.plantication.diary.api.dto.request.DiaryUpdateReqDto;
import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id", nullable = false)
    private Long id;

    private String title;
    private String content;
    private String image;
    private Date createdAt;
    private Date modifiedAt;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Builder
    public Diary(String title, String content, String image, Date createdAt, Date modifiedAt, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.user = user;
    }

    public void update(DiaryUpdateReqDto diaryUpdateReqDto) {
        this.title = diaryUpdateReqDto.title();
        this.content = diaryUpdateReqDto.content();
    }

    public void updateImage(String image) {
        this.image = image;
    }

    public void updateModifiedAt(Date modifiedAt) { this.modifiedAt = modifiedAt; }
}
