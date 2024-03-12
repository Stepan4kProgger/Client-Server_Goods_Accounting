package res.cwclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import res.*;
import res.common.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Controller {
    @FXML
    private AnchorPane seeAllOperationsPane;
    @FXML
    private TableView<Operation> seeAllOperationsTable;
    @FXML
    private TableColumn<Operation, String> seeAllOperationsTableLogin;
    @FXML
    private TableColumn<Operation, String> seeAllOperationsTableName;
    @FXML
    private TableColumn<Operation, String> seeAllOperationsTableType;
    @FXML
    private TableColumn<Operation, String> seeAllOperationsTableSubj;
    @FXML
    private TableColumn<Operation, String> seeAllOperationsTableSubjName;
    @FXML
    private TableColumn<Operation, String> seeAllOperationsTableInfo;
    @FXML
    private AnchorPane WorkWithUsersPane;
    @FXML
    private AnchorPane WorkWithUsersInteractionPane;
    @FXML
    private TableView<Client> WorkWithUsersTable;
    @FXML
    private TableColumn<Client, String> WorkWithUsersTableLogin;
    @FXML
    private TableColumn<Client, String> WorkWithUsersTableName;
    @FXML
    private TableColumn<Client, Integer> WorkWithUsersTableAdmin;
    @FXML
    private AnchorPane WorkWithWorkersPane;
    @FXML
    private AnchorPane WorkWithWorkersInteractionPane;
    @FXML
    private TableView<Worker> WorkWithWorkersTable;
    @FXML
    private TableColumn<Worker, String> WorkWithWorkersTableName;
    @FXML
    private TableColumn<Worker, Integer> WorkWithWorkersTableAge;
    @FXML
    private TableColumn<Worker, String> WorkWithWorkersTableLogin;
    @FXML
    private TableColumn<Worker, String> WorkWithWorkersTablePost;
    @FXML
    private AnchorPane WorkWithProductsPane;
    @FXML
    private AnchorPane WorkWithProductsInteractionPane;
    @FXML
    private TableView<Product> WorkWithProductsTable;
    @FXML
    private TableColumn<Product, Integer> WorkWithProductsTableId;
    @FXML
    private TableColumn<Product, String> WorkWithProductsTableType;
    @FXML
    private TableColumn<Product, String> WorkWithProductsTableName;
    @FXML
    private TableColumn<Product, String> WorkWithProductsTableManufacturer;
    @FXML
    private TableColumn<Product, Integer> WorkWithProductsTableAmount;
    @FXML
    private TableColumn<Product, Float> WorkWithProductsTablePrice;
    @FXML
    private AnchorPane seeOperationsUserPane;
    @FXML
    private TableView<Operation> seeOperationsUserTable;
    @FXML
    private TableColumn<Operation, String> seeOperationsUserTableAuthor;
    @FXML
    private TableColumn<Operation, String> seeOperationsUserTableType;
    @FXML
    private TableColumn<Operation, String> seeOperationsUserTableTarget;
    @FXML
    private TableColumn<Operation, String> seeOperationsUserTableInfo;
    @FXML
    private TableView<Worker> seeWorkersTable;
    @FXML
    private TableColumn<Worker, String> seeWorkersTableName;
    @FXML
    private TableColumn<Worker, Integer> seeWorkersTableAge;
    @FXML
    private TableColumn<Worker, String> seeWorkersTableLogin;
    @FXML
    private TableColumn<Worker, String> seeWorkersTablePost;
    @FXML
    private AnchorPane seeWorkersPane;
    @FXML
    private Button backMenuButton;
    @FXML
    private AnchorPane leavePane;
    @FXML
    private Label LoginNotFoundLabel;
    @FXML
    private Label PasswordIsWrongLabel;
    @FXML
    private Label UnverifiedLabel;
    @FXML
    private Label UnknownErrorLabel;
    @FXML
    private AnchorPane StartScreen;
    @FXML
    private AnchorPane LoginScreen;
    @FXML
    private AnchorPane LoginElements;
    @FXML
    private AnchorPane RegisterElements;
    @FXML
    private TextField LoginField;
    @FXML
    private PasswordField MaskedPasswordField;
    @FXML
    private TextField ShownPasswordField;
    @FXML
    private TextField RegisterLoginField;
    @FXML
    private PasswordField RegisterPasswordField1;
    @FXML
    private PasswordField RegisterPasswordField2;
    @FXML
    private CheckBox ShowPassword;
    @FXML
    private Label UnconnectedLabel1;
    @FXML
    private Label UnconnectedLabel2;
    @FXML
    private Label LoginIsTakenLabel;
    @FXML
    private Label PasswordsNotMatchLabel;
    @FXML
    private Label RegLoginEmptyLabel;
    @FXML
    private Label RegPasswordEmptyLabel;
    @FXML
    private Label RegisterUnknownErrorLabel;
    @FXML
    private Label LoginFieldEmpty;
    @FXML
    private Label PasswordFieldEmpty;
    @FXML
    private Label RegSuccessLabel;
    @FXML
    private AnchorPane LeaveButtonOverlay;
    @FXML
    private Button LeaveButton;
    @FXML
    private AnchorPane AfterLoginScreen;
    @FXML
    private AnchorPane UserMenuScreen;
    @FXML
    private AnchorPane AdminMenuScreen;
    @FXML
    private Label UserLabel;
    @FXML
    private AnchorPane changePasswordPane;
    @FXML
    private PasswordField changePasswordField1;
    @FXML
    private PasswordField changePasswordField2;
    @FXML
    private Label changePasswordError;
    @FXML
    private Label changePasswordSuccessLabel;
    private ClientInteractions client;
    private static boolean isLoggedIn = false;

    private static final ButtonType agree = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);

    private Dialog<Product> productAddDialog;
    private final ComboBox<String> productTypeComboBox = new ComboBox<>();
    private Dialog<Product> productEditDialog;
    private ComboBox<String> changeBox;
    private Dialog<ProductType> typeAddDialog;
    private ObservableList<Product> products;
    private ObservableList<Worker> workers;
    private Dialog<Worker> workerAddDialog;
    private Dialog<Worker> workerPostDialog;
    private Dialog<Worker> workerLoginDialog;
    private final ComboBox<String> loginBox = new ComboBox<>();
    private ObservableList<Client> users;
    private final ComboBox<String> unverifiedBox = new ComboBox<>();
    private final ComboBox<String> nameBox = new ComboBox<>();
    private Dialog<Client> verifyDialog;
    private Dialog<Client> assignDialog;
    private ObservableList<Operation> operations;

    private void initSeeWorkersTable() {
        seeWorkersTable.getColumns().set(0, seeWorkersTableName);
        seeWorkersTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        seeWorkersTable.getColumns().set(1, seeWorkersTableAge);
        seeWorkersTableAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        seeWorkersTable.getColumns().set(2, seeWorkersTableLogin);
        seeWorkersTableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        seeWorkersTable.getColumns().set(3, seeWorkersTablePost);
        seeWorkersTablePost.setCellValueFactory(new PropertyValueFactory<>("post"));
    }

    private void initSeeOperationsUserTable() {
        seeOperationsUserTable.getColumns().set(0, seeOperationsUserTableAuthor);
        seeOperationsUserTableAuthor.setCellValueFactory(new PropertyValueFactory<>("name"));
        seeOperationsUserTable.getColumns().set(1, seeOperationsUserTableType);
        seeOperationsUserTableType.setCellValueFactory(new PropertyValueFactory<>("operationType"));
        seeOperationsUserTable.getColumns().set(2, seeOperationsUserTableTarget);
        seeOperationsUserTableTarget.setCellValueFactory(new PropertyValueFactory<>("target"));
        seeOperationsUserTable.getColumns().set(3, seeOperationsUserTableInfo);
        seeOperationsUserTableInfo.setCellValueFactory(new PropertyValueFactory<>("info"));
    }

    private void initWorkWithProductsTable() {
        products = (FXCollections.observableArrayList());
        WorkWithProductsTable.getColumns().set(0, WorkWithProductsTableId);
        WorkWithProductsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        WorkWithProductsTable.getColumns().set(1, WorkWithProductsTableType);
        WorkWithProductsTableType.setCellValueFactory(new PropertyValueFactory<>("prodType"));
        WorkWithProductsTable.getColumns().set(2, WorkWithProductsTableName);
        WorkWithProductsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        WorkWithProductsTable.getColumns().set(3, WorkWithProductsTableManufacturer);
        WorkWithProductsTableManufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        WorkWithProductsTable.getColumns().set(4, WorkWithProductsTableAmount);
        WorkWithProductsTableAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        WorkWithProductsTable.getColumns().set(5, WorkWithProductsTablePrice);
        WorkWithProductsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        WorkWithProductsTable.setItems(products);
    }

    private void initWorkWithWorkersTable() {
        workers = (FXCollections.observableArrayList());
        WorkWithWorkersTable.getColumns().set(0, WorkWithWorkersTableName);
        WorkWithWorkersTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        WorkWithWorkersTable.getColumns().set(1, WorkWithWorkersTableAge);
        WorkWithWorkersTableAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        WorkWithWorkersTable.getColumns().set(2, WorkWithWorkersTableLogin);
        WorkWithWorkersTableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        WorkWithWorkersTable.getColumns().set(3, WorkWithWorkersTablePost);
        WorkWithWorkersTablePost.setCellValueFactory(new PropertyValueFactory<>("post"));
        WorkWithWorkersTable.setItems(workers);
    }

    private void initWorkWithUsersTable() {
        users = FXCollections.observableArrayList();
        WorkWithUsersTable.getColumns().set(0, WorkWithUsersTableLogin);
        WorkWithUsersTableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        WorkWithUsersTable.getColumns().set(1, WorkWithUsersTableName);
        WorkWithUsersTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        WorkWithUsersTable.getColumns().set(2, WorkWithUsersTableAdmin);
        WorkWithUsersTableAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        WorkWithUsersTable.setItems(users);
    }

    private void initSeeOperationsTable() {
        operations = FXCollections.observableArrayList();
        seeAllOperationsTable.getColumns().set(0, seeAllOperationsTableLogin);
        seeAllOperationsTableLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        seeAllOperationsTable.getColumns().set(1, seeAllOperationsTableName);
        seeAllOperationsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        seeAllOperationsTable.getColumns().set(2, seeAllOperationsTableType);
        seeAllOperationsTableType.setCellValueFactory(new PropertyValueFactory<>("operationType"));
        seeAllOperationsTable.getColumns().set(3, seeAllOperationsTableSubj);
        seeAllOperationsTableSubj.setCellValueFactory(new PropertyValueFactory<>("targetType"));
        seeAllOperationsTable.getColumns().set(4, seeAllOperationsTableSubjName);
        seeAllOperationsTableSubjName.setCellValueFactory(new PropertyValueFactory<>("target"));
        seeAllOperationsTable.getColumns().set(5, seeAllOperationsTableInfo);
        seeAllOperationsTableInfo.setCellValueFactory(new PropertyValueFactory<>("info"));
        seeAllOperationsTable.setItems(operations);
    }

    private void initProductAddDialog() {
        productAddDialog = new Dialog<>();
        productAddDialog.setTitle("Добавление товара");
        productAddDialog.setHeaderText("Заполните значения ниже:");
        DialogPane dialogAddPane = productAddDialog.getDialogPane();
        dialogAddPane.getButtonTypes().addAll(agree, cancel);
        TextField name = new TextField("Название товара");
        TextField manufacturer = new TextField("Производитель");
        TextField amount = new TextField("Количество");
        TextField price = new TextField("Цена");
        dialogAddPane.setContent(new VBox(8, name, productTypeComboBox, manufacturer, amount, price));
        productAddDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Product product = Factory.makeProduct();
                try {
                    product.setName(name.getText());
                    product.setProdType(productTypeComboBox.getSelectionModel().getSelectedItem());
                    product.setManufacturer(manufacturer.getText());
                    product.setAmount(parseInt(amount.getText()));
                    product.setPrice(parseFloat(price.getText()));
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return product;
            }
            return null;
        });
    }

    private void initProductEditDialog() {
        productEditDialog = new Dialog<>();
        productEditDialog.setTitle("Редактирование товара");
        productEditDialog.setHeaderText("Выберите и заполните значения:");
        changeBox = new ComboBox<>(FXCollections.observableArrayList("Количество", "Цена"));
        changeBox.getSelectionModel().selectFirst();
        TextField value = new TextField("Значение");
        DialogPane dialogEditPane = productEditDialog.getDialogPane();
        dialogEditPane.getButtonTypes().addAll(agree, cancel);
        dialogEditPane.setContent(new VBox(8, changeBox, value));
        productEditDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Product product = Factory.makeProduct();
                try {
                    if (changeBox.getSelectionModel().getSelectedItem().equals("Количество"))
                        product.setAmount(parseInt(value.getText()));
                    else if (changeBox.getSelectionModel().getSelectedItem().equals("Цена")) {
                        product.setAmount(-9999);
                        product.setPrice(parseFloat(value.getText()));
                    } else throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return product;
            }
            return null;
        });
    }

    private void initTypeAddDialog() {
        typeAddDialog = new Dialog<>();
        typeAddDialog.setTitle("Добавление типа товара");
        typeAddDialog.setHeaderText("Заполните значения ниже:");
        DialogPane typeAddDialogPane = typeAddDialog.getDialogPane();
        typeAddDialogPane.getButtonTypes().addAll(agree, cancel);
        TextField prodType2 = new TextField("Тип товара");
        TextField description = new TextField("Описание");
        typeAddDialogPane.setContent(new VBox(8, prodType2, description));
        typeAddDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                ProductType type = new ProductType();
                type.setProdType(prodType2.getText());
                type.setDescription(description.getText());
                if (type.getProdType().isEmpty()) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return type;
            }
            return null;
        });
    }

    private void initWorkerAddDialog() {
        workerAddDialog = new Dialog<>();
        workerAddDialog.setHeaderText("Добавление сотрудника");
        workerAddDialog.setHeaderText("Заполните значения ниже:");
        DialogPane dialogPane = workerAddDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(agree, cancel);
        TextField name = new TextField("Полное имя сотрудника");
        TextField age = new TextField("Возраст сотрудника");
        TextField post = new TextField("Должность");
        dialogPane.setContent(new VBox(8, name, age, post));
        workerAddDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Worker worker = Factory.makeWorker();
                try {
                    worker.setName(name.getText());
                    worker.setLogin("");
                    worker.setPost(post.getText());
                    worker.setAge(parseInt(age.getText()));
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return worker;
            }
            return null;
        });
    }

    private void initWorkerPostDialog() {
        workerPostDialog = new Dialog<>();
        workerPostDialog.setTitle("Переаттестация сотрудника");
        workerPostDialog.setHeaderText("Выберите и заполните значения:");
        TextField value = new TextField("Новая должность");
        DialogPane dialogPane = workerPostDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(agree, cancel);
        dialogPane.setContent(new VBox(8, value));
        workerPostDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Worker worker = Factory.makeWorker();
                try {
                    worker.setPost(value.getText());
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return worker;
            }
            return null;
        });
    }

    private void initWorkerLoginDialog() {
        workerLoginDialog = new Dialog<>();
        workerLoginDialog.setTitle("Смена логина сотрудника");
        workerLoginDialog.setHeaderText("Выберите и заполните значения:");
        DialogPane dialogPane = workerLoginDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(agree, cancel);
        dialogPane.setContent(new VBox(8, loginBox));
        workerLoginDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Worker worker = Factory.makeWorker();
                try {
                    worker.setLogin(loginBox.getSelectionModel().getSelectedItem());
                    if (worker.getLogin().isEmpty())
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return worker;
            }
            return null;
        });
    }

    private void initVerifyDialog() {
        verifyDialog = new Dialog<>();
        verifyDialog.setTitle("Верифицирование учётной записи");
        verifyDialog.setHeaderText("Выберите и заполните значения:");
        DialogPane dialogPane = verifyDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(agree, cancel);
        dialogPane.setContent(new VBox(8, unverifiedBox, nameBox));
        verifyDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Client user = Factory.makeClient();
                try {
                    user.setLogin(unverifiedBox.getSelectionModel().getSelectedItem());
                    user.setName(nameBox.getSelectionModel().getSelectedItem());
                    if (user.getLogin().isEmpty() || user.getName().isEmpty())
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return user;
            }
            return null;
        });
    }

    private void initAssignDialog() {
        assignDialog = new Dialog<>();
        assignDialog.setTitle("Перепривязка имени к учётной записи");
        assignDialog.setHeaderText("Выберите и заполните значения:");
        DialogPane dialogPane = assignDialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(agree, cancel);
        dialogPane.setContent(new VBox(8, nameBox));
        assignDialog.setResultConverter((ButtonType button) -> {
            if (button == agree) {
                Client user = Factory.makeClient();
                try {
                    user.setName(nameBox.getSelectionModel().getSelectedItem());
                    if (user.getName().isEmpty())
                        throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    onErrorAlert alert = new onErrorAlert();
                    alert.showAndWait();
                    return null;
                }
                return user;
            }
            return null;
        });
    }

    @FXML
    public void initialize() {
        //FXML tables
        initSeeWorkersTable();
        initSeeOperationsUserTable();
        initWorkWithProductsTable();
        initWorkWithWorkersTable();
        initWorkWithUsersTable();
        initSeeOperationsTable();

        //dialogs
        initProductAddDialog();
        initProductEditDialog();
        initTypeAddDialog();
        initWorkerAddDialog();
        initWorkerPostDialog();
        initWorkerLoginDialog();
        initVerifyDialog();
        initAssignDialog();
    }


    private final EventHandler<WindowEvent> closeEventHandler = event -> {
        if (isLoggedIn) {
            ButtonType agree = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
            onCloseAlert alert = new onCloseAlert(agree, cancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isEmpty() || result.get().equals(cancel)) {
                event.consume();
            } else {
                client.onLeaveState();
            }
        }
    };

    public EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }

    public void onStartButtonClick(ActionEvent ignored) {
        StartScreen.setVisible(false);
        LoginScreen.setVisible(true);
        try {
            client = new ClientInteractions();
        } catch (IOException e) {
            LoginElements.setVisible(false);
            UnconnectedLabel1.setVisible(true);
            UnconnectedLabel2.setVisible(true);
        }
    }

    public void onLoginBackButtonClick(ActionEvent ignored) {
        if (RegisterElements.isVisible()) {
            RegisterElements.setVisible(false);
            clearErrorsRegScreen();
            RegisterLoginField.setText("");
            RegisterPasswordField1.setText("");
            RegisterPasswordField2.setText("");
            LoginElements.setVisible(true);
        } else if (UnconnectedLabel1.isVisible()) {
            UnconnectedLabel1.setVisible(false);
            UnconnectedLabel2.setVisible(false);
            LoginScreen.setVisible(false);
            LoginElements.setVisible(true);
            StartScreen.setVisible(true);
        } else {
            LoginScreen.setVisible(false);
            clearLoginScreen();
            client.closeSocket();
            StartScreen.setVisible(true);
        }
    }

    private void clearErrorsLoginScreen() {
        LoginFieldEmpty.setVisible(false);
        PasswordFieldEmpty.setVisible(false);
        LoginNotFoundLabel.setVisible(false);
        PasswordIsWrongLabel.setVisible(false);
    }

    private void clearErrorsRegScreen() {
        LoginIsTakenLabel.setVisible(false);
        RegLoginEmptyLabel.setVisible(false);
        PasswordsNotMatchLabel.setVisible(false);
        RegPasswordEmptyLabel.setVisible(false);
    }

    private void clearLoginScreen() {
        clearErrorsLoginScreen();
        UnverifiedLabel.setVisible(false);
        LoginField.setText("");
        ShowPassword.setSelected(false);
        MaskedPasswordField.setText("");
        ShownPasswordField.setText("");

    }

    public void onLoginButtonClick(ActionEvent ignored) {
        clearErrorsLoginScreen();
        boolean interrupt = false;
        if (ShowPassword.isSelected()) {
            MaskedPasswordField.setText(ShownPasswordField.getText());
        }
        if (LoginField.getText().isEmpty()) {
            LoginFieldEmpty.setVisible(true);
            interrupt = true;
        }
        if (MaskedPasswordField.getText().isEmpty()) {
            PasswordFieldEmpty.setVisible(true);
            interrupt = true;
        }
        if (interrupt)
            return;
        int answer = client.onLogin(LoginField.getText(), MaskedPasswordField.getText());
        switch (answer) {
            case 1:
            case 0:
                ShowPassword.setSelected(false);
                MaskedPasswordField.setText("");
                ShownPasswordField.setText("");
                LoginScreen.setVisible(false);
                AfterLoginScreen.setVisible(true);
                client.setClient(new Client(LoginField.getText(), MaskedPasswordField.getText(), answer));
                if (client.getClient().getAdmin() == 0) {
                    UserMenuScreen.setVisible(true);
                } else {
                    AdminMenuScreen.setVisible(true);
                }
                isLoggedIn = true;
                client.onAfterLoginState();
                if (client.getClient().getName() != null)
                    UserLabel.setText("Добро пожаловать,\n" + client.getClient().getName() + "!");
                break;
            case -1:
                LoginNotFoundLabel.setVisible(true);
                break;
            case -2:
                PasswordIsWrongLabel.setVisible(true);
                MaskedPasswordField.setText("");
                ShownPasswordField.setText("");
                break;
            case -3:
                UnverifiedLabel.setVisible(true);
                break;
            case -4:
                UnknownErrorLabel.setVisible(true);
                Thread thread = new Thread(new doWithDelay(() -> UnknownErrorLabel.setVisible(false), 4000));
                thread.start();
                break;
        }
    }

    public void onRegisterButtonClick(ActionEvent ignored) {
        boolean interrupt = false;
        if (RegisterLoginField.getText().isEmpty()) {
            RegLoginEmptyLabel.setVisible(true);
            interrupt = true;
        }
        if (RegisterPasswordField1.getText().isEmpty()) {
            RegPasswordEmptyLabel.setVisible(true);
            interrupt = true;
        }
        if (interrupt)
            return;
        if (!RegisterPasswordField1.getText().equals(RegisterPasswordField2.getText())) {
            PasswordsNotMatchLabel.setVisible(true);
            return;
        }
        int answer = client.onRegister(RegisterLoginField.getText(), RegisterPasswordField1.getText());
        switch (answer) {
            case 0:
                RegSuccessLabel.setVisible(true);
                Thread successLabelHide = new Thread(new doWithDelay(() -> RegSuccessLabel.setVisible(false), 4000));
                successLabelHide.start();
                break;
            case -1:
                LoginIsTakenLabel.setVisible(true);
                break;
            case -2:
                RegisterUnknownErrorLabel.setVisible(true);
                Thread errorLabelHide = new Thread(new doWithDelay(() -> RegisterUnknownErrorLabel.setVisible(false), 4000));
                errorLabelHide.start();
                break;
        }
    }

    public void onShowPasswordSwitch(ActionEvent ignored) {
        if (ShowPassword.isSelected()) {
            ShownPasswordField.setVisible(true);
            MaskedPasswordField.setVisible(false);
            ShownPasswordField.setText(MaskedPasswordField.getText());
        } else {
            MaskedPasswordField.setVisible(true);
            ShownPasswordField.setVisible(false);
            MaskedPasswordField.setText(ShownPasswordField.getText());
        }
    }

    public void onRegisterTextClick(ActionEvent ignored) {
        LoginElements.setVisible(false);
        clearLoginScreen();
        RegisterElements.setVisible(true);
    }

    public void onLoginTextFieldClick(MouseEvent ignored) {
        LoginNotFoundLabel.setVisible(false);
        LoginFieldEmpty.setVisible(false);
        UnverifiedLabel.setVisible(false);
    }

    public void onPasswordTextFieldClicked(MouseEvent ignored) {
        PasswordFieldEmpty.setVisible(false);
        PasswordIsWrongLabel.setVisible(false);
    }

    public void onRegisterLoginFieldClick(MouseEvent ignored) {
        LoginIsTakenLabel.setVisible(false);
        RegLoginEmptyLabel.setVisible(false);
    }

    public void onRegisterPasswordFieldClicked(MouseEvent ignored) {
        PasswordsNotMatchLabel.setVisible(false);
        RegPasswordEmptyLabel.setVisible(false);
    }

    public void onLeaveButtonClick(ActionEvent ignored) {
        LeaveButton.setVisible(false);
        LeaveButtonOverlay.setVisible(true);
    }

    public void onLeaveAcceptButtonClick(ActionEvent ignored) {
        client.onLeaveState();
        client.closeSocket();
        isLoggedIn = false;
        UserMenuScreen.setVisible(false);
        AdminMenuScreen.setVisible(false);
        AfterLoginScreen.setVisible(false);
        StartScreen.setVisible(true);
    }

    public void onLeaveCancelButtonClick(ActionEvent ignored) {
        LeaveButtonOverlay.setVisible(false);
        LeaveButton.setVisible(true);
    }

    public void onLeaveCancel(MouseEvent ignored) {
        LeaveButtonOverlay.setVisible(false);
        LeaveButton.setVisible(true);
    }

    private void doMenuLock() {
        leavePane.setVisible(false);
        backMenuButton.setVisible(true);
        if (client.getClient().getAdmin() == 0)
            UserMenuScreen.setDisable(true);
        else
            AdminMenuScreen.setDisable(true);
    }

    private void doMenuHide() {
        leavePane.setVisible(false);
        backMenuButton.setVisible(true);
        if (client.getClient().getAdmin() == 0)
            UserMenuScreen.setVisible(false);
        else
            AdminMenuScreen.setVisible(false);
    }

    public void onChangePassword(ActionEvent ignored) {
        if (changePasswordField1.getText().equals(changePasswordField2.getText()) && !changePasswordField1.getText().isEmpty()) {
            changePasswordSuccessLabel.setVisible(true);
            Thread thread = new Thread(new doWithDelay(() -> changePasswordError.setVisible(false), 2000));
            thread.start();
            client.onChangePasswordState(changePasswordField1.getText());

        } else {
            changePasswordError.setVisible(true);
            Thread thread = new Thread(new doWithDelay(() -> changePasswordError.setVisible(false), 2000));
            thread.start();
        }
    }

    public void changePasswordMenuButtonClick(ActionEvent ignored) {
        doMenuLock();
        changePasswordPane.setVisible(true);
    }

    public void onBackMenuButtonPress(ActionEvent ignored) {
        if (changePasswordPane.isVisible()) {
            changePasswordPane.setVisible(false);
            changePasswordField1.setText("");
            changePasswordField2.setText("");
        } else if (seeWorkersPane.isVisible()) {
            seeWorkersPane.setVisible(false);
        } else if (seeOperationsUserPane.isVisible()) {
            seeOperationsUserPane.setVisible(false);
        } else if (WorkWithProductsPane.isVisible()) {
            WorkWithProductsPane.setVisible(false);
            WorkWithProductsInteractionPane.setDisable(true);
            client.onBack();
        } else if (WorkWithWorkersPane.isVisible()) {
            WorkWithWorkersPane.setVisible(false);
            WorkWithProductsInteractionPane.setDisable(true);
            client.onBack();
        } else if (WorkWithUsersPane.isVisible()) {
            WorkWithUsersPane.setVisible(false);
            WorkWithUsersInteractionPane.setDisable(true);
            client.onBack();
        } else if (seeAllOperationsPane.isVisible()) {
            seeAllOperationsPane.setVisible(false);
            client.onBack();
        }
        if (client.getClient().getAdmin() == 0) {
            UserMenuScreen.setVisible(true);
            UserMenuScreen.setDisable(false);
        } else {
            AdminMenuScreen.setVisible(true);
            AdminMenuScreen.setDisable(false);
        }
        backMenuButton.setVisible(false);
        leavePane.setVisible(true);
    }

    public void onSeeWorkersUserMenuButtonClick(ActionEvent ignored) {
        doMenuLock();
        seeWorkersPane.setVisible(true);
        seeWorkersTable.setItems(FXCollections.observableArrayList(client.onSeeWorkersState()));
    }

    public void onSeeOperationsUserButtonClick(ActionEvent ignored) {
        doMenuLock();
        seeOperationsUserPane.setVisible(true);
        seeOperationsUserTable.setItems(FXCollections.observableArrayList(client.onSeeOperationsAsUserState()));
    }

    public void onWorkWithProductsMenuButtonClick(ActionEvent ignored) {
        doMenuHide();
        WorkWithProductsPane.setVisible(true);
        products.setAll(FXCollections.observableArrayList(client.displayProducts()));
    }

    public void onWorkWithProductsAddButtonClick(ActionEvent ignored) {
        productTypeComboBox.setItems(FXCollections.observableArrayList(client.getTypes()));
        Optional<Product> optionalResult = productAddDialog.showAndWait();
        optionalResult.ifPresent((Product prod) -> {
            client.addProduct(prod);
            products.setAll(FXCollections.observableArrayList(client.refreshProducts()));
            WorkWithProductsTable.getSelectionModel().clearSelection();
            WorkWithProductsInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithProductsDeleteButtonClick(ActionEvent ignored) {
        onDeleteAlert alert = new onDeleteAlert(agree, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        Product product = WorkWithProductsTable.getSelectionModel().getSelectedItem();
        if (result.isPresent() && result.get().equals(agree) && product != null) {
            client.removeProduct(product);
            products.setAll(FXCollections.observableArrayList(client.refreshProducts()));
            WorkWithProductsTable.getSelectionModel().clearSelection();
            WorkWithProductsInteractionPane.setDisable(true);
        }
    }

    public void onWorkWithProductsEditButtonClick(ActionEvent ignored) {
        Optional<Product> optionalResult = productEditDialog.showAndWait();
        optionalResult.ifPresent((Product prod) -> {
            String what_changed = prod.getAmount() == -9999 ? "price" : "amount";
            client.editProduct(products.get(WorkWithProductsTable.getSelectionModel().getSelectedIndex()), prod, what_changed);
            products.setAll(FXCollections.observableArrayList(client.refreshProducts()));
            WorkWithProductsTable.getSelectionModel().clearSelection();
            WorkWithProductsInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithProductsStatsButtonClick(ActionEvent ignored) {
        Map<String, Integer> pairs = new HashMap<>();
        for (var value : products) { //products - ObservableArrayList<Products>
            if (pairs.containsKey(value.getProdType()))
                pairs.put(value.getProdType(), pairs.get(value.getProdType()) + value.getAmount());
            else
                pairs.put(value.getProdType(), value.getAmount());
        }
        ArrayList<PieChart.Data> pie_data = new ArrayList<>();
        for (var value : pairs.entrySet()) {
            pie_data.add(new PieChart.Data(value.getKey(), value.getValue()));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(pie_data);
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Статистика по количеству товара по категориям");
        Dialog<Object> diagram = new Dialog<>();
        diagram.getDialogPane().getButtonTypes().add(agree);
        diagram.getDialogPane().setContent(chart);
        Optional<Object> optionalResult = diagram.showAndWait();
        optionalResult.ifPresent((Object object) -> {
            try {
                FileWriter fw = new FileWriter("Analytics.txt", false);
                StringBuilder string = new StringBuilder();
                for (var value: pairs.entrySet()) {
                    string.append(value.getKey()).append(" - ").append(value.getValue() + '\n');
                }
                fw.write(string.toString());
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void onWorkWithProductsSelect(MouseEvent ignored) {
        WorkWithProductsInteractionPane.setDisable(WorkWithProductsTable.getSelectionModel().getSelectedItem() == null);
    }

    public void onAddProdTypeClick(ActionEvent ignored) {
        Optional<ProductType> optionalResult = typeAddDialog.showAndWait();
        optionalResult.ifPresent((ProductType type) -> client.addType(type));
    }

    public void onWorkWithWorkersMenuButtonClick(ActionEvent ignored) {
        doMenuHide();
        WorkWithWorkersPane.setVisible(true);
        workers.setAll(FXCollections.observableArrayList(client.displayWorkers()));
    }

    public void onWorkWithWorkersAddButtonClick(ActionEvent ignored) {
        Optional<Worker> optionalResult = workerAddDialog.showAndWait();
        optionalResult.ifPresent((Worker worker) -> {
            client.addWorker(worker);
            workers.setAll(FXCollections.observableArrayList(client.refreshWorkers()));
            WorkWithWorkersTable.getSelectionModel().clearSelection();
            WorkWithWorkersInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithWorkersDeleteButtonClick(ActionEvent ignored) {
        onDeleteAlert alert = new onDeleteAlert();
        Optional<ButtonType> result = alert.showAndWait();
        Worker worker = WorkWithWorkersTable.getSelectionModel().getSelectedItem();
        if (result.isPresent() && result.get().equals(agree) && worker != null) {
            client.removeWorker(worker);
            workers.setAll(FXCollections.observableArrayList(client.refreshWorkers()));
            WorkWithWorkersTable.getSelectionModel().clearSelection();
            WorkWithWorkersInteractionPane.setDisable(true);
        }
    }

    public void onWorkWithWorkersChangePostButtonClick(ActionEvent ignored) {
        Optional<Worker> optionalResult = workerPostDialog.showAndWait();
        optionalResult.ifPresent((Worker worker) -> {
            client.editWorker("post", workers.get(WorkWithWorkersTable.getSelectionModel().getSelectedIndex()), worker);
            workers.setAll(FXCollections.observableArrayList(client.refreshWorkers()));
            WorkWithWorkersTable.getSelectionModel().clearSelection();
            WorkWithWorkersInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithWorkersChangeLoginButtonClick(ActionEvent ignored) {
        loginBox.setItems(FXCollections.observableArrayList(client.getLogins()));
        Optional<Worker> optionalResult = workerLoginDialog.showAndWait();
        optionalResult.ifPresent((Worker worker) -> {
            client.editWorker("login", workers.get(WorkWithWorkersTable.getSelectionModel().getSelectedIndex()), worker);
            workers.setAll(FXCollections.observableArrayList(client.refreshWorkers()));
            WorkWithWorkersTable.getSelectionModel().clearSelection();
            WorkWithWorkersInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithWorkersSelect(MouseEvent ignored) {
        WorkWithWorkersInteractionPane.setDisable(WorkWithWorkersTable.getSelectionModel().getSelectedItem() == null);
    }


    public void onWorkWithUsersMenuButtonClick(ActionEvent ignored) {
        doMenuHide();
        WorkWithUsersPane.setVisible(true);
        users.setAll(client.displayUsers());
    }

    public void onWorkWithUsersAddButtonClick(ActionEvent ignored) {
        unverifiedBox.setItems(FXCollections.observableArrayList(client.getUnverified()));
        nameBox.setItems(FXCollections.observableArrayList(client.getNames()));
        Optional<Client> optionalResult = verifyDialog.showAndWait();
        optionalResult.ifPresent((Client user) -> {
            client.addUser(user);
            users.setAll(FXCollections.observableArrayList(client.refreshUsers()));
            WorkWithUsersTable.getSelectionModel().clearSelection();
            WorkWithUsersInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithUsersDeleteButtonClick(ActionEvent ignored) {
        onDeleteAlert alert = new onDeleteAlert();
        Optional<ButtonType> result = alert.showAndWait();
        Client user = WorkWithUsersTable.getSelectionModel().getSelectedItem();
        if (result.isPresent() && result.get().equals(agree) && user != null) {
            client.removeUser(user);
            workers.setAll(FXCollections.observableArrayList(client.refreshWorkers()));
            WorkWithWorkersTable.getSelectionModel().clearSelection();
            WorkWithWorkersInteractionPane.setDisable(true);
        }
    }

    public void onWorkWithUsersAssignNameButtonClick(ActionEvent ignored) {
        nameBox.setItems(FXCollections.observableArrayList(client.getNames()));
        Optional<Client> optionalResult = assignDialog.showAndWait();
        optionalResult.ifPresent((Client user) -> {
            client.editUser("name", users.get(WorkWithUsersTable.getSelectionModel().getSelectedIndex()), user);
            users.setAll(FXCollections.observableArrayList(client.refreshUsers()));
            WorkWithUsersTable.getSelectionModel().clearSelection();
            WorkWithUsersInteractionPane.setDisable(true);
        });
    }

    public void onWorkWithWorkersChangeAdminButtonClick(ActionEvent ignored) {
        Client user = users.get(WorkWithUsersTable.getSelectionModel().getSelectedIndex());
        user.setAdmin(Math.abs(user.getAdmin() - 1));
        client.editUser("isAdmin", users.get(WorkWithUsersTable.getSelectionModel().getSelectedIndex()), user);
        users.setAll(FXCollections.observableArrayList(client.refreshUsers()));
        WorkWithUsersTable.getSelectionModel().clearSelection();
        WorkWithUsersInteractionPane.setDisable(true);
    }

    public void onWorkWithUsersSelect(MouseEvent ignored) {
        WorkWithUsersInteractionPane.setDisable(WorkWithUsersTable.getSelectionModel().getSelectedItem() == null
                || WorkWithUsersTable.getSelectionModel().getSelectedItem().getLogin().equals(client.getClient().getLogin()));
    }


    public void onSeeAllOperationsMenuButtonClick(ActionEvent ignored) {
        doMenuHide();
        seeAllOperationsPane.setVisible(true);
        operations.setAll(client.getAllOperations());
    }

    public void onUpdateOperationsButtonClick(ActionEvent ignored) {
        operations.setAll(client.refreshOperations());
    }

    public void onSaveOperationsToFileClick(ActionEvent ignored) {
        try {
            FileWriter fw = new FileWriter("Operations.txt", false);
            StringBuilder string = new StringBuilder();
            for (var value: operations) {
                string.append(value.getLogin()).append('\t').append(value.getName()).append('\t').append(value.getOperationType()).append('\t').append(value.getTargetType()).append('\t').append(value.getTarget()).append('\t').append(value.getInfo()).append('\n');
            }
            fw.write(string.toString());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}