package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Scene2Controller {
    @FXML
    private Label nameLabel;

    @FXML
    private Button JAWAN;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void displayName(String username) {
        nameLabel.setText("Welcome: " + username + " to MOVIEVERSE, your companion for booking");
    }

    @FXML
    public void buyTicket(ActionEvent event) throws IOException {
        // Identify which button was clicked (in this case, we have JAWAN as an example)
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        // Insert the movie data into the database
        insertMovieIntoDatabase(buttonId);
        
        try {
        	 FXMLLoader loader2 = new FXMLLoader(getClass().getResource("Scene4.fxml"));
			root = loader2.load();
			Scene4Controller scene = loader2.getController();
			scene.moviename.setText(buttonId);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Load the next scene (Scene3.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));
        root = loader.load();

        // You can pass data to Scene3 if needed
        Scene3Controller scene3Controller = loader.getController();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    void insertMovieIntoDatabase(String movieName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.print("\n DRIVER ACTIVATED..... ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Database driver not found.");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha")) {
            System.out.print("\n DATABASE CONNECTED.... ");
            String insertQuery = "INSERT INTO movie (mn) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, movieName);
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Database Error", "Failed to execute the database query.");
        }
        
        
        
        try {
        	 FXMLLoader loader2 = new FXMLLoader(getClass().getResource("Scene4.fxml"));
			root = loader2.load();
			Scene4Controller scene = loader2.getController();
			scene.moviename.setText(movieName);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         
    }
}

