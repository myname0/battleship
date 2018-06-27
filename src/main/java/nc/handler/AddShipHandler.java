package nc.handler;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import nc.controller.GameController;
import nc.entity.FieldCell;
import nc.entity.Status;

public class AddShipHandler <T extends Event> implements EventHandler {
    Integer length;
    Label count;
    GameController controller;

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setCount(Label count) {
        this.count = count;
    }

    public AddShipHandler(GameController controller){
        this.controller = controller;
        this.length = 0;
        this.count = new Label("0");
    }
    @Override
    public void handle(Event event) {
        FieldCell button = ((FieldCell) event.getSource());
        if (!trySet(button.getX(), button.getY()) || count.getText().equals("0"))
            return;
        for (int i = 0; i < length; i++) {
            if(controller.getOrientation().getText().equals("H")){
                controller.user[button.getX()][button.getY() + i].setShowStatus(Status.UNBROKEN);
            }else {
                controller.user[button.getX() + i][button.getY()].setShowStatus(Status.UNBROKEN);
            }
        }
        Integer k = Integer.parseInt( count.getText()) - 1;
        count.setText(k.toString());
        controller.increaseUserShips();
    }

    private Boolean trySet(int x, int y){
        if (controller.getOrientation().getText().equals("H")){
            return trySetHorizontal(x, y);
        }
        return trySetVertical(x, y);
    }

    private Boolean trySetHorizontal(int x, int y){
        if (y + length > 10)
            return Boolean.FALSE;
        for (int i = 0; i < length; i++) {
            if (!checkField(x, y + i)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
    private Boolean trySetVertical(int x, int y){
        if (x + length > 10)
            return Boolean.FALSE;
        for (int i = 0; i < length; i++) {
            if (!checkField(x + i, y)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    private Boolean checkField(int x, int y){
        Boolean result = Boolean.TRUE;
        if (x > 0)
            result = !controller.user[x - 1][y].getShowStatus().equals(Status.UNBROKEN);
        if (y > 0)
            result = !controller.user[x][y - 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x < 9)
            result = !controller.user[x + 1][y].getShowStatus().equals(Status.UNBROKEN) && result;
        if (y < 9)
            result = !controller.user[x][y + 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x > 0 && y > 0)
            result = !controller.user[x - 1][y - 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x > 0 && y < 9)
            result = !controller.user[x - 1][y + 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x < 9 && y < 9)
            result = !controller.user[x + 1][y + 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x < 9 && y > 0)
            result = !controller.user[x + 1][y - 1].getShowStatus().equals(Status.UNBROKEN) && result;
        return result;
    }

}
