package group4.householdhero.view;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {
	
	@FXML
	private Label primaryLabel;

	@FXML
	private void initialize() throws IOException{
		primaryLabel.setText(View.getTestString()); //MVC testing
	}
	
    @FXML
    private void switchToSecondary() throws IOException {
        View.setRoot("secondary");
    }
    
}
