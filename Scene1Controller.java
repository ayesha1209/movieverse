package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Scene1Controller {

	@FXML
	TextField nameTextField;
	@FXML
    PasswordField passwordField;
	
   

	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	public static int i=0;
	
	
	public void goToSignUp(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
	    root = loader.load();
	    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}
    
	public void goToLogin(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
	    root = loader.load();
	    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}

	
	public void Submit(ActionEvent event) throws IOException, SQLException {
	    String username = nameTextField.getText();
	    String password = passwordField.getText();

	    // Check if the fields are empty
	    if (username.isEmpty() || password.isEmpty()) {
	        showAlert(Alert.AlertType.ERROR, stage, "Form Error!", "Please enter a username and password");
	        return;
	    }

	    // Connect to the database
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.print("\n DRIVER ACTIVATED..... ");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        showAlert(Alert.AlertType.ERROR, stage, "Database Error", "Database driver not found.");
	        return;
	    }

	    // Insert the user data into the database
	    try (Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup_movieverse", "root", "ayesha")) {
	        System.out.print("\n DATABASE CONNECTED.... ");

	        // Create a PreparedStatement to insert the user data
	        String query = "INSERT INTO signupdate (username, password) VALUES (?, ?)";
	        PreparedStatement st = con2.prepareStatement(query);
	        st.setString(1, username);
	        st.setString(2, password);

	        // Execute the query
	        int rowsAffected = st.executeUpdate();

	        if (rowsAffected > 0) {
	            // Signup successful, show a "Submitted" alert
	            showAlert(Alert.AlertType.INFORMATION, stage, "Submitted", "Your signup was successful!");
	        } else {
	            // Something went wrong, show an error alert
	            showAlert(Alert.AlertType.ERROR, stage, "Error", "Failed to submit the signup data.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        showAlert(Alert.AlertType.ERROR, stage, "Username Same ", "Try different Username.");
	    }
	}


		
        
        
        
      

	
	public void login(ActionEvent event) throws IOException, SQLException {
	    String username = nameTextField.getText();
	    String password = passwordField.getText();

	    // Check if the fields are empty
	    if (username.isEmpty() || password.isEmpty()) {
	        showAlert(Alert.AlertType.ERROR, stage, "Form Error!", "Please enter a username and password");
	        return;
	    }

	    // Connect to the database
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        System.out.print("\n DRIVER ACTIVATED..... ");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        showAlert(AlertType.ERROR, stage, "Database Error", "Database driver not found.");
	        return;
	    }

	    // Query the database to check if the user exists and the password matches
	    try (Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup_movieverse", "root", "ayesha")) {
	        System.out.print("\n DATABASE CONNECTED.... ");

	        // Create a PreparedStatement to select the user by username
	        String query = "SELECT * FROM signupdate WHERE username = ?";
	        PreparedStatement st = con2.prepareStatement(query);
	        st.setString(1, username);

	        // Execute the query
	        ResultSet resultSet = st.executeQuery();

	        if (resultSet.next()) {
	            // User exists, check the password
	            String storedPassword = resultSet.getString("password");
	            if (password.equals(storedPassword)) {
	                // Password matches, login successful
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
	                root = loader.load();
	                HomeController HomeController = loader.getController();
	                HomeController.displayName(username);
	                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	                scene = new Scene(root);
	                stage.setScene(scene);
	                stage.show();
	            } else {
	                // Password is incorrect, show an alert
	                infoBox("Incorrect password. Please try again.", null, "Wrong Password");
	            }
	        } else {
	            // User doesn't exist, show an alert to sign up
	            infoBox("User not found. Please sign up.", null, "User Not Found");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        showAlert(AlertType.ERROR, stage, "Database Error", "Failed to execute the database query.");
	    }
	}


    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
		
		
		
	}
