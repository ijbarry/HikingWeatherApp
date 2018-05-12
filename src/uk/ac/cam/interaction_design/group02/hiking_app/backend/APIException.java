package uk.ac.cam.interaction_design.group02.hiking_app.backend;

/**
 * Exception thrown when the API fails to work (eg. rate limited, timeout...)
 */
public class APIException extends Exception {
    public APIException(String details) {
        super(details);
    }
}
