package se.mohamedosman.page.repository;

import jakarta.persistence.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import se.mohamedosman.page.model.BlogPost;

import java.util.List;
import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    boolean existsBlogPostByTitle(String title);
    Optional<BlogPost> findBlogPostBySlug(String slug);
}
