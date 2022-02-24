package dad.fitnesslibrary.routine;

import java.util.List;

import dad.fitnesslibrary.classes.ExerciseTime;
import dad.fitnesslibrary.classes.Routine;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class RoutineJson {

	private String name;
	private List<ExerciseTime> exercises;
	
	public RoutineJson() {
		// TODO Auto-generated constructor stub
	}

	public RoutineJson(String name, List<ExerciseTime> exercises) {
		super();
		this.name = name;
		this.exercises = exercises;
	}

	public static RoutineJson fromRoutinetoJson(Routine routine) {
		return new RoutineJson(routine.getName(), routine.getExercisesList());
	}
	
	public static Routine fromJsontoRoutine(RoutineJson routineJson) {
		return new Routine(new SimpleStringProperty(routineJson.getName()), new SimpleListProperty<>(FXCollections.observableArrayList(routineJson.getExercises())));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ExerciseTime> getExercises() {
		return exercises;
	}

	public void setExercises(List<ExerciseTime> exercises) {
		this.exercises = exercises;
	}


}
