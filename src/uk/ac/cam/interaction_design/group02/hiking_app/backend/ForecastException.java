package uk.ac.cam.interaction_design.group02.hiking_app.backend;

/**
 * Exception thrown when an invalid forecast is requested
 * Should always be dealt with - thrown esp. if a hike is too far into the future
 */
public class ForecastException extends Exception {
    public ForecastException(String details){
        super(details);
    }
}
