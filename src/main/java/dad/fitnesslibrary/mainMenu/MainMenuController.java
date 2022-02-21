package dad.fitnesslibrary.mainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class MainMenuController implements Initializable {
	
    @FXML
    private ImageView imageViewMain;
    

    @FXML
    private Pane MainIMGPane;
    
    @FXML
    private GridPane GridPaneMenu;
    
    @FXML
    private Button btn_Exit;

    @FXML
    private Button btn_Start;

    @FXML
    void onClickExit(ActionEvent event) {
    	System.exit(0);
    }

    @FXML
    void onClickStart(ActionEvent event) {

    }


    
    

	public MainMenuController() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPage.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		getImageViewMain().fitWidthProperty().bind(MainIMGPane.prefWidthProperty());
		getImageViewMain().fitHeightProperty().bind(MainIMGPane.prefHeightProperty());
		

	}
	

	public ImageView getImageViewMain() {
		return imageViewMain;
	}

	public Pane getMainIMGPane() {
		return MainIMGPane;
	}

	public GridPane getGridPaneMenu() {
		return GridPaneMenu;
	}
	

}
