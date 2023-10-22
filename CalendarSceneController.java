package application;

import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class CalendarSceneController {

    private HomeController homeController;  // Reference to HomeController
    
    @FXML
    private DatePicker datePicker;
    
    private Scene3Controller scene3Controller;
    @FXML
    private Button confirmButton;

    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    public void initialize() {
        // Set a custom day cell factory to restrict date selection
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(java.time.LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Calculate the current date and the date two days from now
                java.time.LocalDate currentDate = java.time.LocalDate.now();
                java.time.LocalDate twoDaysAfter = currentDate.plusDays(2);

                // Disable dates that are before the current date or more than two days after
                setDisable(item.isBefore(currentDate) || item.isAfter(twoDaysAfter));
            }
        });
    }

    public void handleDateSelection(ActionEvent event) {
        // Capture the selected date from the DatePicker
        LocalDate selectedDate = datePicker.getValue();

        if (scene3Controller == null) {
            // Create an instance of Scene3Controller if it's not already created
            scene3Controller = new Scene3Controller();
        }

        // Pass the selected date to the Scene3Controller
        scene3Controller.setSelectedDate(selectedDate);

        // Now, you can perform any other actions or switch to other scenes as needed
        // For example, you can call methods on scene3Controller or switch to another scene.
    }
    @FXML
    public void confirmDate() {
        // Handle date selection logic

        // After selecting a date, return to the home scene
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            HomeController homeController = loader.getController();
            homeController.displayName("Username");  // Replace "Username" with the actual username

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
