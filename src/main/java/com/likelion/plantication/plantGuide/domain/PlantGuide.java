package com.likelion.plantication.plantGuide.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlantGuide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantGuide_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String sentence;
    private String content;
    private String image;
    private DateTime createdAt;
    private int view;

    @Builder
    public PlantGuide(User user, String title, String sentence, String content,
                      String image, DateTime createdAt, int view) {
        this.user = user;
        this.title = title;
        this.sentence = sentence;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.view = view;
    }
}
