package com.likelion.plantication.myPage.domain;

import com.likelion.plantication.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String subject;
    private String content;
    private DateTime createdAt;

    @Builder
    public Inquiry(User user, String subject, String content, DateTime createdAt) {
        this.user = user;
        this.subject = subject;
        this.content = content;
        this.createdAt = createdAt;
    }
}
