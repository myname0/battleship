package nc.controller;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nc.entity.FieldCell;
import nc.entity.Status;
import nc.handler.AddShipHandler;
import nc.handler.FireHandler;

public class GameController {
    private FieldCell[][] user;
    private FieldCell[][] opponent;
    private Stage mainStage;
    private GridPane opponentPane;
    private GridPane gamerPane;
    private int[] addShipCount = new int[]{4, 3, 2, 1};
    private boolean isVertical;
    private AddShipHandler addShipHandler;

    GameController(GridPane gamerPane, GridPane opponentPane) {
        this.gamerPane = gamerPane;
        this.opponentPane = opponentPane;
        init();
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public FieldCell[][] getUser() {
        return user;
    }

    public int[] getAddShipCount() {
        return addShipCount;
    }

    public AddShipHandler getAddShipHandler() {
        return addShipHandler;
    }

    public void start() {
        FireHandler<ActionEvent> fireHandler = new FireHandler<>(user, opponent, mainStage, this);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                opponent[i][j].setOnAction(fireHandler);
            }
        }
    }

    private void init() {
        user = new FieldCell[10][10];
        opponent = new FieldCell[10][10];
        addShipHandler = new AddShipHandler(this);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                user[i][j] = new FieldCell(i, j);
                user[i][j].setOnAction(addShipHandler);
                opponent[i][j] = new FieldCell(i, j);
                gamerPane.add(user[i][j], i, j);
                opponentPane.add(opponent[i][j], i, j);
            }
        }
    }

    private void clearUserField() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                user[i][j].setShowStatus(Status.CLEAR);
                user[i][j].setTrueStatus(Status.CLEAR);
            }
        }
    }

    public void fillFieldAutomatically() {
        clearUserField();
        boolean isPossibleToSet;

        for (int i = 4; i >= 2; i--) {
            for (int j = 0; j < 4 - i + 1; j++) {
                if (i == 2 && j == 2)
                    continue;
                do {
                    int orientation = (int) (Math.random() * 2.0);
                    int side = (int) (Math.random() * 2.0);
                    int pos = (int) (Math.random() * (11.0 - i));
                    int x = pos * orientation + 9 * side * Math.abs(orientation - 1);
                    int y = pos * Math.abs(orientation - 1) + 9 * side * orientation;
                    isPossibleToSet = trySet(x, y, orientation, i);
                    if (!isPossibleToSet)
                        continue;
                    place(x, y, orientation, i);
                } while (!isPossibleToSet);
            }
        }

        do {
            int orientation = (int) (Math.random() * 2.0);
            int x = (int) (Math.random() * 8.0) + 1;
            int y = (int) (Math.random() * 8.0) + 1;
            isPossibleToSet = trySet(x, y, orientation, 2);
            if (!isPossibleToSet)
                continue;
            place(x, y, orientation, 2);
        } while (!isPossibleToSet);

        for (int i = 0; i < 4; i++) {
            do {
                int x = (int) (Math.random() * 9.0);
                int y = (int) (Math.random() * 9.0);
                isPossibleToSet = trySet(x, y, 0, 2);
                if (!isPossibleToSet)
                    continue;
                place(x, y, 0, 1);
            } while (!isPossibleToSet);
        }
    }

    private void place(int x, int y, int orientation, int length) {
        switch (orientation) {
            case 0:
                for (int i = 0; i < length; i++) {
                    user[x][y + i].setTrueStatus(Status.UNBROKEN);
                    user[x][y + i].setShowStatus(Status.UNBROKEN);
                }
                break;
            case 1:
                for (int i = 0; i < length; i++) {
                    user[x + i][y].setTrueStatus(Status.UNBROKEN);
                    user[x + i][y].setShowStatus(Status.UNBROKEN);
                }
                break;
        }
    }

    private boolean trySet(int x, int y, int orientation, int length) {
        switch (orientation) {
            case 0:
                if (y + length > 10)
                    return false;
                for (int i = 0; i < length; i++) {
                    if (!checkField(x, y + i))
                        return false;
                }
                break;
            case 1:
                if (x + length > 10)
                    return false;
                for (int i = 0; i < length; i++) {
                    if (!checkField(x + i, y)) {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    private boolean checkField(int x, int y) {
        boolean result = true;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i != 0 || j != 0) && isInField(x + i, y + j)) {
                    result = !user[x + i][y + j].getTrueStatus().equals(Status.UNBROKEN) && result;
                }
            }
        }
        return result;
    }

    private boolean isInField(int x, int y) {
        return (x < 10 && x >= 0 && y < 10 && y >= 0);
    }
}
