import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

//ToDo: Попробовать переписать события для заголовков начального окна через css
public class Interface extends Application {
  private boolean key;
  static String userLogin;
  private String currentContact;
  private String lastSendingDate;
  private TreeSet<Label> contacts = new TreeSet<>(new LabelComparator());
  private HashMap<String, ArrayList<Row>> conversation = new HashMap<>();

  public void start(Stage stage) throws Exception {
    loginWindow(stage);
    //mainWindow(stage);
  }

  private void loginWindow(Stage stage) throws Exception {
    key = false;

    ImageView tick = new ImageView(new Image(new FileInputStream("..\\8240774.png")));
    tick.setFitHeight(30);
    tick.setFitWidth(30);
    tick.setEffect(new Lighting());

    ImageView cross = new ImageView(new Image(new FileInputStream("..\\Red X.png")));
    cross.setFitHeight(30);
    cross.setFitWidth(30);
    cross.setEffect(new Lighting());

    ImageView tick1 = new ImageView(new Image(new FileInputStream("..\\8240774.png")));
    tick1.setFitHeight(30);
    tick1.setFitWidth(30);
    tick1.setEffect(new Lighting());

    ImageView cross1 = new ImageView(new Image(new FileInputStream("..\\Red X.png")));
    cross1.setFitHeight(30);
    cross1.setFitWidth(30);
    cross1.setEffect(new Lighting());

    ImageView tick2 = new ImageView(new Image(new FileInputStream("..\\8240774.png")));
    tick2.setFitHeight(30);
    tick2.setFitWidth(30);
    tick2.setEffect(new Lighting());

    ImageView cross2 = new ImageView(new Image(new FileInputStream("..\\Red X.png")));
    cross2.setFitHeight(30);
    cross2.setFitWidth(30);
    cross2.setEffect(new Lighting());

    Text entranceText = new Text("Вход");
    entranceText.setId("entrance");
    Text registrationText = new Text("Регистрация");
    registrationText.setId("registration");

    TextField login = new TextField();
    login.setPrefSize(380, 40);
    login.setPromptText("Введите логин");
    login.setFocusTraversable(false);

    PasswordField password1 = new PasswordField();
    password1.setPrefSize(380, 40);
    password1.setPromptText("Введите пароль");
    password1.setFocusTraversable(false);

    PasswordField password2 = new PasswordField();
    password2.setPrefSize(380, 40);
    password2.setPromptText("Повторите пароль");
    password2.setFocusTraversable(false);

    Button button = new Button("Вход");
    button.setFocusTraversable(false);

    HBox titleBox = new HBox();
    titleBox.getChildren().addAll(entranceText, registrationText);
    titleBox.setAlignment(Pos.CENTER);
    titleBox.setSpacing(100);

    HBox loginBox = new HBox();
    ObservableList<Node> loginList = loginBox.getChildren();
    loginList.add(login);
    loginBox.setSpacing(20);

    HBox password1Box = new HBox();
    ObservableList<Node> password1List = password1Box.getChildren();
    password1List.add(password1);
    password1Box.setSpacing(20);

    HBox password2Box = new HBox();
    ObservableList<Node> password2List = password2Box.getChildren();
    password2List.add(password2);
    password2Box.setSpacing(20);

    VBox vBox = new VBox();
    VBox.setMargin(titleBox, new Insets(20, 20, 10, 20));
    VBox.setMargin(loginBox, new Insets(20, 0, 10, 20));
    VBox.setMargin(password1Box, new Insets(20, 0, 10, 20));
    VBox.setMargin(password2Box, new Insets(20, 0, 10, 20));
    VBox.setMargin(button, new Insets(10, 20, 10, 20));

    ObservableList<Node> list = vBox.getChildren();
    list.addAll(titleBox, loginBox, password1Box, button);

    Scene scene = new Scene(vBox, 460, 340);
    scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("Вход");
    stage.show();

    //Events
    login.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.TAB)) {
        password1.setFocusTraversable(true);
      } else {
        removeExtraElement(loginList);
      }
    });

    password1.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.TAB)) {
        if (list.size() == 5) {
          password2.setFocusTraversable(true);
        } else {
          button.setFocusTraversable(true);
        }
      } else {
        removeExtraElement(password1List);
      }
    });

    password2.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.TAB)) {
        button.setFocusTraversable(true);
      } else {
        removeExtraElement(password2List);
      }
    });

    button.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.TAB)) {
        login.setFocusTraversable(true);
      }
    });

    registrationText.setOnMouseClicked(event -> {
      if (!key) {
        list.remove(button);
        list.add(password2Box);
        list.add(button);
        button.setText("Зарегистрироваться");
        removeExtraElement(password1List, loginList);
      }
      key = true;
    });

    registrationText.setOnMouseMoved(event -> registrationText.setEffect(new Reflection()));

    registrationText.setOnMouseExited(event -> registrationText.setEffect(new InnerShadow(
        BlurType.THREE_PASS_BOX, Color.color(0, 0, 0, 0.7), 6, 0.0, 0, 2)));

    entranceText.setOnMouseClicked(event -> {
      if (key) {
        list.remove(button);
        list.remove(password2Box);
        list.add(button);
        button.setText("Вход");
        removeExtraElement(password1List, loginList, password2List);
      }
      key = false;
    });

    entranceText.setOnMouseMoved(event -> entranceText.setEffect(new Reflection()));

    entranceText.setOnMouseExited(event -> entranceText.setEffect(new InnerShadow(
        BlurType.THREE_PASS_BOX, Color.color(0, 0, 0, 0.7), 6, 0.0, 0, 2)));

    button.setOnAction(event -> {
      int ticksCounter = 0;
      try {
        if (key) {
          removeExtraElement(password1List, loginList, password2List);

          if (password1.getText().length() > 0) {
            password1List.add(tick1);
            ticksCounter++;
          } else {
            password1List.add(cross1);
          }

          if ((Objects.equals(password1.getText(), password2.getText())) && (password2.getText().length() > 0)) {
            password2List.add(tick2);
            ticksCounter++;
          } else {
            password2List.add(cross2);
          }

          if ((login.getText().length() > 0) && (login.getText().length() < 35) &&
              (!ORM.checkAccount("'" + login.getText() + "'"))) {
            loginList.add(tick);
            ticksCounter++;
          } else {
            loginList.add(cross);
          }

          if (ticksCounter == 3) {
            ORM.add(login.getText(), password1.getText());
            userLogin = login.getText();
            mainWindow(stage);
          }
        } else if (!ORM.checkAccount("'" + login.getText() + "'", "'" + password1.getText() + "'")) {
          removeExtraElement(password1List, loginList, password2List);
          loginList.add(cross);
          password1List.add(cross1);
        } else {
          userLogin = login.getText();
          mainWindow(stage);
        }
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    });
  }

  private void mainWindow(Stage stage) throws Exception {
    ImageView cross = new ImageView(new Image(new FileInputStream("..\\Red X.png")));
    cross.setFitHeight(30);
    cross.setFitWidth(30);
    cross.setEffect(new Lighting());

    ImageView tick = new ImageView(new Image(new FileInputStream("..\\8240774.png")));
    tick.setFitHeight(30);
    tick.setFitWidth(30);
    tick.setEffect(new Lighting());

    Text text = new Text("Контакты");
    Text column = new Text();
    Text someText = new Text("Здесь должна быть ваша переписка");

    TextField searchLine = new TextField();
    searchLine.setPromptText("Поиск...");
    searchLine.setPrefHeight(15);
    searchLine.setFocusTraversable(false);
    searchLine.setEditable(true);
    searchLine.setId("blackBorder");

    TextField addField = new TextField();
    addField.setPrefSize(300, 15);
    addField.setPromptText("Добавить контакт...");
    addField.setFocusTraversable(false);
    addField.setId("blackBorder");

    TextArea sendArea = new TextArea();
    sendArea.setPrefSize(600, 80);
    sendArea.setWrapText(true);
    sendArea.setPromptText("Напишите сообщение...");
    sendArea.setFocusTraversable(false);
    sendArea.setId("blackBorder");

    ImageView send = new ImageView(new Image(new FileInputStream("..\\6.png")));
    send.setFitHeight(27);
    send.setFitWidth(27);

    VBox messageBox = new VBox();
    ObservableList<Node> messageList = messageBox.getChildren();

    ScrollPane messageScroll = new ScrollPane();
    messageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    messageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    messageScroll.setContent(messageBox);
    messageScroll.setPrefHeight(600);
    messageScroll.setId("blackBorder");

    VBox contactsBox = new VBox();
    ObservableList<Node> contactList = contactsBox.getChildren();

    //Достаем все имеющиеся контакты
    contacts.addAll(ORM.readContacts(userLogin));

    for (Label label : contacts) {
      label.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-color: #262626");
      VBox.setMargin(label, new Insets(20, 20, 0, 20));
      label.setPrefWidth(300);

      label.setOnMouseClicked(subEvent -> setCurrentContact(label, messageList, messageScroll));
    }

    contactList.addAll(contacts);

    ScrollPane contactsScroll = new ScrollPane();
    contactsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    contactsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    contactsScroll.setContent(contactsBox);
    contactsScroll.setId("blackBorder");
    contactsScroll.setPrefSize(350, 600);

    HBox sendBox = new HBox(sendArea, send);
    sendBox.setSpacing(20);

    HBox addBox = new HBox(addField);
    ObservableList<Node> addList = addBox.getChildren();
    addBox.setSpacing(20);

    VBox leftVBox = new VBox(text, searchLine, contactsScroll, addBox);
    leftVBox.setSpacing(20);
    leftVBox.setPadding(new Insets(20, 0, 20, 20));

    VBox centerBox = new VBox(column);
    centerBox.setPrefSize(80, 740);
    //centerBox.setStyle("-fx-background-color: #760d4f");
    centerBox.setStyle("-fx-background-color: linear-gradient(#000000, #96127e, #000000)");

    VBox rightVBox = new VBox(someText, messageScroll, sendBox);
    rightVBox.setSpacing(20);
    rightVBox.setPadding(new Insets(20, 20, 20, 0));

    HBox mainBox = new HBox(leftVBox, centerBox, rightVBox);
    mainBox.setSpacing(5);

    Scene scene = new Scene(mainBox, 1130, 740);
    scene.getStylesheets().add(Main.class.getResource("style2.css").toExternalForm());
    stage.setScene(scene);
    stage.setTitle("Вход");
    stage.show();

    //Поток для ловли сообщений
    Thread thread1 = new Thread(() -> {
      ArrayList<String> arrayList = new ArrayList<>();
      while (stage.isShowing()) {
        try {
          if (messageList.size() > 0) {
            arrayList.addAll(ORM.checkMessages(currentContact));
            if (arrayList.size() > 0) {
              Platform.runLater(() -> {
                for (int i = 0; i < addList.size(); i += 2) {
                  addMessage(arrayList.get(i), arrayList.get(i + 1), messageList, false);
                }
                arrayList.clear();
              });
            }
          }
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
      }
    });
    thread1.start();

    //Events
    send.setOnMousePressed(event -> sendMessage(sendArea, messageList));

    send.setOnMouseReleased(event -> messageScroll.setVvalue(1));

    searchLine.setOnKeyReleased(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        double position = 1, i = 1;
        for (Label label : contacts) {
          if (label.getText().equals(searchLine.getText())) {
            setCurrentContact(label, messageList, messageScroll);
            position = i;
          }
          i++;
        }
        contactsScroll.setVvalue(position / contacts.size());
      }
    });

    addField.setOnKeyPressed(event -> {
      try {
        removeExtraElement(addList);
        if (event.getCode().equals(KeyCode.ENTER)) {
          if ((ORM.checkAccount("'" + addField.getText() + "'")) && (
              !contacts.contains(new Label(addField.getText()))) && (!addField.getText().equals(userLogin))) {

            Label newContact = new Label(addField.getText());
            newContact.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-color: #262626");
            VBox.setMargin(newContact, new Insets(20, 20, 0, 20));
            newContact.setPrefWidth(300);

            ORM.add(addField.getText());
            contacts.add(newContact);

            contactList.clear();
            contactList.addAll(contacts);

            addField.clear();
            addList.add(tick);

            newContact.setOnMouseClicked(subEvent -> setCurrentContact(newContact, messageList, messageScroll));
          } else {
            addList.add(cross);
          }
        }
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    });
  }

  private void sendMessage(TextArea sendArea, ObservableList<Node> messageList) {
    try {
      if ((sendArea.getText().length() > 0) && (!currentContact.isEmpty())) {
        getLastDate();
        ORM.add(currentContact, sendArea.getText(), lastSendingDate);
        addMessage(sendArea.getText(), lastSendingDate, messageList, true);
        sendArea.clear();
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void addMessage(String messageText, String dateText, ObservableList<Node> messageList, boolean key) {
    Label message = new Label(messageText);
    double x, distance, length = message.getText().length();

    if (length <= 45) {
      x = length * 9.001;
      distance = 600 - x;
    } else {
      x = 450;
      distance = 150;
    }

    message.setPrefWidth(x);
    message.setWrapText(true);
    message.setTextAlignment(TextAlignment.JUSTIFY);
    message.setStyle("-fx-border-color: #262626; -fx-border-style: solid");

    Label date = new Label(dateText);

    if (key) {
      message.setAlignment(Pos.TOP_RIGHT);
      date.setAlignment(Pos.TOP_RIGHT);
      VBox.setMargin(message, new Insets(20, 0, 0, distance));
      VBox.setMargin(date, new Insets(0, 0, 0, 480));
    } else {
      message.setAlignment(Pos.TOP_LEFT);
      date.setAlignment(Pos.TOP_LEFT);
      VBox.setMargin(message, new Insets(20, distance, 0, 10));
      VBox.setMargin(date, new Insets(0, 470, 0, 10));
    }
    messageList.addAll(message, date);
  }

  private void setCurrentContact(Label thisLabel, ObservableList<Node> messageList, ScrollPane messageScroll) {
    try {
      currentContact = thisLabel.getText();
      messageList.clear();
      ArrayList<Row> rowArrayList = new ArrayList<>();

      if (conversation.containsKey(thisLabel.getText())) {
        rowArrayList.addAll(conversation.get(thisLabel.getText()));
      } else {
        rowArrayList.addAll(ORM.readMessages(currentContact));
        conversation.put(thisLabel.getText(), rowArrayList);
      }

      for (Row row : rowArrayList) {
        if (row.getLogin_who().equals(userLogin)) {
          addMessage(row.getMessage(), row.getDate(), messageList, true);
        } else {
          addMessage(row.getMessage(), row.getDate(), messageList, false);
        }
      }

      Platform.runLater(() -> messageScroll.setVvalue(1));

      for (Label label : contacts) {
        label.setTextFill(Paint.valueOf("black"));
      }
      thisLabel.setTextFill(Paint.valueOf("chartreuse"));
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void getLastDate() {
    lastSendingDate = new SimpleDateFormat("HH:mm:ss dd/MM/yy").format(new Date());
  }

  private void removeExtraElement(ObservableList... listing) {
    for (ObservableList list : listing) {
      if (list.size() == 2) {
        list.remove(1);
      }
    }
  }

  static void createInterface() {
    launch();
  }
}

