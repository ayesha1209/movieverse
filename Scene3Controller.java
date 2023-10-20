package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Scene3Controller {
	
	  private Stage stage;
	    private Scene scene;
	    private Parent root;
  
    private boolean[] seatAvailability = new boolean[20];

    @FXML
    public void initialize() {
        // Initialize all seats as available
        for (int i = 0; i < seatAvailability.length; i++) {
            seatAvailability[i] = true;
        }
    }

    
    
    @FXML
   
    public void selectSeat(ActionEvent event) {
        Button selectedSeat = (Button) event.getSource();
        String buttonText = selectedSeat.getText();
        // Extract the seat number from the text
        String seatNumberText = buttonText.substring(buttonText.indexOf(" ") + 1);
        int seatNumber = Integer.parseInt(seatNumberText);

        if (seatAvailability[seatNumber - 1]) {
            // Seat is available, allow selection
            selectedSeat.setStyle("-fx-background-color: yellow;");
            seatAvailability[seatNumber - 1] = false; // Mark seat as selected
        } else {
            // Seat is already booked, indicate to the user
            selectedSeat.setStyle("-fx-background-color: red;");
        }
    }



    public void goBackToScene2(ActionEvent event) {
        // Create a FXMLLoader to load Scene2.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene2Controller scene2Controller = loader.getController();
        // Pass any necessary data to Scene2 if needed
        // scene2Controller.setData(data);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    public void printticket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4.fxml"));
        root = loader.load();

        Scene4Controller scene4Controller = loader.getController();
        // Pass any necessary data to Scene3 if needed
        // scene3Controller.setData(data);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}





/*package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Scene3Controller {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void selectSeat(ActionEvent event) {
        // Handle seat selection logic here
    }

   
}
*/