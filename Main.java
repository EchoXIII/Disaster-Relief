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
                    String.format("SELECT zipcode, city, state, lat, `long`, estimatedpopulation, taxreturnsfiled " +
                            "FROM zips2 WHERE locationtype LIKE \"Primary\" AND zipcode LIKE " + initial);
            System.out.println("Query String is " + queryString);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(queryString);
            while(rs.next()) {
                String city = rs.getString("city");
                String state = rs.getString("state");
                int zipcode = rs.getInt("zipcode");
                double lat = rs.getDouble("lat");
                double lon = rs.getDouble("long");
                int pop = rs.getInt("estimatedpopulation");
                int houses = rs.getInt("taxreturnsfiled");
                epicenter = new Location(city,state,zipcode,lat,lon,pop,houses);
            }
            System.out.println(epicenter.toString()+" "+epicenter.getLat()+" "+epicenter.getLon());
            maxDist mxLoc = new maxDist(epicenter.getLat(), epicenter.getLon(), radius);
            System.out.println(mxLoc);
            rs.close();
            String queryString2 = String.format("SELECT zipcode, city, state, lat, `long`, estimatedpopulation, taxreturnsfiled "
                    + "FROM zips2 WHERE locationtype LIKE \"Primary\" AND lat <=" + mxLoc.getMaxLat() +
                    " AND lat >=" + mxLoc.getMinLat() + " AND `long` >=" + mxLoc.getMaxLon() + " AND `long` <=" + mxLoc.getMinLon());
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery(queryString2);
            Location inZone;
            while (rs2.next()) {
                String city = rs2.getString("city");
                String state = rs2.getString("state");
                int zipcode = rs2.getInt("zipcode");
                double lat = rs2.getDouble("lat");
                double lon = rs2.getDouble("long");
                int pop = rs2.getInt("estimatedpopulation");
                int houses = rs2.getInt("taxreturnsfiled");
                inZone = new Location (city,state,zipcode,lat,lon,pop,houses);
                boolean unique = true;
                for (int i = 0; i<list.size(); i++){
                    if (inZone.city.equals(list.get(i).city)&& inZone.state.equals(list.get(i).state)){
                        int x = inZone.pop + list.get(i).pop;
                        list.get(i).setPop(x);
                        unique = false;
                    }
                }
                if (unique) {
                    list.add(inZone);
                }
            }
            st.close();
            st2.close();
            rs2.close();
            System.out.println(list.size());
            for (int i = 0; i < list.size(); i++){
                System.out.println(list.get(i).toString());

            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error opening the database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static double toKilometers (double miles){
        return miles/0.62137119;
    }
    public static double toMiles (double kilos){
        return kilos*0.62137119;
    }
}
