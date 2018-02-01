import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        ArrayList<Location> list = new ArrayList<>();
        String database = "misc";
        String server = "turing.cs.missouriwestern.edu";
        String user = "csc346";
        String password = "age126";
        String connectionString = String.format("jdbc:mysql://%s/%s?user=%s&password=%s", server,
                database, user, password);
        System.out.println("The connection string is " + connectionString);
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the zipcode of the disaster epicenter:");
        int initial = keyboard.nextInt();
        System.out.println("Please enter the disaster radius in miles");
        double radius = keyboard.nextInt();
        radius = toKilometers(radius);
        Location epicenter = null;
        try {
            Connection conn = DriverManager.getConnection(connectionString);

            String queryString =
                    String.format("SELECT zipcode, city, state, lat, `long`, estimatedpopulation " +
                            "FROM zips2 WHERE locationtype LIKE \"Primary\" AND zipcode LIKE " + initial);
            System.out.println("Query String is " + queryString);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(queryString);
            epicenter.setCity(rs.getString("city"));
            epicenter.setState(rs.getString("state"));
            epicenter.setZip(rs.getInt("zipcode"));
            epicenter.setLat(rs.getDouble("lat"));
            epicenter.setLon(rs.getDouble("long"));
            epicenter.setPop(rs.getInt("estimatedpopulation"));
            maxDist mxLoc = new maxDist(epicenter.getLat(), epicenter.getLon(), radius);
            queryString = String.format("SELECT zipcode, city, state, lat, `long`, estimatedpopulation " +
                    "FROM zips2 WHERE locationtype LIKE \"Primary\" AND lat <=" + mxLoc.getMaxLat() +
                    " AND lat >=" + mxLoc.getMinLat() + " AND lon <=" + mxLoc.getMaxLon() + " AND lon>=" + mxLoc.getMinLon());
            st = conn.createStatement();
            rs = st.executeQuery(queryString);
            Location inZone = null;
            while (rs.next()) {
                inZone.setCity(rs.getString("city"));
                inZone.setState(rs.getString("state"));
                inZone.setZip(rs.getInt("zipcode"));
                inZone.setLat(rs.getDouble("lat"));
                inZone.setLon(rs.getDouble("long"));
                inZone.setPop(rs.getInt("estimatedpopulation"));
                for (int i = 0; i<list.size(); i++){
                    if (inZone.getCity() == list.get(i).city && inZone.getState() == list.get(i).state){
                        list.get(i).setPop(inZone.getPop() + list.get(i).pop);
                        continue;
                    }
                }
                list.add(inZone);
            }


            conn.close();
        } catch (SQLException e) {
            System.out.println("Error opening the database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        //maxDist mxLoc = new maxDist(epicenter.getLat(), epicenter.getLon(), radius);
    }
    public static double toKilometers (double miles){
        return miles/0.62137119;
    }
    public static double toMiles (double kilos){
        return kilos*0.62137119;
    }
}
