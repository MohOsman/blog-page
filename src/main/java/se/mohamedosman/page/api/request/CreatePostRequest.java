package se.mohamedosman.page.api.request;

import java.time.LocalDateTime;

public record CreatePostRequest(
    String title,
    String content,
    String featuredImage,
    PostStatus status
) {}
