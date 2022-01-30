package dad.fitnesslibrary.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MenuBarController implements Initializable {

    @FXML
    private AnchorPane view;
	
    @FXML
    private Button buscarButton;

    @FXML
    private TextField busquedaText;

    @FXML
    private ComboBox<String> grupoMuscularCombo;

    @FXML
    private ComboBox<String> musculoConcretoCombo;

    @FXML
    private ComboBox<String> parteCuerpoCombo;

	
	public MenuBarController(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuBar.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
    @FXML
    void onBuscarButtonAction(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public AnchorPane getView(){
		return view;
	}

}
