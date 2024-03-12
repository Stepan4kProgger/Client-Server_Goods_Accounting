package res;

import javafx.scene.control.Alert;

public class onErrorAlert extends Alert {
    private static final AlertType alertType = AlertType.ERROR;
    private static final String contentText = "Проверьте заполненные данные";

    public onErrorAlert() {
        super(alertType, contentText);
        setHeaderText("Не удалось выполнить действие");
        setTitle("Внимание");
    }
}
