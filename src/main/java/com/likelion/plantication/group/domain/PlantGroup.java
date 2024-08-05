package com.likelion.plantication.group.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PLANT_GROUPS")
public class PlantGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long groupId;

    @Column(name = "GROUP_TITLE", length = 50, nullable = false)
    private String title;

    // 공개여부
    @Column(name = "GROUP_OPEN", nullable = false)
    private Boolean openStatus;

    // 인원수 설정
    @Column(name = "GROUP_PEOPLE", nullable = false)
    private Integer people;

    @Column(name = "GROUP_CONTENT", length = 4000, nullable = false)
    private String content;

    @Column(name = "GROUP_IMAGE")
    private String image;

    @Column(name = "GROUP_CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "GROUP_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;


    // hashtag

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public PlantGroup(String title, boolean openStatus, Integer people, String content, String image, LocalDateTime createdAt, User user) {
        this.title = title;
        this.openStatus = openStatus;
        this.people = people;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.user = user;
    }

    // 수정 로직

    public void update(String title, Boolean openStatus, String image, Integer people, String content, LocalDateTime createdAt) {
        this.title = title;
        this.openStatus = openStatus;
        this.people = people;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
    }
}
