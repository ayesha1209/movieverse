package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button goToScene2Button;
    @FXML
    private Label nameLabel;
   
    
    

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
        nameLabel.setText("Welcome: " + username + " to MOVIEVERSE, your companion for booking");
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
}
