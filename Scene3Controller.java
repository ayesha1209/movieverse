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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Scene3Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button confirmButton;
    private String movieName;
    private double totalTicketPrice;
      public String movieN;
     public String selectedLocation;

    @FXML
    private Label seatCountLabel;
    public LocalDate selectedDate;
   
    private Scene4Controller scene4Controller;

    // Database connection
    

    private List<String> selectedSeatIds1 = new ArrayList<>();
    private int selectedSeatCount = 0;
    

    @FXML
    public void initialize() {
    	
    	selectedDate = SharedData.getSelectedDate();
     selectedLocation = SharedData.getSelectedLocation();
    	
    	 try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha")) {
             String sql = "SELECT mn FROM movie ORDER BY id DESC LIMIT 1"; // Use the correct table name and column name
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();

             if (resultSet.next()) {
                  movieN = resultSet.getString("mn"); // Use the correct column name

                 System.out.print(movieN);
                
             }
         } catch (SQLException e) {
             // Handle the error, e.g., log it
             
             e.printStackTrace();
         }
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha");
            System.out.print("\n DATABASE CONNECTED.... ");
           

            // Fetch seat statuses for the specific movie from the database
            String query = "SELECT seat_id, is_booked, booking_date FROM seats WHERE moviename = ? AND booking_date = ? AND location = ?";
            PreparedStatement selectStatement = connection.prepareStatement(query);
            System.out.print(movieN);
            selectStatement.setString(1, movieN);
            selectStatement.setDate(2, java.sql.Date.valueOf(selectedDate));
            selectStatement.setString(3, selectedLocation); // Set the selected location
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                String seatId = resultSet.getString("seat_id");
                boolean isBooked = resultSet.getBoolean("is_booked");

                root = (Parent) anchor;
                Button button = (Button) root.lookup("#" + seatId);
                if (button != null) {
                    if (isBooked) {
                        // Seat is booked, set its color to red
                        button.setStyle("-fx-background-color: red");
                    } else {
                        // Seat is available, set its color to yellow
                        button.setStyle("-fx-background-color: grey");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (selectedDate != null) {
            // Do something with the selected date
            System.out.println("Selected date in Scene3Controller: " + selectedDate);
        }
        if (selectedLocation != null) {
            // Use the selected location
            System.out.println("Selected location in Scene3Controller: " + selectedLocation);
        }
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
        
    }
   
    public void setSelectedSeatIds1(List<String> seatIds) {
        selectedSeatIds1.addAll(seatIds);
        updateSeatCountLabel();
    }
   
   
    @FXML
    public void selectSeat(ActionEvent event) {
        Button selectedSeat = (Button) event.getSource();
        String seatId = selectedSeat.getId();
        

        if (selectedSeat.getStyle().contains("red")) {
            // Seat is already booked, do nothing
            return;
        }

        if (selectedSeat.getStyle().contains("green")) {
            // Deselect the seat
            selectedSeat.setStyle("-fx-background-color: null");
            selectedSeatIds1.remove(seatId);
        } else {
            // Select the seat
            selectedSeat.setStyle("-fx-background-color: green");
            selectedSeatIds1.add(seatId);
        }

        updateSeatCountLabel();
    }

    private void updateSeatCountLabel() {
        selectedSeatCount = selectedSeatIds1.size();
        seatCountLabel.setText("Selected Seats: " + selectedSeatCount);
    }

   public void goBackToScene2(ActionEvent event) {
        try {
            // Create a FXMLLoader to load Scene2.fxml
        	
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            root = loader.load();

            Scene2Controller scene2Controller = loader.getController();
            
            // Pass any necessary data to Scene2 if needed
            // scene2Controller.setData(data);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    @FXML
    private double fetchTicketPriceFromDatabase() {
        double ticketPrice = 0.0; // Initialize with 0

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha")) {
            String sql = "SELECT TICKETPRICE FROM ticket WHERE MOVIE = ?"; // Use the correct table and column names
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, movieName); // Assuming movieName is accessible in this class
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                ticketPrice = resultSet.getDouble("TICKETPRICE"); // Use the correct column name
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }

        return ticketPrice*selectedSeatCount;
    }

    @FXML
    public void printticket(ActionEvent event) throws IOException {
        // Create a confirmation dialog
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText("Confirm Booking");
        confirmationDialog.setContentText("Are you sure you want to print the ticket?");

        // Show the dialog and wait for the user's response
        ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            // User confirmed, proceed to the print action
        	totalTicketPrice = fetchTicketPriceFromDatabase();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4.fxml"));
            root = loader.load();

            Scene4Controller scene4Controller = loader.getController();
           
            
            scene4Controller.NOS.setText(""+selectedSeatCount);
            scene4Controller.setTotalTicketPrice(totalTicketPrice);
            scene4Controller.setSeatNumbers("" + String.join(", ", selectedSeatIds1));

           
            
            // Pass any necessary data to Scene4 if needed
            // scene4Controller.setData(data);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void confirmBooking(ActionEvent event) {
        if (selectedSeatCount > 0) {
            // If there are selected seats, proceed with the confirmation
            Parent root = ((Node) event.getSource()).getScene().getRoot();
            Button selectedSeat = (Button) event.getSource();

            // Create a confirmation dialog
            Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation");
            confirmationDialog.setHeaderText("Confirm Booking");
            confirmationDialog.setContentText("Are you sure you want to confirm the booking?");

            // Show the dialog and wait for the user's response
            ButtonType result = confirmationDialog.showAndWait().orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                // User confirmed, update seats and store in the database
                LocalDate bookingDate = selectedDate;
                String confirmedSeatIds = String.join(",", selectedSeatIds1);

                for (Node node : root.getChildrenUnmodifiable()) {
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        if (button.getStyle().contains("green")) {
                            // Get the seat ID
                            String seatId = button.getId();

                            if (selectedSeatIds1.contains(seatId)) {
                                // Change the seat color to red
                                button.setStyle("-fx-background-color: red");
                                System.out.print(seatId);

                                // Store the booking information in the database
                                updateSeatsInDatabase(seatId, true, movieName, bookingDate, selectedLocation); // Update seat status to booked
                            }
                        }
                    }
                }
            }
        } else {
            // If no seats are selected, show an alert to inform the user
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Seats Selected");
            alert.setHeaderText("Please select seats before confirming.");
            alert.showAndWait();
        }
    }
    void updateSeatsInDatabase(String seatId, boolean isBooked, String movieName,LocalDate bookingDate,String location) {
        try {
            // Update the database for the selected seat
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviename", "root", "ayesha");
            System.out.print("\n DATABASE CONNECTED.... ");

            // Update the database for the selected seat
            PreparedStatement statement = connection.prepareStatement("INSERT INTO seats (seat_id, is_booked, moviename, booking_date, location) VALUES(?, ?, ?, ?, ?)");
            statement.setBoolean(2, isBooked);
            statement.setString(1, seatId);
            statement.setString(3, movieName); 
            statement.setDate(4, java.sql.Date.valueOf(bookingDate)); // Convert LocalDate to SQL Date
            statement.setString(5, location); // Set the selected location
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database errors here
        }
    }

    }
	
	

