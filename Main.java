import java.sql.*;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
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
        System.out.println("Please enter the disaster radius");
        int radius = keyboard.nextInt();
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
            /*while (rs.next()) {
                String stationName = rs.getString("station");
                String place = rs.getString("place");
                String stateAbb = rs.getString("state");
                String country = rs.getString("country");
                int elevation = rs.getInt("elevation");
                list.add(new WeatherStation(stationName, place, stateAbb, country, elevation));
            }*/


            conn.close();
        } catch (SQLException e) {
            System.out.println("Error opening the database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
