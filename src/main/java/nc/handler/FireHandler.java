package nc.handler;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import nc.controller.GameController;
import nc.entity.FieldCell;


public class FireHandler <T extends Event> implements EventHandler {
    FieldCell[][] user, opponent;

    Stage stage;
    GameController controller;

    public FireHandler(FieldCell user[][], FieldCell opponent[][], Stage stage, GameController controller) {
        this.user = user;
        this.opponent = opponent;
        this.stage = stage;
        this.controller = controller;

    }

    @Override
    public void handle(Event event) {

    }
}
