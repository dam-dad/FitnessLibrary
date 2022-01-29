package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import dad.fitnesslibrary.classes.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ListViewController implements Initializable {

	@FXML
	private ListView<Exercise> root;

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	Type exerciseListType = new TypeToken<List<Exercise>>() {
	}.getType();

	Type stringListType = new TypeToken<List<String>>() {
	}.getType();

	public ListViewController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListViewExercises.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			List<Exercise> exercises = getAllExercises();
			root.getItems().addAll(exercises);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onRootDoubleClicked(MouseEvent event) {
		
	}

	// Get All Exercises
	private List<Exercise> getAllExercises() throws IOException {
		String json = getResponseBody("").string();

		return gson.fromJson(json, exerciseListType);
	}

	// Exercises by Name
	private List<Exercise> getByName(String name) throws IOException {
		name.toLowerCase();
		name.replaceAll(" ", "%20");

		String json = getResponseBody("/name/" + name).string();

		return gson.fromJson(json, exerciseListType);
	}

	// Exercises by BodyPart
	private List<Exercise> getByBodypart(String bodyPart) throws IOException {
		bodyPart.replaceAll(" ", "%20");

		String json = getResponseBody("/bodyPart/" + bodyPart).string();

		return gson.fromJson(json, exerciseListType);
	}

	// List of Bodyparts
	private List<String> getBodyParts() throws IOException {
		String json = getResponseBody("/bodyPartList").string();

		return gson.fromJson(json, stringListType);
	}

	// Exercises by Target
	private List<Exercise> getByTarget(String target) throws IOException {
		target.replaceAll(" ", "%20");

		String json = getResponseBody("/target/" + target).string();

		return gson.fromJson(json, exerciseListType);
	}

	// List of Targets
	private List<String> getTargets() throws IOException {
		String json = getResponseBody("/targetList").string();

		return gson.fromJson(json, stringListType);
	}

	// Exercises by Equipment
	private List<Exercise> getByEquipment(String equipment) throws IOException {
		equipment.replaceAll(" ", "%20");

		String json = getResponseBody("/equipment/" + equipment).string();

		return gson.fromJson(json, exerciseListType);
	}

	// List of Equipments
	private List<String> getEquipments() throws IOException {
		String json = getResponseBody("/equipmentList").string();

		return gson.fromJson(json, stringListType);
	}

	private ResponseBody getResponseBody(String parameter) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://exercisedb.p.rapidapi.com/exercises" + parameter).get()
				.addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "582862b3a3mshb1f23c0f20232c2p175ea4jsn3aa2ad592392").build();

		Response response = client.newCall(request).execute();

		ResponseBody responseBody = null;

		responseBody = response.body();

		return responseBody;
	}

	public ListView<Exercise> getRoot() {
		return root;
	}

}
