package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

public class MenuLeftController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	@FXML
    private RadioButton EquipamientoRadio;

    @FXML
    private VBox LeftMenuView;

    @FXML
    private RadioButton MúsculoRadio;

    @FXML
    private RadioButton RadioButtonRadio;
    
    public MenuLeftController() {
    
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuLeft.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void onActionEquipamientoRadio(ActionEvent event) {

    }

    @FXML
    void onActionMúsculoRadio(ActionEvent event) {

    }

    @FXML
    void onActionRadioButtonRadio(ActionEvent event) {

    }


}
