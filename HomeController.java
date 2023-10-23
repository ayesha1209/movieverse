package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController implements Initializable{

    @FXML
    private Button goToScene2Button;
    @FXML
    private Label nameLabel;
    @FXML
    private ChoiceBox<String> location;
	public String selectedLocation;
    private String[] loc = {
    	    "PVR Cinemas, Vadodara",
    	    "INOX - Seven Seas Mall, Vadodara",
    	    "Cinépolis - Inorbit Mall, Vadodara",
    	    "Cinepolis - Centre Square Mall, Vadodara",
    	    "PVR R-City Mall, Vadodara",
    	    "PVR Cinemas, Lower Parel, Mumbai",
    	    "INOX - R City Mall, Ghatkopar, Mumbai",
    	    "Carnival Cinemas, Andheri, Mumbai",
    	    "Cinepolis - Viviana Mall, Thane, Mumbai",
    	    "IMAX - Wadala, Mumbai",
    	    "PVR Forum Mall, Bangalore",
    	    "INOX - Garuda Mall, Bangalore",
    	    "Cinepolis - Orion Mall, Bangalore",
    	    "SPI Cinemas - Lido Mall, Bangalore",
    	    "Gopalan Cinemas, Bangalore",
    	    "PVR Cinemas, Rajkot",
    	    "INOX - Crystal Mall, Rajkot",
    	    "Carnival Cinemas, Rajkot",
    	    "Cosmoplex Cinema, Rajkot",
    	    "INOX - Reliance Mega Mall, Rajkot",
    	    "PVR Cinemas, Surat",
    	    "INOX - VR Surat, Surat",
    	    "Rajhans Cinemas, Surat",
    	    "Cinepolis - Imperial Square, Surat",
    	    "Central Mall Multiplex, Surat",
    	    "PVR Cinemas, Ahmedabad",
    	    "INOX - Himalaya Mall, Ahmedabad",
    	    "Cinépolis - AlphaOne Mall, Ahmedabad",
    	    "Carnival Cinemas, Ahmedabad",
    	    "City Gold Multiplex, Ahmedabad"
    	};

   
    
    

   public void openCalendarScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CalenderScene.fxml"));
            Parent root = loader.load();
            CalendarSceneController calendarController = loader.getController();
            calendarController.setHomeController(this);  // Pass HomeController reference

            Stage stage = (Stage) goToScene2Button.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    @FXML
    void initialize() {
        goToScene2Button.setOnAction(event -> {
            // Load Scene2
            loadScene2(event);
        });
    }

    public void displayName(String username) {
        nameLabel.setText("Welcome: " + username + " to MOVIEVERSE");
    }

    public void loadScene2(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            Parent root = loader.load();
            Scene2Controller scene2Controller = loader.getController();
            

            // Pass data or perform any setup here if needed

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene
            Scene scene = new Scene(root);

            // Set the new scene on the stage
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleLocationSelection() {
        selectedLocation = location.getValue();
        SharedData.setSelectedLocation(selectedLocation);
        // You can now use 'selectedLocation' in other methods or perform any necessary action.
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		location.getItems().addAll(loc);
		 location.setOnAction(event -> handleLocationSelection());
		
	}
}