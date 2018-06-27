package nc.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import nc.handler.AddShipHandler;

import java.awt.*;


public class UIController  {
    private Label ShipCounts;
    private  GameController controller;
    private AddShipHandler addShipHandler;

    @FXML
    private GridPane opponentPane;

    @FXML
    private GridPane gamerPane;

    @FXML
    public void HandleStartButton(){
        controller.start();
    }

    @FXML
    public void HandleExitButton(){

    }

    @FXML
    public void HandleReverse(){
        controller.setVertical(!controller.isVertical());
    }

    @FXML
    public void HandleAddShip(){

        addShipHandler.setLength(Integer.parseInt(button.getText()));
        addShipHandler.setCount(addShipCount[i]);
    }

    @FXML
    public void HandleAutoGenerate(){
        controller.fillFieldAutomatically();
    }

}
