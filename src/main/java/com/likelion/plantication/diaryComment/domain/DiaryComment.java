package com.likelion.plantication.diaryComment.domain;

import com.likelion.plantication.diary.api.dto.request.DiaryUpdateReqDto;
import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.diaryComment.api.dto.request.DiaryCommentUpdateReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diaryComment_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    private String content;
    private DateTime createdAt;
    private DateTime modifiedAt;

    @Builder
    public DiaryComment(User user, Diary diary, String content,
                        DateTime createdAt, DateTime modifiedAt) {
        this.user = user;
        this.diary = diary;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void update(DiaryCommentUpdateReqDto diaryCommentUpdateReqDto) {
        this.content = diaryCommentUpdateReqDto.content();
    }

    public void updateModifiedAt(DateTime modifiedAt) { this.modifiedAt = modifiedAt; }
}
