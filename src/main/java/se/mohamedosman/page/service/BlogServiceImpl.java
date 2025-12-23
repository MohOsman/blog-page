package se.mohamedosman.page.service;


import org.springframework.stereotype.Service;
import se.mohamedosman.page.api.request.CreatePostRequest;
import se.mohamedosman.page.api.request.GetPostRequest;
import se.mohamedosman.page.exception.BlogPostNotFoundException;
import se.mohamedosman.page.exception.PostAlreadyExistException;
import se.mohamedosman.page.model.BlogPost;
import se.mohamedosman.page.repository.BlogPostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {


    private final BlogPostRepository blogPostRepository;

    public BlogServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public void createPost(CreatePostRequest createPostRequest) throws PostAlreadyExistException {
        // Get from the dto to real entity

        var slug = generateSlug(createPostRequest.title());
        BlogPost blogPost = new BlogPost();
        blogPost.setTitle(createPostRequest.title());
        blogPost.setContent(createPostRequest.content());
        blogPost.setSlug(slug);
        blogPost.setFeaturedImage(createPostRequest.featuredImage());
        blogPost.setStatus(createPostRequest.status());
        blogPost.setPublishedAt(LocalDateTime.now());
        blogPost.setMetaDescription("short description");
        if(blogPostRepository.existsBlogPostByTitle(createPostRequest.title())) {
            throw new PostAlreadyExistException("Blog post with the same title already exists");
        }
        blogPostRepository.save(blogPost);

    }



    @Override
    public List<GetPostRequest> getAllPosts() {
      final List<BlogPost> blogPosts = blogPostRepository.findAll();
      return blogPosts.stream().map(b-> new GetPostRequest(b.getTitle(),
              b.getContent(),
              b.getPublishedAt())).toList();
    }

    @Override
    public Optional<GetPostRequest> getPostBySlug(String slug) {
        return Optional.of(blogPostRepository.findBlogPostBySlug((slug))
                .map(b -> new GetPostRequest(
                        b.getTitle(),
                        b.getContent(),
                        b.getPublishedAt()
                )).orElseThrow(() -> new BlogPostNotFoundException("Blog post not found")));
    }

    private String generateSlug(String title) {
        return title.toLowerCase().replaceAll(" ", "_");
    }

}
