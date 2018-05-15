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

    private List<ForecastWeatherPoint> forecastCache;
    private List<TypicalWeatherPoint> typicalWeatherPoints;
    private static NaiveAPI instance;

    /**
     * Use the OWM API to fetch the weather data for a point
     * TODO: Implement API usage
     * @return Weather for a point
     */
 private AsyncHttpClient client = new AsyncHttpClient();
 public WeatherPoint fetchWeatherUsingAPI(double latitude, double longitude) throws APIException {
        List<WeatherData> data = new ArrayList<WeatherData>();

        client.get( "https://api.forecast.io/forecast/" + API_KEY + "/" + latitude + "," + longitude + "?units=si", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                    JSONObject root = new JSONObject(response);
                    JSONObject daily = root.getJSONObject("daily");
                    JSONObject hourly = root.getJSONObject("hourly");
                    JSONObject minutely = root.getJSONObject("minutely");
                    JSONObject currently = root.getJSONObject("currently");

                    JSONArray nHour = minutely.getJSONArray("data");
                    JSONArray nDay = hourly.getJSONArray("data");
                    JSONArray nDay = hourly.getJSONArray("data");

                    /*(long timeOfForecast, double temperatureCelsius, double pressure, double humidity,
                       double precipitationIntensity, double precipitationProbability,
                       ForecastType forecastType, PrecipitationType precipitationType)*/


                    //loop through next hour for each minute
                    for (int i = 0; i < nHour.length(); i++) {
                        JSONObject Min = nHour.getJSONObject(i);
                        PrecipitationType type = new PrecipitationType();

                        switch (Min.getString("precipType")){
                            case "snow":type = PrecipitationType.SNOW;
                                        break;
                            case "rain":type = PrecipitationType.RAIN;
                                        break;
                            case "sleet":type = PrecipitationType.SLEET;
                                        break;
                            default:type = PrecipitationType.NONE;
                                        break;
                        }
                            
                        
                        WeatherData toSave = new WeatherData(
                            Min.getLong("time"),
                            Min.getDouble("apparentTemperature"),
                            Min.getDouble("pressure"),
                            Min.getDouble("humidity"),

                            Min.getDouble("precipIntensity"),
                            Min.getDouble("precipProbability"),
                            ForecastType.MINUTELY,
                            type
                        );
                        data.add(toSave);
                    }

                    //loop through next day for each hour
                    for (int i = 0; i < nDay.length(); i++) {
                        JSONObject Hour = nDay.getJSONObject(i);
                        PrecipitationType type = new PrecipitationType();

                        switch (Hour.getString("precipType")){
                            case "snow":type = PrecipitationType.SNOW;
                                        break;
                            case "rain":type = PrecipitationType.RAIN;
                                        break;
                            case "sleet":type = PrecipitationType.SLEET;
                                        break;
                            default:type = PrecipitationType.NONE;
                                        break;
                        }
                            
                        
                        WeatherData toSave = new WeatherData(
                            Hour.getLong("time"),
                            Hour.getDouble("apparentTemperature"),
                            Hour.getDouble("pressure"),
                            Hour.getDouble("humidity"),

                            Hour.getDouble("precipIntensity"),
                            Hour.getDouble("precipProbability"),
                            ForecastType.HOURLY,
                            type
                        );
                        data.add(toSave);
                    }

                    //loop through next week for each day
                     for (int i = 0; i < nWeek.length(); i++) {
                        JSONObject Day = nWeek.getJSONObject(i);
                        PrecipitationType type = new PrecipitationType();

                        switch (Day.getString("precipType")){
                            case "snow":type = PrecipitationType.SNOW;
                                        break;
                            case "rain":type = PrecipitationType.RAIN;
                                        break;
                            case "sleet":type = PrecipitationType.SLEET;
                                        break;
                            default:type = PrecipitationType.NONE;
                                        break;
                        }
                            
                        
                        WeatherData toSave = new WeatherData(
                            Day.getLong("time"),
                            Day.getDouble("temperature"),
                            Day.getDouble("pressure"),
                            Day.getDouble("humidity"),

                            Day.getDouble("precipIntensity"),
                            Day.getDouble("precipProbability"),
                            ForecastType.DAILY,
                            type
                        );
                        data.add(toSave);
                    }

                }
            }
        });
        
        return new WeatherPoint(latitude, longitude, System.currentTimeMillis()/1000, data);
    }

    private WeatherData fetchTypicalWeatherForPoint(TypicalWeatherPoint point, long time) throws APIException {
        double latitude = point.getLatitude();
        double longitude = point.getLongitude();

        //TODO: make API call, return correct WeatherData for that day
        WeatherData typicalWeather = new WeatherData(time, 0, 0, 0,
                1, 0.5, ForecastType.DAILY, PrecipitationType.SLEET);

        point.addDataPoint(typicalWeather);

        return typicalWeather;
    }

    private boolean isStale(ForecastWeatherPoint point, long currentTime) {
        long timeForecastGenerated = point.getTimeForecastGenerated();
        long timeDelta = timeForecastGenerated - currentTime;

        return (timeDelta < DATA_VALIDITY);
    }
    /**
     * Discards timed-out (stale) cache entries
     */
    private void cleanCache() {
        long currentTime = System.currentTimeMillis()/1000;

        forecastCache.removeIf(x -> isStale(x, currentTime));
    }

    /**
     * @param latitude Latitude of point being fetched in degrees
     * @param longitude Longitude of point being fetched in degrees
     * @param tolerance Acceptable distance difference in metres
     * @return The closest point to the given position within tolerance
     * @throws APIException Exception when API call to fetch point fails
     */
    @Override
    public ForecastWeatherPoint getWeatherInTolerance(double latitude, double longitude, double tolerance) throws APIException {
        // Running this every time isn't ideal, but prevents case where there are multiple suitable points, and closest is stale
        cleanCache(); //If this call is removed, check stale and remove in loop.

        ForecastWeatherPoint bestPoint = null;
        double bestDistance = tolerance;

        for(ForecastWeatherPoint point : forecastCache) {
            double distance = Haversine.getDistance(point.getLatitude(), point.getLongitude(), latitude, longitude);
            if(distance < bestDistance) {
                bestPoint = point;
                bestDistance = distance;
            }
        }

        if(bestPoint == null) {
            ForecastWeatherPoint point = fetchWeatherUsingAPI(latitude, longitude);
            forecastCache.add(point);
            return point;
        } else {
            return bestPoint;
        }
    }


    @Override
    public ForecastWeatherPoint getWeatherForPoint(double latitude, double longitude) throws APIException {
        return getWeatherInTolerance(latitude, longitude, LOCATION_TOLERANCE_METRES);
    }

    @Override
    public WeatherData getTypicalWeatherForPoint(double latitude, double longitude, long time) throws APIException {
        TypicalWeatherPoint bestPoint = null;
        double bestDistance = LOCATION_TOLERANCE_METRES;

        for(TypicalWeatherPoint point : typicalWeatherPoints) {
            double distance = Haversine.getDistance(point.getLatitude(), point.getLongitude(), latitude, longitude);
            if(distance < bestDistance) {
                bestPoint = point;
                bestDistance = distance;
            }
        }

        if(bestPoint == null) {
            bestPoint = new TypicalWeatherPoint(latitude, longitude, new ArrayList<>());
            typicalWeatherPoints.add(bestPoint);
        }

        // Now we have a bestPoint, see if it has a point at the right time
        try {
            return bestPoint.getDataAboutTime(time);
        } catch(ForecastException e) {
            return fetchTypicalWeatherForPoint(bestPoint, time);
        }
    }

    private NaiveAPI() {
        forecastCache = new ArrayList<>();
    }

    public static NaiveAPI getInstance() {
        if(instance == null) {
            instance = new NaiveAPI();
        }
        return instance;
    }
}
