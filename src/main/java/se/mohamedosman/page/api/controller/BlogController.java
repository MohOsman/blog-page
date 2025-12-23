package se.mohamedosman.page.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.mohamedosman.page.api.request.CreatePostRequest;
import se.mohamedosman.page.api.request.GetPostRequest;
import se.mohamedosman.page.service.BlogService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/posts")
public class BlogController {

    private static final Logger logger = Logger.getLogger(BlogController.class.getName());
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody CreatePostRequest createPostRequest) {
        try {
            logger.info("Creating new blog post with title: " + createPostRequest.title());
            blogService.createPost(createPostRequest);
            logger.info("Blog post created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid blog post data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.severe("Error creating blog post: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetPostRequest>> getAllPosts() {
        List<GetPostRequest> posts = blogService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<GetPostRequest> getPostBySlug(@PathVariable String slug) {
        return blogService.getPostBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}
