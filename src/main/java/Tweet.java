import com.datastax.driver.core.LocalDate;
import twitter4j.GeoLocation;
import twitter4j.User;
import java.util.Date;
import java.util.UUID;

public class Tweet {
    private LocalDate createdDate;
    private UUID id;
    private String text;
    private String source;
    private boolean isTruncated;
    private double latitude;
    private double longitude;
    private boolean isFavorited;
    private String user;
    private long contributors;

    public Tweet(LocalDate createdDate, String text,String source,boolean isTruncated,double latitude, double longitude,boolean isFavorited,String user,long contributors) {
        this.createdDate = createdDate;
        this.id = UUID.randomUUID();
        this.text = text;
        this.source = source;
        this.isTruncated = isTruncated;
        this.latitude = latitude;
        this.latitude = latitude;
        this.isFavorited = isFavorited;
        this.user = user;
        this.contributors = contributors;

    }
    public LocalDate getDate() {
        return createdDate;
    }

    public void setDate(LocalDate date) {
        this.createdDate = createdDate;
    }

    public UUID getUUID() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public boolean getIsTruncated() {
        return isTruncated;
    }

    public void setIsTruncated(boolean isTruncated) {
        this.isTruncated = isTruncated;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double geolocation) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public long getContributors() {
        return contributors;
    }

    public void setContributors(long contributors) {
        this.contributors = contributors;
    }
}
