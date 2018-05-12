package uk.ac.cam.interaction_design.group02.hiking_app.backend;

/**
 * Exception thrown when an invalid forecast is requested
 */
public class ForecastException extends Exception {
    public ForecastException(String details){
        super(details);
    }
}
