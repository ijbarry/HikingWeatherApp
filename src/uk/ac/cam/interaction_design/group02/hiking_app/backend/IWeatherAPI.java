package uk.ac.cam.interaction_design.group02.hiking_app.backend;

public interface IWeatherAPI {
    public WeatherPoint getWeatherInTolerance(double longitude, double latitude, double tolerance) throws APIException;

    public WeatherPoint getWeatherForPoint(double longitude, double latitude) throws APIException;
}
