package dad.fitnesslibrary.activity;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dad.fitnesslibrary.classes.Exercise;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
/**
 * Controlador de la tabla de ejercicios 
 *
 */
public class TableViewController implements Initializable {

	private ObjectProperty<Exercise> selectedExercise = new SimpleObjectProperty<>();

	@FXML
	private TableColumn<Exercise, String> idColumn;

	@FXML
	private TableColumn<Exercise, String> nameColumn;

	@FXML
	private TableColumn<Exercise, String> equipmentColumn;

	@FXML
	private TableColumn<Exercise, String> bodypartColumn;

	@FXML
	private TableColumn<Exercise, String> targetColumn;

	@FXML
	private TableView<Exercise> root;

	private static ListProperty<Exercise> exerciseList;

	private static ListProperty<Exercise> exerciseListAux;

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private final static Type exerciseListType = new TypeToken<List<Exercise>>() {
	}.getType();

	public TableViewController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TableViewExercises.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		exerciseList = new SimpleListProperty<>();
		exerciseListAux = new SimpleListProperty<>();

		idColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getId()));
		nameColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
		equipmentColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEquipment()));
		bodypartColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getBodyPart()));
		targetColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTarget()));
		getAllExercises();

		selectedExercise.bind(root.getSelectionModel().selectedItemProperty());

//		ejercicioController.EjercicioProperty().bind(selectedExercise);
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
/**
 * Cambia los valores mostrados en la tabla
 */
	public static void onBodyPartChanged(ObservableValue<? extends Boolean> obv, Boolean ov, Boolean nv, String s) {
//		if(nv == true)
//			exerciseList.stream().filter(exercise -> exercise.getBodyPart().equals(s)).collect(Collectors.toList());
//		else {
//			getAllExercises();
//		}
//			
//		exerciseListAux.stream().filter(exercise -> exercise.getBodyPart().equals(s)).collect(Collectors.toList());
//		if (nv) {
//			ArrayList<Exercise> ejerciciosSeleccionados = new ArrayList<Exercise>();
//			ArrayList<String> checboxesSeleccionados = MenuLeftController.bodyPartCheckBoxesUncheked();
//			if (checboxesSeleccionados.isEmpty())
//				exerciseList.addAll(exerciseListAux);
//			else {	
//			}

//			if (checboxesSeleccionados.isEmpty()) {
//				exerciseList.clear();
//				exerciseList.addAll(exerciseListAux);
//			} else {
//				
////				exerciseList.clear();
////				exerciseList.addAll(ejerciciosSeleccionados);
	}
	/**
	 * Cambia los valores mostrados en la tabla
	 */
	public static void onEquipmentChanged(ObservableValue<? extends Boolean> obv, Boolean ov, Boolean nv, String s) {
		if (nv) {
			ArrayList<Exercise> ejerciciosSeleccionados = new ArrayList<Exercise>();
			ArrayList<String> checboxesSeleccionados = MenuLeftController.equipmentCheckBoxesUncheked();
			for (Exercise e : exerciseListAux) {
				if (checboxesSeleccionados.contains(e.getTarget())) {
					ejerciciosSeleccionados.add(e);
				}
			}
			exerciseList.clear();
			exerciseList.addAll(ejerciciosSeleccionados);
		}
	}
	/**
	 * Cambia los valores mostrados en la tabla
	 */
	public static void onTargetCHKChanged(ObservableValue<? extends Boolean> obv, Boolean ov, Boolean nv,
			String targetString) {
		if (nv) {
			ArrayList<Exercise> ejerciciosSeleccionados = new ArrayList<Exercise>();
			ArrayList<String> checboxesSeleccionados = MenuLeftController.targetCheckBoxesUncheked();
			for (Exercise e : exerciseListAux) {
				if (checboxesSeleccionados.contains(e.getTarget())) {
					ejerciciosSeleccionados.add(e);
				}
			}
			exerciseList.clear();
			exerciseList.addAll(ejerciciosSeleccionados);
		}
	}

	// Exercises by Name (Adri)
	public void getByName(String name) throws IOException {
		name.toLowerCase();
		name.replaceAll(" ", "%20");

		getResponseBodyExercise("/name/" + name);
	}

//	// Exercises by BodyPart (Javi y Robertz)
//	public void getByBodypart(String bodyPart) throws IOException {
//		bodyPart.replaceAll(" ", "%20");
//
//		getResponseBodyExercise("/bodyPart/" + bodyPart);
//	}
//
//	// Exercises by Target (Javi y Robertz)
//	public void getByTarget(String target) throws IOException {
//		target.replaceAll(" ", "%20");
//
//		getResponseBodyExercise("/target/" + target);
//	}
//	
//	// Exercises by Equipment (Javi y Robertz)
//	public void getByEquipment(String equipment) throws IOException {
//		equipment.replaceAll(" ", "%20");
//
//		getResponseBodyExercise("/equipment/" + equipment);
//	}

	private void getResponseBodyExercise(String parameter) throws IOException {
		Task<List<Exercise>> rbTask = new Task<List<Exercise>>() {
			@Override
			protected List<Exercise> call() throws Exception {
				return gson.fromJson(backgroundCall(parameter), exerciseListType);
			}
		};

		rbTask.setOnSucceeded(e -> {
			try {
				root.getItems().clear();
				root.getItems().addAll(rbTask.get());
				exerciseList.bind(root.itemsProperty());
				exerciseListAux = new SimpleListProperty<>(exerciseList);
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
		});

		rbTask.setOnFailed(e -> {
			System.err.println(rbTask.getException());
		});

		Thread thread = new Thread(rbTask);
		thread.start();
	}

	private static String backgroundCall(String parameter) throws IOException {
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("https://exercisedb.p.rapidapi.com/exercises" + parameter).get()
				.addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
				// Secundaria: 6cecac0657msh67ad3a0c522aeccp1aa69ajsn2f5640fe0689
				// Principal(Esperar a marzo): 582862b3a3mshb1f23c0f20232c2p175ea4jsn3aa2ad592392
				.addHeader("x-rapidapi-key", "6cecac0657msh67ad3a0c522aeccp1aa69ajsn2f5640fe0689").build();

		Response response = client.newCall(request).execute();

		return response.body().string();
	}

	public TableView<Exercise> getRoot() {
		return root;
	}

	public final ObjectProperty<Exercise> selectedExerciseProperty() {
		return this.selectedExercise;
	}

	public final Exercise getSelectedExercise() {
		return this.selectedExerciseProperty().get();
	}

	public final void setSelectedExercise(final Exercise selectedExercise) {
		this.selectedExerciseProperty().set(selectedExercise);
	}
}
