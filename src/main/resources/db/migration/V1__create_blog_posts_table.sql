CREATE TABLE blog_posts (
                            id BIGSERIAL PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            content TEXT,
                            slug TEXT UNIQUE,
                            featured_image VARCHAR(255),
                            status VARCHAR(50),
                            published_at TIMESTAMP,
                            meta_description VARCHAR(255),
                            created_at TIMESTAMP NOT NULL DEFAULT now(),
                            updated_at TIMESTAMP
);