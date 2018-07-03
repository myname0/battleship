package nc.handler;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import nc.controller.GameController;
import nc.entity.FieldCell;
import nc.entity.Status;


public class FireHandler<T extends Event> implements EventHandler {
    FieldCell[][] user, opponent;
    int countOpponentShips = 10;
    Stage stage;
    GameController controller;

    public FireHandler(FieldCell user[][], FieldCell cpu[][], Stage stage, GameController main) {
        this.user = user;
        this.opponent = cpu;
        this.stage = stage;
        this.controller = main;
    }

    @Override
    public void handle(Event event) {
        FieldCell cell = (FieldCell) event.getSource();
        switch (cell.getTrueStatus()) {
            case CLEAR:
                cell.setShowStatus(Status.MISSED);
                cell.setDisable(true);
                //       makeAMove(); // TODO: 28.06.2018 send to server
                break;
            case UNBROKEN:
                cell.setShowStatus(Status.INJURED);
                if (checkKilled(cell.getX(), cell.getY(), opponent)) {
                    countOpponentShips--;
                    killShip(cell.getX(), cell.getY(), opponent);
                }
                if (countOpponentShips == 0) {
                    showResult("You won!");
                }
                // TODO: 28.06.2018 send to server
        }
    }

    private Boolean checkKilled(int x, int y, FieldCell[][] field) {
        int i = x;
        while (i < 10) {
            if (field[i][y].getTrueStatus().equals(Status.UNBROKEN)) {
                return false;
            }
            if (field[i][y].getShowStatus().equals(Status.MISSED) || field[i][y].getShowStatus().equals(Status.CLEAR)) {
                break;

            }
            ++i;
        }
        i = x;
        while (i >= 0) {
            if (field[i][y].getTrueStatus().equals(Status.UNBROKEN)) {
                return false;
            }
            if (field[i][y].getShowStatus().equals(Status.MISSED) || field[i][y].getShowStatus().equals(Status.CLEAR)) {
                break;
            }
            --i;
        }
        i = y;
        while (i <= 9) {
            if (field[x][i].getTrueStatus().equals(Status.UNBROKEN)) {
                return false;
            }
            if (field[x][i].getShowStatus().equals(Status.MISSED) || field[x][i].getShowStatus().equals(Status.CLEAR)) {
                break;
            }
            ++i;
        }
        i = y;
        while (i > -1) {
            if (field[x][i].getTrueStatus().equals(Status.UNBROKEN)) {
                return false;
            }
            if (field[x][i].getShowStatus().equals(Status.MISSED) || field[x][i].getShowStatus().equals(Status.CLEAR)) {
                break;
            }
            --i;
        }
        return true;
    }

    private int killShip(int x, int y, FieldCell[][] field) {
        killCell(x, y, field);
        int result = 1;
        int i = x;
        while (isInField(++i, y) && field[i][y].getShowStatus().equals(Status.INJURED)) {
            killCell(i, y, field);
            result++;
        }
        i = x;
        while (isInField(--i, y) && field[i][y].getShowStatus().equals(Status.INJURED)) {
            killCell(i, y, field);
            result++;
        }
        i = y;
        while (isInField(x, ++i) && field[x][i].getShowStatus().equals(Status.INJURED)) {
            killCell(x, i, field);
            result++;
        }
        i = y;
        while (isInField(x, --i) && field[x][i].getShowStatus().equals(Status.INJURED)) {
            killCell(x, i, field);
            result++;
        }
        return result;
    }

    private void killCell(int x, int y, FieldCell[][] field) {
        field[x][y].setShowStatus(Status.KILLED);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0)
                    continue;
                if (isInField(x + i, y + j) && field[x + i][y + j].getShowStatus().equals(Status.CLEAR)) {
                    field[x + i][y + j].setShowStatus(Status.MISSED);
                }
            }
        }
    }

    private boolean isInField(int x, int y) {
        return (x < 10 && x >= 0 && y < 10 && y >= 0);
    }

    private void showResult(String message) {
        Alert gameResult = new Alert(Alert.AlertType.CONFIRMATION);
        gameResult.setTitle(message);
        gameResult.setHeaderText(null);
        gameResult.setContentText("Do you want to continue?");
        if (gameResult.showAndWait().get() == ButtonType.OK) {
            controller.start();
        } else {
            stage.close();
        }
    }
}

