package ao.jedp.colormanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Label lbColorCode;

    @FXML
    private Label lbBlue;

    @FXML
    private Label lbGreen;

    @FXML
    private Label lbRed;

    @FXML
    private Pane paneColor;

    @FXML
    private Slider slBlue;

    @FXML
    private Slider slGreen;

    @FXML
    private Slider slRed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colorPicker.setOnAction(event -> {
            var color = colorPicker.getValue().toString().substring(2, 8);
            var r = convertScale255(colorPicker.getValue().getRed());
            var g = convertScale255(colorPicker.getValue().getGreen());
            var b = convertScale255(colorPicker.getValue().getBlue());
            updatePaneColor("#" + color, r, g, b);
        });

        slRed.valueProperty().addListener((observableValue, number, t1) ->
                updatePaneColor("rgb(" + t1.intValue() + "," + (int) slGreen.getValue() + "," +
                        (int) slBlue.getValue() + ")", t1.intValue(), (int) slGreen.getValue(), (int) slBlue.getValue())
        );

        slGreen.valueProperty().addListener((observableValue, number, t1) ->
                updatePaneColor("rgb(" + (int) slRed.getValue() + "," + t1.intValue() + "," +
                        (int) slBlue.getValue() + ")", (int) slRed.getValue(), t1.intValue(), (int) slBlue.getValue())
        );

        slBlue.valueProperty().addListener((observableValue, number, t1) ->
                updatePaneColor("rgb(" + (int) slRed.getValue() + "," + (int) slGreen.getValue() + "," +
                        t1.intValue() + ")", (int) slRed.getValue(), (int) slGreen.getValue(), t1.intValue())
        );
    }

    @FXML
    protected void getColorHexCode(ActionEvent event) {
        var color = ((Button) event.getSource()).getText().substring(1, 7);
        var r = Integer.parseInt(color.substring(0, 2), 16);
        var g = Integer.parseInt(color.substring(2, 4), 16);
        var b = Integer.parseInt(color.substring(4, 6), 16);
        updatePaneColor("#" + color, r, g, b);

        colorPicker.setValue(new Color(convertScale1(r), convertScale1(g), convertScale1(b), 1));
    }

    private int convertScale255(double value) {
        var percent = value * 100;
        return (int) ((percent * 255) / 100);
    }

    private float convertScale1(int value) {
        var percent = (float)((value * 100) / 255);
        return (percent / 100);
    }

    private void updatePaneColor(String color, int r, int g, int b) {
        paneColor.setStyle("-fx-background-color: " + color);
        lbRed.setText(String.valueOf(r));
        lbGreen.setText(String.valueOf(g));
        lbBlue.setText(String.valueOf(b));

        slRed.setValue(r);
        slGreen.setValue(g);
        slBlue.setValue(b);

        lbColorCode.setText(GFG.convertRGBtoHex(r, g, b));
    }

    @FXML
    protected void copyValue(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(lbColorCode.getText());
        clipboard.setContent(content);
    }
}