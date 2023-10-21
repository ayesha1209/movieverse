package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Scene4Controller implements Initializable {
    @FXML Label moviename;
    @FXML Label NOS;
   @FXML Label nameofseat;
    @FXML Label TAP;

    private Stage stage;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha")) {
            String sql = "SELECT mn FROM movie ORDER BY id DESC LIMIT 1"; // Use the correct table name and column name
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String movieName = resultSet.getString("mn"); // Use the correct column name

                // Set the movie name to the Label with fx:id "moviename"
                moviename.setText(movieName);
               
            }
        } catch (SQLException e) {
            // Handle the error, e.g., log it
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to retrieve movie name.");
            e.printStackTrace();
        }
        
        
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha");
			 System.out.print("\n DATABASE CONNECTED.... ");

		        // Fetch seat statuses from the database
		        PreparedStatement selectStatement = connection.prepareStatement("SELECT seat_id FROM seats");
		        ResultSet resultSet = selectStatement.executeQuery();
                String seatnumbers=" ";
		        while(resultSet.next())
		        {
		        	
		        	seatnumbers = seatnumbers+","+resultSet.getString(1);
		        	
		        	
		        }
		        
		        nameofseat.setText(seatnumbers);
		        
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha")) {
		    String sql = "SELECT TICKETPRICE FROM ticket WHERE MOVIE = ?"; // Use the correct table and column names from the other database
		    PreparedStatement preparedStatement = connection.prepareStatement(sql);
		    preparedStatement.setString(1, moviename.getText().trim()); // Assuming moviename is the movie name
		    ResultSet resultSet = preparedStatement.executeQuery();
           
		    if (resultSet.next()) {
		        double ticketPrice = resultSet.getDouble("TICKETPRICE"); // Use the correct column name
		        System.out.print(ticketPrice);
		        
		        // Calculate and set the total ticket price
		         numberOfSeats = numberOfSeats+Integer.parseInt(NOS.getText()); 
		        System.out.print(numberOfSeats);
		        // Assuming NOS is the number of seats
		        double totalTicketPrice = ticketPrice * numberOfSeats;
		        TAP.setText(""+totalTicketPrice);
		        
		    }
		} catch (SQLException e) {
		    // Handle the error, e.g., log it
		    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to retrieve ticket price.");
		    e.printStackTrace();
		}

       
    }
    

    @FXML
    public void goBackToScene3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));
        
        Parent root = loader.load();

        Scene3Controller scene3Controller = loader.getController();
        // Pass any necessary data back to Scene3 if needed

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Add handlers for the "book more" and "homepage" buttons if needed.

    // Helper method to show an alert
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
	
}

