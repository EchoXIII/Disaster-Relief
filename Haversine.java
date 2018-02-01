public class Haversine {
    public static final double R = 6372.8; // In kilometers
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
    public static maxDist haversineCoord (double lat, double lon, double dist){
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
        maxDist result = new maxDist(latNorth, latSouth, lonWest, lonEast);
        return result;
    }
    /*public static void main(String[] args) {
        System.out.println(haversine(36.12, -86.67, 33.94, -118.40));
    }*/
}
//credit for this class to rosettacode.org