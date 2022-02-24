package dad.fitnesslibrary.mainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
/**
 * Controllador de la tabla principal
 *
 */
public class MainMenuController implements Initializable {
	
    @FXML
    private Button btn_Exit;

    @FXML
    private Button btn_Start;

    @FXML
    private GridPane rootGridPane;

    @FXML
    void onClickExit(ActionEvent event) {
    	System.exit(0);
    }
    
	public MainMenuController() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
//		getImageViewMain().fitWidthProperty().bind(MainIMGPane.prefWidthProperty());
//		getImageViewMain().fitHeightProperty().bind(MainIMGPane.prefHeightProperty());
	}

	public Button getBtn_Start() {
		return btn_Start;
	}

	public GridPane getRootGridPane() {
		return rootGridPane;
	}
}
