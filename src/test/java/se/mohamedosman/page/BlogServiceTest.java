package se.mohamedosman.page;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.mohamedosman.page.api.request.CreatePostRequest;
import se.mohamedosman.page.api.request.GetPostRequest;
import se.mohamedosman.page.model.BlogPost;
import se.mohamedosman.page.repository.BlogPostRepository;
import se.mohamedosman.page.service.BlogService;
import se.mohamedosman.page.service.BlogServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTest {

    @Mock
    BlogPostRepository blogPostRepository;

    BlogService blogService;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        blogService = new BlogServiceImpl(blogPostRepository);
    }

    @Test
    public void user_should_create_blog_post() {
        var blogPostReq = new CreatePostRequest(
                "Test Title",
                "Test Content",
                "featured-image.jpg",
                null);

        blogService.createPost(blogPostReq);

        verify(blogPostRepository).save(any());
    }

    @Test
    public void user_should_get_all_blog_posts() {
        // Arrange
        BlogPost blogPost1 = new BlogPost();
        blogPost1.setId(1L);
        blogPost1.setTitle("First Blog Post");
        blogPost1.setContent("This is the first blog post");
        blogPost1.setPublishedAt(LocalDateTime.now());

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setId(2L);
        blogPost2.setTitle("Second Blog Post");
        blogPost2.setContent("This is the second blog post");
        blogPost2.setPublishedAt(LocalDateTime.now());

        List<BlogPost> mockBlogPosts = Arrays.asList(blogPost1, blogPost2);
        when(blogPostRepository.findAll()).thenReturn(mockBlogPosts);

        // Act
        List<GetPostRequest> result = blogService.getAllPosts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("First Blog Post", result.get(0).title());
        assertEquals("Second Blog Post", result.get(1).title());
        assertEquals("This is the first blog post", result.get(0).content());
        assertEquals("This is the second blog post", result.get(1).content());

        verify(blogPostRepository).findAll();
    }

    @Test
    public void user_should_generate_slug_from_title() {
        // Arrange
        var blogPostReq = new CreatePostRequest(
                "My Amazing Blog Post",
                "This is the content",
                "image.jpg",
                null
        );

        // Act
        blogService.createPost(blogPostReq);

        // Assert
        ArgumentCaptor<BlogPost> blogPostCaptor = ArgumentCaptor.forClass(BlogPost.class);
        verify(blogPostRepository).save(blogPostCaptor.capture());

        BlogPost savedPost = blogPostCaptor.getValue();
        assertEquals("my_amazing_blog_post", savedPost.getSlug());
    }

    @Test
    public void user_should_get_post_by_slug() {
        // Arrange
        String slug = "test_blog_post";
        BlogPost blogPost = new BlogPost();
        blogPost.setId(1L);
        blogPost.setTitle("Test Blog Post");
        blogPost.setContent("This is test content");
        blogPost.setSlug(slug);
        blogPost.setPublishedAt(LocalDateTime.now());

        when(blogPostRepository.findBlogPostBySlug(slug)).thenReturn(java.util.Optional.of(blogPost));

        // Act
        var result = blogService.getPostBySlug(slug);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Blog Post", result.get().title());
        assertEquals("This is test content", result.get().content());
        verify(blogPostRepository).findBlogPostBySlug(slug);
    }


    @Test
    public void user_should_handle_post_not_found_by_slug() {
        // Arrange
        String slug = "non_existent_slug";
        when(blogPostRepository.findBlogPostBySlug(slug)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(se.mohamedosman.page.exception.BlogPostNotFoundException.class, () -> {
            blogService.getPostBySlug(slug);
        });
        verify(blogPostRepository).findBlogPostBySlug(slug);
    }
}
