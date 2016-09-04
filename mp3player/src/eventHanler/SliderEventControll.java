package eventHanler;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sample.Controller;

/**
 * Created by 박경린 on 2016-08-25.
 */
public class SliderEventControll implements ChangeListener<Number> {
    private Controller controller;

    public SliderEventControll(Controller controller){
        this.controller = controller;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        controller.setVolume();
    }
}
