package dad.fitnesslibrary.classes;

public class ExerciseTime extends Exercise {

	private int minutos;
	
	private int segundos;
	
	private Exercise exercise;


	public ExerciseTime() {
		
	}
	
	public ExerciseTime(Exercise exercise, int minutos, int segundos) {
		super(exercise.getBodyPart(), exercise.getEquipment(), exercise.getGifUrl(), exercise.getId(), exercise.getName(), exercise.getTarget());
		this.minutos = minutos;
		this.segundos =  segundos;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}
	
	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
}
