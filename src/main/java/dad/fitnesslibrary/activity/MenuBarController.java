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

	@FXML
	private ComboBox<String> grupoMuscularCombo;

	@FXML
	private ComboBox<String> musculoConcretoCombo;

	@FXML
	private ComboBox<String> equipamientoCombo;

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
		settingComboBoxes();
	}

	private void settingComboBoxes() {
		try {
			getBodyParts();
			getTargets();
			getEquipments();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// List of Bodyparts (Robertz)
	private void getBodyParts() throws IOException {
		getResponseBodyString("/bodyPartList", TipoComboBox.BODYPART);
	}

	// List of Targets (Robertz)
	private void getTargets() throws IOException {
		getResponseBodyString("/targetList", TipoComboBox.TARGET);
	}

	// List of Equipments (Robertz)
	private void getEquipments() throws IOException {
		getResponseBodyString("/equipmentList", TipoComboBox.EQUIPMENT);
	}

	private void getResponseBodyString(String parameter, TipoComboBox tipo) throws IOException {
		Task<List<String>> rbTask = new Task<List<String>>() {
			@Override
			protected List<String> call() throws Exception {
				return gson.fromJson(backgroundCall(parameter), stringListType);
			}
		};

		rbTask.setOnSucceeded(e -> {
			try {
				List<String> listaString = rbTask.get();
				switch (tipo) {
				case BODYPART:
					grupoMuscularCombo.getItems().addAll(listaString);
					break;

				case EQUIPMENT:
					equipamientoCombo.getItems().addAll(listaString);
					break;

				case TARGET:
					musculoConcretoCombo.getItems().addAll(listaString);
					break;
				}
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		});

		rbTask.setOnFailed(e -> {
			System.err.println(rbTask.getException());
		});

		new Thread(rbTask).start();
	}

	private String backgroundCall(String parameter) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://exercisedb.p.rapidapi.com/exercises" + parameter).get()
				.addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "582862b3a3mshb1f23c0f20232c2p175ea4jsn3aa2ad592392").build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}
	
	public ComboBox<String> getGrupoMuscularCombo() {
		return grupoMuscularCombo;
	}

	public ComboBox<String> getMusculoConcretoCombo() {
		return musculoConcretoCombo;
	}

	public ComboBox<String> getEquipamientoCombo() {
		return equipamientoCombo;
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
