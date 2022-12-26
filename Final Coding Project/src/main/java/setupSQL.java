import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class setupSQL
{
    public static void main() {
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("create table if not exists restaurant (resName VARCHAR(255) UNIQUE PRIMARY KEY,totalSeats integer, availableSeats integer, address VARCHAR(256))");
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    public static HashMap<String, ArrayList<Object>> readTable() throws SQLException{
        Connection connection = null;
        HashMap<String, ArrayList<Object>> resData = new HashMap<String,ArrayList<Object>>();
        try
        {
            /* create a database connection */
            connection = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from restaurant");
            while(rs.next())
            {
                /* read the result set */
                ArrayList<Object> tmp = new ArrayList<>();
                tmp.add(rs.getInt("totalSeats"));
                tmp.add(rs.getInt("availableSeats"));
                tmp.add(rs.getString("address"));

                resData.put(rs.getString("resName"),tmp);
                System.out.println("Restaurant Name = " + rs.getString("resName"));
                System.out.println("Total Seats = " + rs.getInt("totalSeats"));
                System.out.println("Available Seats = " + rs.getInt("availableSeats"));
                System.out.println("Address = " + rs.getString("address"));

            }
        }
        catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return resData;
    }
    private Connection connect() {
        /* SQLite connection string */
        String url = "jdbc:sqlite:restaurant.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String name, Integer capacity,String address) throws SQLException {
        String sql = "INSERT INTO restaurant(resName,totalSeats,availableSeats,address) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, capacity);
            pstmt.setDouble(3, capacity);
            pstmt.setString(4, address);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, ArrayList<Object>> getRes(String resName) throws SQLException {
        Connection connection = null;
        HashMap<String, ArrayList<Object>> resData = new HashMap<String,ArrayList<Object>>();
        try
        {
            /* create a database connection */
            connection = DriverManager.getConnection("jdbc:sqlite:restaurant.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            ResultSet rs = statement.executeQuery("select * from restaurant where resName='"+resName+"'");
            while(rs.next())
            {
                /* read the result set */
                ArrayList<Object> tmp = new ArrayList<>();
                tmp.add(rs.getInt("totalSeats"));
                tmp.add(rs.getInt("availableSeats"));
                tmp.add(rs.getString("address"));

                resData.put(rs.getString("resName"),tmp);
                System.out.println("Restaurant Name = " + rs.getString("resName"));
                System.out.println("Total Seats = " + rs.getInt("totalSeats"));
                System.out.println("Available Seats = " + rs.getInt("availableSeats"));
                System.out.println("Address = " + rs.getString("address"));

            }
        }
        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }
        return resData;
    }


    public void update(String resName, Integer availableSeats) throws SQLException {
        String sql = "UPDATE restaurant SET totalSeats=? WHERE resName=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(2, resName);
            pstmt.setInt(1, availableSeats);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlTwo = "UPDATE restaurant SET availableSeats=? WHERE resName=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlTwo)) {
            pstmt.setString(2, resName);
            pstmt.setInt(1, availableSeats);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void updateAll(String resName, Integer availableSeats,Integer totalSeats, String address) throws SQLException {
        String sql = "UPDATE restaurant SET totalSeats=? WHERE resName=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(2, resName);
            pstmt.setInt(1, totalSeats);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlTwo = "UPDATE restaurant SET availableSeats=? WHERE resName=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlTwo)) {
            pstmt.setString(2, resName);
            pstmt.setInt(1, availableSeats);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String sqlThree = "UPDATE restaurant SET address=? WHERE resName=?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlThree)) {
            pstmt.setString(2, resName);
            pstmt.setString(1, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}