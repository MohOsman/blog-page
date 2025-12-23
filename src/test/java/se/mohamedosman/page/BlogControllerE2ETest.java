package se.mohamedosman.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import se.mohamedosman.page.api.request.CreatePostRequest;
import se.mohamedosman.page.api.request.PostStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest()
@AutoConfigureMockMvc
class BlogControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBlogPostSuccessfully() throws Exception {
        // Arrange
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "Test Blog Title",
                "Test blog content goes here",
                "test-image.jpg",
                PostStatus.DRAFT
        );

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateBlogPostWithPublishedStatus() throws Exception {
        // Arrange
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "Published Blog Post",
                "This is a published blog post",
                "published-image.jpg",
                PostStatus.PUBLISHED
        );

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateBlogPostWithMinimalData() throws Exception {
        // Arrange
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "Minimal Title",
                "Minimal content",
                null,
                null

        );

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateBlogPostWithAllFields() throws Exception {
        // Arrange
        CreatePostRequest createPostRequest = new CreatePostRequest(
                "Complete Blog Post",
                "This is a complete blog post with all fields populated",
                "complete-image.jpg",
                PostStatus.PUBLISHED
        );

        // Act & Assert
        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}



