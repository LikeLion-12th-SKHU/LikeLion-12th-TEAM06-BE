package com.likelion.plantication.diaryLike.domain;

import com.likelion.plantication.diary.domain.Diary;
import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diaryLike_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date createdAt;

    @Builder
    public DiaryLike(Diary diary, User user, Date createdAt) {
        this.diary = diary;
        this.user = user;
        this.createdAt = createdAt;
    }

}
