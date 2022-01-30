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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class MenuLeftController implements Initializable {

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private Type stringListType = new TypeToken<List<String>>() {
	}.getType();

	private ToggleGroup bodypartTG = new ToggleGroup();

	private ToggleGroup equipmentTG = new ToggleGroup();

	private ToggleGroup targetTG = new ToggleGroup();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		generateRadioButtons();
	}

	@FXML
    private VBox equipamientoVBox;

    @FXML
    private VBox grupoMuscularVBox;

    @FXML
    private VBox musculoVBox;
	
    @FXML
    private ScrollPane LeftMenuView;

	public MenuLeftController() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuLeft.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generateRadioButtons() {
		try {
			getBodyParts();
			getEquipments();
			getTargets();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// List of Bodyparts (Robertz)
	public void getBodyParts() throws IOException {
		getResponseBodyString("/bodyPartList", TipoComboBox.BODYPART);
	}

	// List of Targets (Robertz)
	public void getTargets() throws IOException {
		getResponseBodyString("/targetList", TipoComboBox.TARGET);
	}

	// List of Equipments (Robertz)
	public void getEquipments() throws IOException {
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
					bodypartTG = new ToggleGroup();
					for (String s : listaString) {
						RadioButton radioButton = new RadioButton(s);
						radioButton.setToggleGroup(bodypartTG);
						grupoMuscularVBox.getChildren().add(radioButton);
					}
					break;

				case EQUIPMENT:
					equipmentTG = new ToggleGroup();
					for (String s : listaString) {
						RadioButton radioButton = new RadioButton(s);
						radioButton.setToggleGroup(equipmentTG);
						equipamientoVBox.getChildren().add(radioButton);
					}
					break;

				case TARGET:
					targetTG = new ToggleGroup();
					for (String s : listaString) {
						RadioButton radioButton = new RadioButton(s);
						radioButton.setToggleGroup(targetTG);
						musculoVBox.getChildren().add(radioButton);
					}
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
	
	public ScrollPane getLeftMenuView() {
		return LeftMenuView;
	}

}
