package uk.ac.cam.interaction_design.group02.hiking_app.backend;

/**
 * Helper class implementing the Haversine distance between two points on Earth
 */
public class Haversine {
    /**
     * Approximate radius of the earth in metres
     */
    private static final double EARTH_RADIUS = 6371000;

    /**
     * Calculate the Haversine distance between two points on Earth
     * @param latitude1 Latitude of point 1 in degrees
     * @param longitude1 Longitude of point 1 in degrees
     * @param latitude2 Latitude of point 2 in degrees
     * @param longitude2 Longitude of point 2 in degrees
     * @return Haversine distance between the points in metres
     */
    public static double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        // Implements the formula on Wikipedia https://en.wikipedia.org/wiki/Haversine_formula
        double lat1rad = Math.toRadians(latitude1);
        double lat2rad = Math.toRadians(latitude2);
        double long1rad = Math.toRadians(longitude1);
        double long2rad = Math.toRadians(longitude2);

        double term1 = Math.pow(Math.sin((lat2rad - lat1rad)/2), 2);
        double term2 = Math.cos(lat1rad) * Math.cos(lat2rad) * Math.pow(Math.sin((long2rad - long1rad)/2),2);

        return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(term1 + term2));
    }

}
