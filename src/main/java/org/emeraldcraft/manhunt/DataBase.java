package org.emeraldcraft.manhunt;

import org.bukkit.Bukkit;

import java.sql.*;
import java.util.UUID;
import java.util.logging.Level;

import static java.util.logging.Level.INFO;

public class DataBase {
    private String url;
    private Integer port;
    private String username;
    private String name;
    private String password;
    private Connection connection;
    private boolean isEnabled = false;
    public DataBase(String url, Integer port,String name ,String username, String password){
        this.url = url;
        this.name = name;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    public void testConnection(){
        try {
            this.openConnection();
            if(getConnection() != null && !getConnection().isClosed()){
                closeConnection();
                Bukkit.getLogger().log(INFO, "[MANHUNT] Test database connection successful! You are good to go!");
                isEnabled = true;
                return;
            }
            isEnabled = false;
            Bukkit.getLogger().log(INFO, "[MANHUNT] There seems to be a problem while opening up the database connection! Please consult any stacktrace that may have been printed.");
        } catch (SQLException e) {
            e.printStackTrace();
            isEnabled = false;
            Bukkit.getLogger().log(INFO, "[MANHUNT] There seems to be a problem while opening up the database connection! Please consult any stacktrace that may have been printed.");
        }
    }
    public void openConnection(){
        try {
            String url = "jdbc:mysql://" + this.url + ":" + this.port + "/" + this.name;
            // try catch to get any SQL errors (for example connections errors)
            connection = DriverManager.getConnection(url, username, password);
            // with the method getConnection() from DriverManager, we're trying to set
            // the connection's url, username, password to the variables we made earlier and
            // trying to get a connection at the same time. JDBC allows us to do this.
        } catch (SQLException e) { // catching errors
            e.printStackTrace(); // prints out SQLException errors to the console (if any)
        }
    }
    public void closeConnection(){
        try {
            if(getConnection() != null && !getConnection().isClosed()){
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getter and Setters
    public String getUrl(){
        return url;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Connection getConnection() {
        return connection;
    }
    //

    public void initializeDB(){
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            String sqlSelect = "SELECT * FROM manhuntStats";
            PreparedStatement stmt = connection.prepareStatement(sqlSelect);
            stmt.executeUpdate();
            closeConnection();
        }
        catch (SQLException e){
            return;
        }
    }

    public void addManhuntWin(UUID uuid){
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            String sqlcreateTable = "CREATE TABLE IF NOT EXISTS manhuntStats(UUID  varchar(100),  wins INTEGER(10), losses INTEGER(10), deaths INTEGER(10));";
            String sqlins = "insert into manhuntStats(UUID, wins, losses ,deaths)  values(?,1,0,0);";
            String sqlupdate = "update manhuntStats set wins = ? where uuid = ?;";
            String sqlSelect = "SELECT UUID,wins,losses,deaths FROM manhuntStats ORDER BY wins DESC;";

            // Create table
            PreparedStatement stmt = connection.prepareStatement(sqlcreateTable);
            stmt.executeUpdate();


            PreparedStatement stmt2 = connection.prepareStatement(sqlSelect);
            ResultSet results = stmt2.executeQuery();
            boolean foundUUID = false;
            while (results.next()) {
                String sqlUUID = results.getString("UUID");
                if (sqlUUID.equalsIgnoreCase(uuid.toString())) {
                    PreparedStatement stmt3 = connection.prepareStatement(sqlupdate);
                    int wins = results.getInt("wins");

                    stmt3.setString(2, uuid.toString());
                    stmt3.setInt(1, wins + 1);


                    stmt3.executeUpdate();
                    foundUUID = true;
                }
            }
            if(!foundUUID){
                //Insert Data
                PreparedStatement stmt3 = connection.prepareStatement(sqlins);
                stmt3.setString(1, uuid.toString());
                stmt3.executeUpdate();
            }
            closeConnection();
        }
        catch (SQLException e){
            Bukkit.getLogger().log(Level.SEVERE, "A database error has occurred!");
            e.printStackTrace();
        }

    }
    public void addManhuntLoss(UUID uuid){
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            String sqlcreateTable = "CREATE TABLE IF NOT EXISTS manhuntStats(UUID  varchar(100),  wins INTEGER(10), losses INTEGER(10), deaths INTEGER(10));";
            String sqlins = "insert into manhuntStats(UUID, wins, losses ,deaths)  values(?,0,1,0);";
            String sqlupdate = "update manhuntStats set losses = ? where uuid = ?;";
            String sqlSelect = "SELECT UUID,losses FROM manhuntStats ORDER BY deaths DESC;";

            // Create table
            PreparedStatement stmt = connection.prepareStatement(sqlcreateTable);
            stmt.executeUpdate();


            PreparedStatement stmt2 = connection.prepareStatement(sqlSelect);
            ResultSet results = stmt2.executeQuery();
            boolean foundUUID = false;
            while (results.next()) {
                String sqlUUID = results.getString("UUID");
                if (sqlUUID.equalsIgnoreCase(uuid.toString())) {
                    PreparedStatement stmt3 = connection.prepareStatement(sqlupdate);
                    int losses = results.getInt("losses");

                    stmt3.setString(2, uuid.toString());
                    stmt3.setInt(1, losses + 1);


                    stmt3.executeUpdate();
                    foundUUID = true;
                }
            }
            if(!foundUUID){
                //Insert Data
                PreparedStatement stmt3 = connection.prepareStatement(sqlins);
                stmt3.setString(1, uuid.toString());
                stmt3.executeUpdate();
            }
            closeConnection();
        }
        catch (SQLException e){
            Bukkit.getLogger().log(Level.SEVERE, "A database error has occurred!");
            e.printStackTrace();
        }
    }
    public void addManhuntDeath(UUID uuid){
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            String sqlcreateTable = "CREATE TABLE IF NOT EXISTS manhuntStats(UUID  varchar(100),  wins INTEGER(10), losses INTEGER(10), deaths INTEGER(10));";
            String sqlins = "insert into manhuntStats(UUID, wins, losses ,deaths)  values(?,0,0,1);";
            String sqlupdate = "update manhuntStats set deaths = ? where uuid = ?;";
            String sqlSelect = "SELECT UUID,deaths FROM manhuntStats ORDER BY deaths DESC;";

            // Create table
            PreparedStatement stmt = connection.prepareStatement(sqlcreateTable);
            stmt.executeUpdate();


            PreparedStatement stmt2 = connection.prepareStatement(sqlSelect);
            ResultSet results = stmt2.executeQuery();
            boolean foundUUID = false;
            while (results.next()) {
                String sqlUUID = results.getString("UUID");
                if (sqlUUID.equalsIgnoreCase(uuid.toString())) {
                    PreparedStatement stmt3 = connection.prepareStatement(sqlupdate);
                    int deaths = results.getInt("deaths");

                    stmt3.setString(2, uuid.toString());
                    stmt3.setInt(1, deaths + 1);


                    stmt3.executeUpdate();
                    foundUUID = true;
                }
            }
            if(!foundUUID){
                //Insert Data
                PreparedStatement stmt3 = connection.prepareStatement(sqlins);
                stmt3.setString(1, uuid.toString());
                stmt3.executeUpdate();
            }
            closeConnection();
        }
        catch (SQLException e){
            Bukkit.getLogger().log(Level.SEVERE, "A database error has occurred!");
            e.printStackTrace();
        }

    }
    public Integer getManhuntWins(UUID uuid) {
        String sqlSelect = "SELECT UUID,wins,losses,deaths FROM manhuntStats ORDER BY wins DESC;";
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            PreparedStatement stmt2 = connection.prepareStatement(sqlSelect);
            ResultSet results = stmt2.executeQuery();
            while (results.next()) {
                String sqlUUID = results.getString("UUID");
                if (sqlUUID.equalsIgnoreCase(uuid.toString())) {
                    return results.getInt("wins");
                }
            }
            closeConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public Integer getManhuntLosses(UUID uuid) {
        String sqlSelect = "SELECT UUID,losses FROM manhuntStats ORDER BY losses DESC;";
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            PreparedStatement stmt2 = connection.prepareStatement(sqlSelect);
            ResultSet results = stmt2.executeQuery();
            while (results.next()) {
                String sqlUUID = results.getString("UUID");
                if (sqlUUID.equalsIgnoreCase(uuid.toString())) {
                    return results.getInt("losses");
                }
            }
            closeConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public Integer getManhuntDeaths(UUID uuid) {
        String sqlSelect = "SELECT UUID,deaths FROM manhuntStats ORDER BY deaths DESC;";
        try {
            if(getConnection() == null || getConnection().isClosed()){
                openConnection();
            }
            Connection connection = getConnection();
            PreparedStatement stmt2 = connection.prepareStatement(sqlSelect);
            ResultSet results = stmt2.executeQuery();
            while (results.next()) {
                String sqlUUID = results.getString("UUID");
                if (sqlUUID.equalsIgnoreCase(uuid.toString())) {
                    return results.getInt("deaths");
                }
            }
            closeConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}