package nc.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nc.entity.FieldCell;
import nc.entity.Status;
import nc.handler.AddShipHandler;
import nc.handler.FireHandler;

public class GameController {
    public FieldCell[][] user;
    FieldCell[][] opponent;
    Stage mainStage;
    GridPane opponentPane;
    GridPane gamerPane;

    Integer userShips = 0, opponentShips = 0;

    private boolean isVertical;
    Button orientation;
    AddShipHandler addShipHandler;

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public void start() {
        createFields();
        userShips = 0;
        opponentShips = 0;

        FireHandler<ActionEvent> fireHandler = new FireHandler<>(user, opponent, mainStage, this);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                opponent[i][j].setOnAction(fireHandler);
            }
        }
    }

    private void createFields() {
        user = new FieldCell[10][10];
        opponent = new FieldCell[10][10];
        addShipHandler = new AddShipHandler<>(this);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                user[i][j] = new FieldCell(i, j);
                user[i][j].setOnAction(addShipHandler);
                opponent[i][j] = new FieldCell(i, j);
                gamerPane.add(user[i][j], i, j);
                gamerPane.add(opponent[i][j], i, j);
            }
        }
    }

    private void clearUserField(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                user[i][j].setShowStatus(Status.CLEAR);
            }
        }
        addShipHandler.setLength(0);
        addShipHandler.setCount(new Label("0"));
        for (int i = 0; i < 4; i++) {
            addShipCount[i].setText(Integer.toString(4 - i));
        }
        userShips = 0;
    }

    public void fillFieldAutomatically() {
        clearUserField();
        boolean flag;
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
                    flag = trySet(x, y, orientation, i);
                    if (!flag)
                        continue;
                    place(x, y, orientation, i);
                } while (!flag);
            }
        }
        do {
            int orientation = (int) (Math.random() * 2.0);
            int x = (int) (Math.random() * 8.0) + 1;
            int y = (int) (Math.random() * 8.0) + 1;
            flag = trySet(x, y, orientation, 2);
            if (!flag)
                continue;
            place(x, y, orientation, 2);
        } while (!flag);

        for (int i = 0; i < 4; i++) {
            do {
                int x = (int) (Math.random() * 9.0);
                int y = (int) (Math.random() * 9.0);
                flag = trySet(x, y, 0, 2);
                if (!flag)
                    continue;
                place(x, y, 0, 1);
            } while (!flag);
        }
        opponentShips = 10;
    }

    private void place(int x, int y, int orientation, int length) {
        switch (orientation) {
            case 0:
                for (int i = 0; i < length; i++) {
                    user[x][y + i].setTrueStatus(Status.UNBROKEN);
                }
                break;
            case 1:
                for (int i = 0; i < length; i++) {
                    user[x + i][y].setTrueStatus(Status.UNBROKEN);
                }
                break;
        }
    }

    private Boolean trySet(Integer x, Integer y, Integer orientation, Integer length) {
        switch (orientation) {
            case 0:
                if (y + length > 10)
                    return Boolean.FALSE;
                for (int i = 0; i < length; i++) {
                    if (!checkField(x, y + i))
                        return Boolean.FALSE;
                }
                break;
            case 1:
                if (x + length > 10)
                    return Boolean.FALSE;
                for (int i = 0; i < length; i++) {
                    if (!checkField(x + i, y)) {
                        return Boolean.FALSE;
                    }
                }
                break;
        }
        return Boolean.TRUE;
    }

    private Boolean checkField(int x, int y) {
        Boolean result = Boolean.TRUE;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i != 0 || j != 0) && isInField(x + i, y + j)) {
                    result = !opponent[x + i][y + j].getTrueStatus().equals(Status.UNBROKEN) && result;
                }
            }
        }

        return result;
    }

    private boolean isInField(int x, int y) {
        return (x < 10 && x >= 0 && y < 10 && y >= 0);
    }

    public void increaseUserShips() {
        userShips++;
    }

    public Button getOrientation() {
        return orientation;
    }
}
