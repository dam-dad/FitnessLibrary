package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import dad.fitnesslibrary.classes.Exercise;
import dad.fitnesslibrary.classes.TipoComboBox;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ListViewController implements Initializable {

	@FXML
	private ListView<Exercise> root;

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private Type exerciseListType = new TypeToken<List<Exercise>>() {
	}.getType();

	private Type stringListType = new TypeToken<List<String>>() {
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
		getAllExercises();
	}

	@FXML
	void onRootDoubleClicked(MouseEvent event) {
		
	}

	// Get All Exercises
	public void getAllExercises() {
		try {
			getResponseBodyExercise("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Exercises by Name (Adri)
	public void getByName(String name) throws IOException {
		name.toLowerCase();
		name.replaceAll(" ", "%20");

		getResponseBodyExercise("/name/" + name);
	}

	// Exercises by BodyPart (Javi y Robertz)
	public void getByBodypart(String bodyPart) throws IOException {
		bodyPart.replaceAll(" ", "%20");

		getResponseBodyExercise("/bodyPart/" + bodyPart);
	}

	// List of Bodyparts (Robertz)
	public void getBodyParts() throws IOException {
		getResponseBodyString("/bodyPartList", TipoComboBox.BODYPART);
	}

	// Exercises by Target (Javi y Robertz)
	public void getByTarget(String target) throws IOException {
		target.replaceAll(" ", "%20");

		getResponseBodyExercise("/target/" + target);
	}

	// List of Targets (Robertz)
	public void getTargets() throws IOException {
		getResponseBodyString("/targetList", TipoComboBox.TARGET);
	}

	// Exercises by Equipment (Javi y Robertz)
	public void getByEquipment(String equipment) throws IOException {
		equipment.replaceAll(" ", "%20");

		getResponseBodyExercise("/equipment/" + equipment);
	}

	// List of Equipments ()
	public void getEquipments() throws IOException {
		getResponseBodyString("/equipmentList",TipoComboBox.EQUIPMENT);
	}

	private void getResponseBodyExercise(String parameter) throws IOException {		
		Task<List<Exercise>> rbTask = new Task<List<Exercise>>() {
			@Override
			protected List<Exercise> call() throws Exception {
				System.out.println("Ha entrado a el hilo");
				
				return gson.fromJson(backgroundCall(parameter), exerciseListType);
			}
		};

		rbTask.setOnSucceeded(e -> {
			try {
				root.getItems().addAll(rbTask.get());
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		});
		
		rbTask.setOnFailed(e -> {
			System.err.println(rbTask.getException());
		});
		
		new Thread(rbTask).start();
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
				rbTask.get();
				switch (tipo) {
				case BODYPART:
					
				break;
					
				case EQUIPMENT:
					
				break;
					
				case TARGET:
					
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

	public ListView<Exercise> getRoot() {
		return root;
	}

}
