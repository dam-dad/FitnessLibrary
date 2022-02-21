package dad.fitnesslibrary.mainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class MainMenuController implements Initializable {
	
    @FXML
    private ImageView imageViewMain;

    
    

	public MainMenuController() {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		

	}

	public ImageView getImageViewMain() {
		return imageViewMain;
	}


}
