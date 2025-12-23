package se.mohamedosman.page.service;

import org.springframework.stereotype.Service;
import se.mohamedosman.page.api.request.CreatePostRequest;
import se.mohamedosman.page.api.request.GetPostRequest;
import se.mohamedosman.page.model.BlogPost;

import java.util.List;
import java.util.Optional;

@Service
public interface BlogService {
    void createPost(final CreatePostRequest createPostRequest);

    List<GetPostRequest> getAllPosts();

    Optional<GetPostRequest> getPostBySlug(final String slug);
}
