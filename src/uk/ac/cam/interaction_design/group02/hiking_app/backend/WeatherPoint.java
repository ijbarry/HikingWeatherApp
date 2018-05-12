package uk.ac.cam.interaction_design.group02.hiking_app.backend;

import java.util.List;

/**
 * Immutable class representing weather data about a single point
 */
public class WeatherPoint {
    // 3 hour tolerance by default, based on the API we're using.
    private static final int TIME_FORECAST_TOLERANCE = 60*60*3;

    private double longitude;
    private double latitude;
    private List<WeatherData> forecast;
    private int timeForecastGenerated;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getTimeForecastGenerated() {
        return timeForecastGenerated;
    }

    /**
     * @param timeToGet Time for which we are getting the forecast
     * @return Closest forecast in time (if less than TIME_FORECAST_TOLERANCE), else throws ForecastException
     */
    public WeatherData getForecastAtTime(long timeToGet) throws ForecastException {

        // By initialising bestDelta at TIME_FORECAST_TOLERANCE, ensures we never break tolerance
        WeatherData bestData = null;
        long bestDelta = TIME_FORECAST_TOLERANCE;

        // Find the closest valid forecast for this point
        for(WeatherData data : forecast) {
            long timeOfForecast = data.getTimeOfForecast();
            long timeDelta = Math.abs(timeToGet - timeOfForecast);
            if(timeDelta < bestDelta) {
                bestDelta = timeDelta;
                bestData = data;
            }
        }

        // Ensure we never return a null bestData - this MUST be handled.
        if(bestData == null) {
            throw new ForecastException("Time lies outside forecast range");
        } else {
            return bestData;
        }
    }

    public WeatherPoint(double latitude, double longitude, int timeForecastGenerated, List<WeatherData> weatherData) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeForecastGenerated = timeForecastGenerated;
        this.forecast = weatherData;
    }
}
