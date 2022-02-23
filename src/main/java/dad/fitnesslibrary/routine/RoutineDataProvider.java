package dad.fitnesslibrary.routine;

import java.util.ArrayList;
import java.util.List;

import dad.fitnesslibrary.classes.Exercise;
import dad.fitnesslibrary.classes.Routine;


public class RoutineDataProvider {

	public static List<Routine> getRoutines(Routine routine) {
		List<Routine> exercises = new ArrayList<>();
		for (int i = 1; i <= exercises.size(); i++) {
			routine.getExercisesList();
			exercises.add(routine);
		}
		return exercises;
	}

}
