package uk.ac.cam.interaction_design.group02.hiking_app.backend;

public interface IAPICache {
    public ForecastWeatherPoint getWeatherInTolerance(double longitude, double latitude, double tolerance) throws APIException;

    public ForecastWeatherPoint getWeatherForPoint(double longitude, double latitude) throws APIException;

    public WeatherData getTypicalWeatherForPoint(double longitude, double latitude, long time) throws APIException;
}
