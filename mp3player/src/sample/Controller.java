package sample;

import eventHanler.BarEventControll;
import eventHanler.BtnEventControll;
import eventHanler.DragEventControll;
import eventHanler.SliderEventControll;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private Label name;
    @FXML private Button btnLoad;
    @FXML private Button btnPlay;
    @FXML private Button btnPause;
    @FXML private Button btnStop;
    @FXML private Button btnPlayStyle;
    @FXML private ProgressBar bar;
    @FXML private Label timeLabel;
    @FXML private ListView list;
    @FXML private Button btnlistdelete;
    @FXML private Button btnNext;
    @FXML private Slider soundSlider;

    private PlayStyle playStyle;
    private String encodeFilePath;
    private Media hit;
    public MediaPlayer mediaPlayer;
    private int finalList = 0;
    private int nowPlaying = 0;
    private BtnEventControll btnEventControll;
    private DragEventControll dragEventControll;
    private BarEventControll barEventControll;
    private SliderEventControll sliderEventControll;

    public ObservableList songList = FXCollections.observableArrayList();
    public ObservableList pathList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sliderEventControll = new SliderEventControll(this);
        barEventControll = new BarEventControll(this);
        dragEventControll = new DragEventControll(this);
        btnEventControll = new BtnEventControll(this);
        name.setText("no media");

        playStyle = PlayStyle.STANDARD_PLAY;
        btnPlayStyle.setText("Standard");

        btnPlayStyle.setOnAction(btnEventControll);
        btnLoad.setOnAction(btnEventControll);
        btnPlay.setOnAction(btnEventControll);
        btnNext.setOnAction(btnEventControll);
        btnPause.setOnAction(btnEventControll);
        btnStop.setOnAction(btnEventControll);
        btnlistdelete.setOnAction(btnEventControll);


        setTimeLabelText("No media");

        list.setItems(songList);

        soundSlider.setValue(50.0);

        list.setOnDragOver(dragEventControll);
        list.setOnDragDropped(dragEventControll);

    }

    public void loadFile(String filePath){
        int k = 0;

        char[] c = filePath.toCharArray();
        for (int i = 0; i< filePath.length(); i++){
            if (c[i] == '\\') {
                c[i] = '/';
                k  = i;
            }
        }


        filePath = new String(c);
        songList.add(filePath.substring(k+1, filePath.length()));
        //list.setItems(FXCollections.observableArrayList(filePath.substring(k+1, filePath.length())));

        try{
            encodeFilePath = URLEncoder.encode(filePath, "UTF-8").replace("+", "%20").replace("%2f", "/");
        } catch (UnsupportedEncodingException e){
            throw new AssertionError("UTF-8 is unknown");
        }
        encodeFilePath = "file:///" + encodeFilePath;

        pathList.add(encodeFilePath);
        finalList++;

    }


    public void setBtnPlayStyleText(String string){
        btnPlayStyle.setText(string);
    }

    public int getFinalList(){
        return finalList;
    }

    public int getSelectedIndex(){
        return list.getSelectionModel().getSelectedIndex();
    }

    public void setNameText(String string){
        name.setText(string);
    }

    public void setTimeLabelText(String string){
        timeLabel.setText(string);
    }

    public void subFinalList(){
        finalList--;
    }

    public void setNowPlaying(int i){
        nowPlaying = i;
    }

    public void setBarProgress(double d){
        bar.setProgress(d);
    }

    public int getNowPlaying(){
        return nowPlaying;
    }

    public void setVolume(){
        mediaPlayer.setVolume(soundSlider.getValue()/100);
    }

    public void setting(){

        soundSlider.valueProperty().addListener(sliderEventControll);
        mediaPlayer.setVolume(soundSlider.getValue() / 100.0);

        mediaPlayer.setOnReady(barEventControll);

        mediaPlayer.setOnEndOfMedia(()->{
            bar.setProgress(0.0);
            switch (playStyle) {
                case RANDOM_PLAY:
                    int i = (int) (Math.random() * finalList);
                    hit = new Media((String) pathList.get(i));
                    name.setText((String) songList.get(i));
                    mediaPlayer = new MediaPlayer(hit);
                    nowPlaying = i;
                    break;
                case STANDARD_PLAY:
                    if (nowPlaying == finalList - 1)
                        nowPlaying = 0;
                    else
                        nowPlaying++;
                    hit = new Media((String) pathList.get(nowPlaying));
                    name.setText((String) songList.get(nowPlaying));
                    mediaPlayer = new MediaPlayer(hit);
                    break;
                case RE_PLAY:
                    mediaPlayer.stop();
                    break;
            }
            setting();
            mediaPlayer.play();
        });
    }


}
