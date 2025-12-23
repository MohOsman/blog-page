package se.mohamedosman.page.exception;

public class BlogPostNotFoundException extends RuntimeException {
    public BlogPostNotFoundException(String message){
        super(message);

    }
}
