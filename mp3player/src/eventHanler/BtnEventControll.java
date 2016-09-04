package eventHanler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Controller;
import sample.PlayStyle;

import java.io.File;


/**
 * Created by 박경린 on 2016-08-25.
 */
public class BtnEventControll implements EventHandler<ActionEvent>{

    private Controller controller;
    private PlayStyle playStyle;
    private Media hit;
    private int start;
    private File file;
    private Stage stage;
    private FileChooser fileChooser;

    public BtnEventControll(Controller controller){
        this.controller = controller;
        start = 0;
        playStyle = playStyle.STANDARD_PLAY;
        fileChooser = new FileChooser();
        fileChooser.setTitle("File Open");
    }

    @Override
    public void handle(ActionEvent event) {
        Button o = (Button)event.getSource();
        switch (o.getId()){
            case "btnPlayStyle":
                switch (playStyle) {
                    case STANDARD_PLAY:
                        controller.setBtnPlayStyleText("Re-play");
                        playStyle = PlayStyle.RE_PLAY;
                        break;
                    case RE_PLAY:
                        controller.setBtnPlayStyleText("Random");
                        playStyle = PlayStyle.RANDOM_PLAY;
                        break;
                    case RANDOM_PLAY:
                        controller.setBtnPlayStyleText("Standard");
                        playStyle = PlayStyle.STANDARD_PLAY;
                        break;
                }
                break;
            case "btnPlay":
                if (controller.getFinalList() != 0) {
                    if (start == 0){
                        start = 1;
                        int nowPlaying = controller.getSelectedIndex();
                        hit = new Media((String)controller.pathList.get(nowPlaying));
                        controller.setNameText((String)controller.songList.get(nowPlaying));
                        controller.mediaPlayer = new MediaPlayer(hit);
                        controller.setting();
                    }
                    controller.mediaPlayer.play();
                }
                break;
            case "btnPause":
                controller.mediaPlayer.pause();
                break;
            case "btnStop":
                controller.mediaPlayer.stop();
                break;
            case "btnNext":
                controller.mediaPlayer.stop();
                controller.setBarProgress(0.0);
                if (playStyle == PlayStyle.RANDOM_PLAY){
                    int i = (int)(Math.random()*controller.getFinalList());
                    hit = new Media((String)controller.pathList.get(i));
                    controller.setNameText((String)controller.songList.get(i));
                    controller.mediaPlayer = new MediaPlayer(hit);
                    controller.setNowPlaying(i);
                } else{
                    if(controller.getNowPlaying() == controller.getFinalList()-1)
                        controller.setNowPlaying(0);
                    else
                        controller.setNowPlaying(controller.getNowPlaying()+1);
                    hit = new Media((String)controller.pathList.get(controller.getNowPlaying()));
                    controller.setNameText((String)controller.songList.get(controller.getNowPlaying()));
                    controller.mediaPlayer = new MediaPlayer(hit);
                }
                controller.mediaPlayer.play();
                controller.setting();
                break;
            case "btnLoad":
                file = fileChooser.showOpenDialog(stage);
                String filePath = file.getPath();
                controller.loadFile(filePath);
                break;
            case "btnDelete":
                int delete = controller.getSelectedIndex();
                controller.pathList.remove(delete);
                controller.songList.remove(delete);
                controller.subFinalList();
                break;
        }
    }

}
