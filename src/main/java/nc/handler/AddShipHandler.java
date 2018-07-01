package nc.handler;

import javafx.event.Event;
import javafx.event.EventHandler;
import nc.controller.GameController;
import nc.entity.FieldCell;
import nc.entity.Status;

public class AddShipHandler<T extends Event> implements EventHandler {
    private int length;
    private int count;
    private GameController controller;

    public void setLength(Integer length) {
        this.length = length;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AddShipHandler(GameController controller) {
        this.controller = controller;
        length = 0;
        count = 0;
    }

    @Override
    public void handle(Event event) {
        FieldCell button = ((FieldCell) event.getSource());
        if (!trySet(button.getX(), button.getY()) || count == 0)
            return;
        for (int i = 0; i < length; i++) {
            if (controller.isVertical()) {
                controller.getUser()[button.getX() + i][button.getY()].setShowStatus(Status.UNBROKEN);
            } else {
                controller.getUser()[button.getX()][button.getY() + i].setShowStatus(Status.UNBROKEN);
            }
        }
        count--;
    }

    private boolean trySet(int x, int y) {
        if (controller.isVertical()) {
            return trySetVertical(x, y);

        } else {
            return trySetHorizontal(x, y);
        }
    }

    private boolean trySetHorizontal(int x, int y) {
        if (y + length > 10)
            return false;
        for (int i = 0; i < length; i++) {
            if (!checkField(x, y + i)) {
                return false;
            }
        }
        return true;
    }

    private boolean trySetVertical(int x, int y) {
        if (x + length > 10)
            return false;
        for (int i = 0; i < length; i++) {
            if (!checkField(x + i, y)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkField(int x, int y) {
        boolean result = true;
        if (x > 0)
            result = !controller.getUser()[x - 1][y].getShowStatus().equals(Status.UNBROKEN);
        if (y > 0)
            result = !controller.getUser()[x][y - 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x < 9)
            result = !controller.getUser()[x + 1][y].getShowStatus().equals(Status.UNBROKEN) && result;
        if (y < 9)
            result = !controller.getUser()[x][y + 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x > 0 && y > 0)
            result = !controller.getUser()[x - 1][y - 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x > 0 && y < 9)
            result = !controller.getUser()[x - 1][y + 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x < 9 && y < 9)
            result = !controller.getUser()[x + 1][y + 1].getShowStatus().equals(Status.UNBROKEN) && result;
        if (x < 9 && y > 0)
            result = !controller.getUser()[x + 1][y - 1].getShowStatus().equals(Status.UNBROKEN) && result;
        return result;
    }

}
