package uk.ac.cam.interaction_design.group02.hiking_app.backend;

public enum ForecastType {
    DAILY(24*60*60),
    HOURLY(60*60),
    MINUTELY(60);

    private final long validTime;

    private ForecastType(final long validTime) {
        this.validTime = validTime;
    }

    public long getValidTime() {
        return validTime;
    }
}
