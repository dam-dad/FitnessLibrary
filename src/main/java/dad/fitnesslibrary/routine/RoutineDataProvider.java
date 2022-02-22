package dad.fitnesslibrary.routine;

import java.util.ArrayList;
import java.util.List;

import dad.fitnesslibrary.classes.Exercise;
import dad.fitnesslibrary.classes.Routine;


public class RoutineDataProvider {

	public static List<Exercise> getRoutines() {
		List<Exercise> exercises = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Exercise exercise = new Exercise();
			exercise.setId("Id-" + i);
			exercise.setName("Name-" + i);
			exercise.setEquipment("Equipment-" + i);
			exercise.setBodyPart("BodyPart-" + i);
			exercise.setTarget("Target-" + i);
			exercises.add(exercise);
		}
		return exercises;
	}

}
