package nc.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class UIController {
    @FXML
    private GridPane opponentPane;

    @FXML
    private GridPane gamerPane;

    private GameController controller;

    @FXML
    public void HandleStartButton() {
        controller = new GameController(gamerPane, opponentPane); //init after opponent choose
        controller.start();
    }

    @FXML
    public void HandleExitButton() {

    }

    @FXML
    public void HandleReverse() {
        controller.setVertical(!controller.isVertical());
    }

    @FXML
    public void HandleAddShip(ActionEvent event) {
        Button button = (Button) event.getSource();
        int shipLength = Integer.parseInt(button.getText().substring(0, 1));
        controller.getAddShipHandler().setLength(shipLength);
        controller.getAddShipHandler().setCount(controller.getAddShipCount()[shipLength - 1]);

    }

    @FXML
    public void HandleAutoGenerate() {
        controller.fillFieldAutomatically();
    }

}
