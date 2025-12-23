package se.mohamedosman.page.api.request;

import java.time.LocalDateTime;

public record GetPostRequest(String title, String content, LocalDateTime publishedAt) { }
