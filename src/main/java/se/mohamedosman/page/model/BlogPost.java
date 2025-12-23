package se.mohamedosman.page.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import se.mohamedosman.page.api.request.PostStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_posts")
@Setter
@Getter
public class BlogPost {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT", unique = true)
    private String slug;

    private String featuredImage;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private LocalDateTime publishedAt;

    private String metaDescription;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public BlogPost() {
    }

    public BlogPost(Long id, String title, String content, String slug, String featuredImage, PostStatus status, LocalDateTime publishedAt, String metaDescription, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.slug = slug;
        this.featuredImage = featuredImage;
        this.status = status;
        this.publishedAt = publishedAt;
        this.metaDescription = metaDescription;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
