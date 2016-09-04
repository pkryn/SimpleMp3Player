package eventHanler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Duration;
import sample.Controller;

/**
 * Created by 박경린 on 2016-08-25.
 */
public class BarEventControll implements ChangeListener<Duration>, Runnable{
    private Controller controller;

    public BarEventControll(Controller controller){
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.mediaPlayer.currentTimeProperty().addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
        double current = controller.mediaPlayer.getCurrentTime().toSeconds() % 60;
        double total = controller.mediaPlayer.getTotalDuration().toSeconds() % 60;
        double progress = controller.mediaPlayer.getCurrentTime().toSeconds() / controller.mediaPlayer.getTotalDuration().toSeconds();
        controller.setBarProgress(progress);
        controller.setTimeLabelText(
                (int)controller.mediaPlayer.getCurrentTime().toMinutes() + ":" + (int)current + "/" + (int)controller.mediaPlayer.getTotalDuration().toMinutes() + ":" + (int)total
        );
    }

}
