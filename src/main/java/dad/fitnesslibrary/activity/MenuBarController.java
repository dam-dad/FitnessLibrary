package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dad.fitnesslibrary.classes.TipoComboBox;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;

public class MenuBarController implements Initializable {

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private Type stringListType = new TypeToken<List<String>>() {
	}.getType();

	@FXML
    private MenuBar view;

	@FXML
	private Button buscarButton;

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

	public Button getBuscarButton() {
		return buscarButton;
	}
	
	public MenuBar getView() {
		return view;
	}

}
