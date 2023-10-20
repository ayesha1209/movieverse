package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Scene2Controller {

    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
	Label nameLabel;
	ImageView movie1;
	 
	    public void displayName(String username) {
	    	nameLabel.setText("Welcome: " + username +" to MOVIEVERSE your companion for booking");
	    }
	

	    
    @FXML
    public void buyTicket(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3.fxml"));
        root = loader.load();

        Scene3Controller scene3Controller = loader.getController();
        // Pass any necessary data to Scene3 if needed
        // scene3Controller.setData(data);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   
}
