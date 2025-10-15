package dev.cattyn.catformat.stylist.exceptions;

public class StylistException extends RuntimeException {
    public StylistException(String message, Throwable throwable) {
      super(message, throwable);
    }

    public StylistException(String message) {
        super(message);
    }
}
