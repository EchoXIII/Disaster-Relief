/**
 * Created by etern on 1/31/2018.
 */
public class maxDist {
    double maxLat;

    public double getMaxLat() {
        return maxLat;
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMaxLon() {
        return maxLon;
    }

    public double getMinLon() {
        return minLon;
    }

    double minLat;
    double maxLon;
    double minLon;
    public static final double R = 6372.8; // In kilometers
    public maxDist (double lat, double lon, double dist){
        lat = Math.toRadians(lat);
        lon = Math.toRadians(lon);
        double bearing = Math.toRadians(0);
        double angleDist = dist/R;
        double latNorth = Math.asin(Math.sin(lat))*Math.cos(angleDist)+Math.cos(lat)*Math.sin(angleDist)*Math.cos(bearing);
        bearing = Math.toRadians(90);
        double latEast = Math.asin(Math.sin(lat))*Math.cos(angleDist)+Math.cos(lat)*Math.sin(angleDist)*Math.cos(bearing);
        double lonEast =  lon+Math.atan2(Math.sin(bearing)*Math.sin(angleDist)*Math.cos(lat),
                Math.cos(angleDist)-Math.sin(lat)*Math.sin(latEast));
        bearing = Math.toRadians(180);
        double latSouth = Math.asin(Math.sin(lat))*Math.cos(angleDist)+Math.cos(lat)*Math.sin(angleDist)*Math.cos(bearing);
        bearing = Math.toRadians(270);
        double latWest = Math.asin(Math.sin(lat))*Math.cos(angleDist)+Math.cos(lat)*Math.sin(angleDist)*Math.cos(bearing);
        double lonWest =  lon+Math.atan2(Math.sin(bearing)*Math.sin(angleDist)*Math.cos(lat),
                Math.cos(angleDist)-Math.sin(lat)*Math.sin(latWest));
        this.maxLat = latNorth;
        this.minLat = latSouth;
        this.maxLon = lonWest;
        this.minLon = lonEast;
    }
}
