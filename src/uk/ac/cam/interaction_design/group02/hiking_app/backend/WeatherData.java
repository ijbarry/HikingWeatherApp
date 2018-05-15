package uk.ac.cam.interaction_design.group02.hiking_app.backend;


/**
 * Immutable class representing a piece of weather data
 */
public class WeatherData {
    private long timeOfForecast;

    private double temperatureCelsius;
    private double pressure;
    private double humidity;

    private double precipitationIntensity;
    private double precipitationProbability;

    private ForecastType forecastType;
    private PrecipitationType precipitationType;

    public WeatherData(long timeOfForecast, double temperatureCelsius, double pressure, double humidity,
                       double precipitationIntensity, double precipitationProbability,
                       ForecastType forecastType, PrecipitationType precipitationType) {
        this.temperatureCelsius = temperatureCelsius;
        this.pressure = pressure;
        this.humidity = humidity;

        this.precipitationIntensity = precipitationIntensity;
        this.precipitationProbability = precipitationProbability;
        this.forecastType = forecastType;
        this.precipitationType = precipitationType;
        this.timeOfForecast = timeOfForecast;
    }

    public long getTimeOfForecast() {
        return timeOfForecast;
    }

    public double getTemperatureKelvin() {
        return temperatureCelsius-273.15;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public double getTemperatureFahrenheit() {
        return 9*getTemperatureCelsius()/5 + 32;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public double getPrecipitationIntensity() {
        return precipitationIntensity;
    }

    public ForecastType getForecastType() {
        return forecastType;
    }

    public PrecipitationType getPrecipitationType() {
        return precipitationType;
    }
}
