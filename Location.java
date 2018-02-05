/**
 * Created by etern on 1/31/2018.
 */
public class Location {
    String city;
    String state;
    int zip;
    double lat;
    double lon;
    int pop;
    int houses;
    public Location(String city, String state, int zip, double lat, double lon, int pop, int houses) {
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.lat = lat;
        this.lon = lon;
        this.pop = pop;
        this.houses = houses;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public int getHouses() {
        return houses;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    @Override
    public String toString() {
        String s = String.format(city + ", " + state + " Population: " + pop + " Housing units: " + houses);
        return s;
    }
}
