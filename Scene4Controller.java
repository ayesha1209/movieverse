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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Scene4Controller implements Initializable {
    @FXML Label moviename;
    @FXML Label NOS;
   @FXML Label nameofseat;
    @FXML Label TAP;
    @FXML
    private Label selectedDateLabel;

    private double totalTicketPrice;
    private Stage stage;
    private LocalDate selectedDate;
    private String selectedLocation;
    @FXML Label  locations;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	 selectedDate = SharedData.getSelectedDate();
    	 selectedLocation = SharedData.getSelectedLocation();
    	 
    	    selectedDateLabel.setText(selectedDate.toString());
    	    locations.setText(selectedLocation);
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
        
        


		
       
    }
    @FXML
    public void setSeatNumbers(String seatNumbers) {
        nameofseat.setText(seatNumbers);
    }

    public void setMovieName(String movieName) {
        moviename.setText(movieName);
    }
    public void setTotalTicketPrice(double totalTicketPrice) {
        this.totalTicketPrice = totalTicketPrice;
        TAP.setText(String.format("$%.2f", totalTicketPrice));
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
