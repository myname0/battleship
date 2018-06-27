package nc.entity;


import javafx.scene.control.Button;

public class FieldCell extends Button {
    private int x;
    private int y;
    private Status showStatus;
    private Status trueStatus;

    public FieldCell(int x, int y){
        super();
        this.x = x;
        this.y = y;
        trueStatus = Status.CLEAR;
        showStatus = Status.CLEAR;
        setStatusStyle(showStatus);
        this.setPrefSize(30,30);
    }

    public FieldCell(Status showStatus, String text){
        super(text);
        this.showStatus = showStatus;
        x = -1;
        y = -1;
        setStatusStyle(showStatus);
        this.setPrefSize(30,30);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setTrueStatus(Status trueStatus) {
        this.trueStatus = trueStatus;
    }

    public Status getTrueStatus() {
        return trueStatus;
    }

    public Status getShowStatus(){
        return showStatus;
    }

    public void setShowStatus(Status status){
        this.showStatus = status;
        trueStatus = status;
        setStatusStyle(status);
    }

    private void setStatusStyle(Status status){
        switch (status){
            case INJURED:
                setStyle("-fx-background-color: #ff0000;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-color: black;");
                setOnAction(null);
                break;
            case MISSED:
                setStyle("-fx-background-color: gray;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-color: black;");
                setDisable(true);
                break;
            case UNBROKEN:
                setStyle("-fx-background-color: #00ff3b;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-color: black;");
                break;
            case CLEAR:
                setStyle("-fx-background-color: #0be4ff;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-color: black;");
                break;
            case KILLED:
                setStyle("-fx-background-color: #ff0000;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-color: black;");
                setOnAction(null);
        }
    }
}
