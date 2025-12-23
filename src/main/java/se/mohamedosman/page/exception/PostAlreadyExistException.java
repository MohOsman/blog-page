package se.mohamedosman.page.exception;

public class PostAlreadyExistException extends RuntimeException {
    public PostAlreadyExistException(String s) {
        super(s);
    }
}
