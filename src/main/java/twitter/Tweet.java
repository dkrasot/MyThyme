package twitter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Date;

public class Tweet {

    //TODO latitude, longitude OFF; time to postedTime; add User field

    private final Long id;
    private final String message;
    private final Date time;
    private Double latitude;
    private Double longitude;

    public Tweet(String message, Date time) {
        this(null, message, time, null, null);
    }

    public Tweet(Long id, String message, Date time, Double latitude, Double longitude) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public Date getTime() {
        return time;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, new String[]{"id", "time"});
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[]{"id", "time"});
    }
}
