package eventHanler;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import sample.Controller;

import java.io.File;

/**
 * Created by 박경린 on 2016-08-25.
 */
public class DragEventControll implements EventHandler<DragEvent>{

    Controller controller;

    public DragEventControll(Controller controller){
        this.controller = controller;
    }

    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (event.getEventType().getName() == "DRAG_OVER") {
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        }
        else if (event.getEventType().getName() == "DRAG_DROPPED") {
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    String filePath = file.getAbsolutePath();
                    controller.loadFile(filePath);
                }
            }
            event.setDropCompleted(success);
            event.consume();
        }
    }
}
