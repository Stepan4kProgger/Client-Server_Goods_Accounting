package res;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class onDeleteAlert extends Alert {
    private static final AlertType alertType = AlertType.CONFIRMATION;
    private static final String contentText = "Отменить данное действие будет невозможно";

    public onDeleteAlert(ButtonType... buttons) {
        super(alertType, contentText, buttons);
        setHeaderText("Вы уверенны, что хотите удалить выбранную запись?");
        setTitle("Внимание");
    }
}