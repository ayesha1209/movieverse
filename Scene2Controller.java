package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Scene2Controller {

	@FXML
	Label nameLabel;
	ImageView movie1;
	
	
	
	public void displayName(String username) {
		nameLabel.setText("Welcome: " + username +" to MOVIEVERSE your companion for booking");
	}
	
}