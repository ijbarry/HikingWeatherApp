package uk.ac.cam.interaction_design.group02.hiking_app.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Naive implementation of the weather API using O(n) cache searching (would scale better with k-d tree)
 * Implemented using Singleton pattern so it can be accessed from any module
 * TODO: Implement API calls
 */
public class NaiveAPI implements IAPICache {
    /**
     * long stating the Unix time for which a data point should be valid (1 hour)
     */
    private static final long DATA_VALIDITY = 60*60;

    /**
     * Tolerance for equality of locations in metres
     */
    private static final double LOCATION_TOLERANCE_METRES = 5;

    private List<WeatherPoint> cache;
    private static NaiveAPI instance;

    /**
     * Use the OWM API to fetch the weather data for a point
     * TODO: Implement API usage
     * @return Weather for a point
     */
    private WeatherPoint fetchWeatherUsingAPI(double latitude, double longitude) throws APIException {
        List<WeatherData> data = List.of(new WeatherData(273.15, 1000, 100));
        return new WeatherPoint(latitude, longitude, System.currentTimeMillis()/1000, data);
    }

    private boolean isStale(WeatherPoint point, long currentTime) {
        long timeForecastGenerated = point.getTimeForecastGenerated();
        long timeDelta = timeForecastGenerated - currentTime;

        return (timeDelta < DATA_VALIDITY);
    }
    /**
     * Discards timed-out (stale) cache entries
     */
    private void cleanCache() {
        long currentTime = System.currentTimeMillis()/1000;

        cache.removeIf(x -> isStale(x, currentTime));
    }

    /**
     * @param latitude Latitude of point being fetched in degrees
     * @param longitude Longitude of point being fetched in degrees
     * @param tolerance Acceptable distance difference in metres
     * @return The closest point to the given position within tolerance
     * @throws APIException Exception when API call to fetch point fails
     */
    @Override
    public WeatherPoint getWeatherInTolerance(double latitude, double longitude, double tolerance) throws APIException {
        // Running this every time isn't ideal, but prevents case where there are multiple suitable points, and closest is stale
        cleanCache(); //If this call is removed, check stale and remove in loop.

        WeatherPoint bestPoint = null;
        double bestDistance = tolerance;

        for(WeatherPoint point : cache) {
            double distance = Haversine.getDistance(point.getLatitude(), point.getLongitude(), latitude, longitude);
            if(distance < bestDistance) {
                bestPoint = point;
                bestDistance = distance;
            }
        }

        if(bestPoint == null) {
            WeatherPoint point = fetchWeatherUsingAPI(latitude, longitude);
            cache.add(point);
            return point;
        } else {
            return bestPoint;
        }
    }


    @Override
    public WeatherPoint getWeatherForPoint(double latitude, double longitude) throws APIException {
        return getWeatherInTolerance(latitude, longitude, LOCATION_TOLERANCE_METRES);
    }

    private NaiveAPI() {
        cache = new ArrayList<>();
    }

    public static NaiveAPI getInstance() {
        if(instance == null) {
            instance = new NaiveAPI();
        }
        return instance;
    }
}
