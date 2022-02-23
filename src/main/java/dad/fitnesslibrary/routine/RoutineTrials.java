package dad.fitnesslibrary.routine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class RoutineTrials {

	public static void main(String[] args) {
//		new Timer().schedule(new TimerTask() {
//	        @Override
//	        public void run() {
//	            System.out.println("ping");
//	        }
//	    }, 0, 1000);

		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("this is called every 1 seconds on UI thread");
			}
		}));
		fiveSecondsWonder.setOnFinished(e -> {
			System.out.println("it has finished");
		});
		fiveSecondsWonder.setAutoReverse(true);
		fiveSecondsWonder.setCycleCount(5);
		fiveSecondsWonder.play();

	}

}
