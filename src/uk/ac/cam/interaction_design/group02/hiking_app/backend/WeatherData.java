package uk.ac.cam.interaction_design.group02.hiking_app.backend;


/**
 * Immutable class representing a piece of weather data
 */
public class WeatherData {
    private long timeOfForecast;
    private double temperatureKelvin;
    private double pressure;
    private double humidity;

    public WeatherData(double temperatureKelvin, double pressure, double humidity) {
        this.temperatureKelvin = temperatureKelvin;
        this.pressure = pressure;
        this.humidity = humidity;

        this.timeOfForecast = System.currentTimeMillis()/1000;
    }

    public long getTimeOfForecast() {
        return timeOfForecast;
    }

    public double getTemperatureKelvin() {
        return temperatureKelvin;
    }

    public double getTemperatureCelsius() {
        return (temperatureKelvin - 273.15);
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
}
