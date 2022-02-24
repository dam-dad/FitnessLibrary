package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
/**
 * Controlador de la barra de menú 
 *
 */
public class MenuBarController implements Initializable {

	@FXML
    private MenuBar view;

	@FXML
	private Button buscarButton;
	
	@FXML
    private Button clearFiltersButton;

	@FXML
	private TextField busquedaText;

	public MenuBarController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuBar.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public TextField getBusquedaText() {
		return busquedaText;
	}
	
	public Button getClearFiltersButton() {
		return clearFiltersButton;
	}

	public Button getBuscarButton() {
		return buscarButton;
	}
	
	public MenuBar getView() {
		return view;
	}

}
