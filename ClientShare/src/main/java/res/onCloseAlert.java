package res;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class onCloseAlert extends Alert {
    private static final AlertType alertType = AlertType.CONFIRMATION;
    private static final String contentText = "При запуске вам придётся войти в учётную запись повторно";

    public onCloseAlert(ButtonType... buttons) {
        super(alertType, contentText, buttons);
        setHeaderText("Вы уверенны, что хотите закрыть приложение?");
        setTitle("Внимание");
    }
}