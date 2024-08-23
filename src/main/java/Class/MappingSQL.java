package Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Reyner
 */
public class MappingSQL {
    private final Connection connection;
    
    public MappingSQL(){
    this.connection = ConnectionSingleTon.getInstance().conn;
    }
    
    public int insertClientToDB(Client client){
        String query = "INSERT INTO client (client_name, client_cedula) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getCedula());
            pstmt.executeUpdate();
            
             try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    client.setIdClient(id);
                    return id;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<Client> loadClientFromDB(){
        List<Client> clientInfo = new ArrayList<>();
        String query = "SELECT client_id, client_name, client_cedula FROM client";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    int id = rs.getInt("client_id");
                    String name = rs.getString("client_name");
                    String cedula = rs.getString("client_cedula");
                    Client client = new Client(id, cedula, name);
                    clientInfo.add(client);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return clientInfo;
    }
    
    public boolean deleteClientFromDB(int clientId){
        String deleteQuery = "DELETE FROM property WHERE client_id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(deleteQuery)){
            pstmt.setInt(1, clientId);
            pstmt.executeUpdate();
        }catch(SQLException e){
         e.printStackTrace();
         return false;
        }
        
        
        String deleteUserQuery = "DELETE FROM client WHERE client_id = ?";
        try(PreparedStatement pstmtUser = connection.prepareStatement(deleteUserQuery)){
            pstmtUser.setInt(1, clientId);
            int rowsAffected = pstmtUser.executeUpdate();
            return rowsAffected > 0;  
        }catch(SQLException e){
         e.printStackTrace();
         return false;
        } 
    }
    
    public boolean insertPropertyToDB(PropertyClient property){
        String query = "INSERT INTO property (property_type, property_status, property_direction, property_size, client_id, value) VALUES (?,?,?,?,?,?)"; 
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, property.getType());
            pstmt.setString(2, property.getStatus());
            pstmt.setString(3, property.getDirection());
            pstmt.setString(4, property.getSize());
            pstmt.setInt(5, property.getClientPropertyId());
            pstmt.setInt(6, property.getValue());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<PropertyClient> loadPropertyFromDB(int clientId) {
        List<PropertyClient> properties = new ArrayList<>();
        String query = "SELECT property_type, property_status, property_direction, property_size, property_id, value FROM property WHERE client_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, clientId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String propertyType = rs.getString("property_type");
                    String propertyStatus = rs.getString("property_status");
                    String propertyDirection = rs.getString("property_direction");
                    String propertySize = rs.getString("property_size");
                    int propertyId = rs.getInt("property_id");
                    int value = rs.getInt("value");

                    PropertyClient property = new PropertyClient(propertyType, propertyStatus, propertyDirection, propertySize, clientId, propertyId, value);
                    properties.add(property);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    
    public boolean deletePropertyFromDB(int propertyId) {
        String query = "DELETE FROM property WHERE property_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, propertyId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }   
    }
  
    
    public boolean updateClientFromDB(Client client){
        String query = "UPDATE client SET client_name= ?, client_cedula = ? WHERE client_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getCedula());
            pstmt.setInt(3, client.getIdClient());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updatePropertyFromDB(PropertyClient property){
        String query = "UPDATE property SET property_type = ?, property_status = ?, property_direction = ?, property_size = ?, value = ? WHERE property_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, property.getType());
            pstmt.setString(2, property.getStatus());
            pstmt.setString(3, property.getDirection());
            pstmt.setString(4, property.getSize());
            pstmt.setInt(5, property.getValue());
            pstmt.setInt(6, property.getPropertyId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<PropertyClient> filterByType(String type){
        List<PropertyClient> properties = new ArrayList<>();
        String query = "SELECT * FROM property WHERE property_type = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String propertyType = rs.getString("property_type");
                    String propertyStatus = rs.getString("property_status");
                    String propertyDirection = rs.getString("property_direction");
                    String propertySize = rs.getString("property_size");
                    int propertyId = rs.getInt("property_id");
                    int value = rs.getInt("value");

                    PropertyClient property = new PropertyClient(propertyType, propertyStatus, propertyDirection, propertySize, -1, propertyId, value);
                    properties.add(property);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    public List<PropertyClient> filterByStatus(String type){
        List<PropertyClient> properties = new ArrayList<>();
        String query = "SELECT * FROM property WHERE property_status = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String propertyType = rs.getString("property_type");
                    String propertyStatus = rs.getString("property_status");
                    String propertyDirection = rs.getString("property_direction");
                    String propertySize = rs.getString("property_size");
                    int propertyId = rs.getInt("property_id");
                    int value = rs.getInt("value");

                    PropertyClient property = new PropertyClient(propertyType, propertyStatus, propertyDirection, propertySize, -1, propertyId, value);
                    properties.add(property);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    public List<PropertyClient> filterByPrice(String price){
        List<PropertyClient> properties = new ArrayList<>();
        String query = "";
        
        switch (price) {
        case "<1000000":
            query = "SELECT * FROM property WHERE value < 1000000";
            break;
        case "2000000-6000000":
            query = "SELECT * FROM property WHERE value BETWEEN 2000000 AND 6000000";
            break;
        case "6000000-10000000":
            query = "SELECT * FROM property WHERE value BETWEEN 6000000 AND 10000000";
            break;
        case ">10000000":
            query = "SELECT * FROM property WHERE value > 10000000";
            break;
    }
    
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String propertyType = rs.getString("property_type");
                    String propertyStatus = rs.getString("property_status");
                    String propertyDirection = rs.getString("property_direction");
                    String propertySize = rs.getString("property_size");
                    int propertyId = rs.getInt("property_id");
                    int value = rs.getInt("value");

                    PropertyClient property = new PropertyClient(propertyType, propertyStatus, propertyDirection, propertySize, -1, propertyId, value);
                    properties.add(property);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

    
