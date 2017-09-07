import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
  private boolean isButtonFront = false;
  static String userLogin;
  private String currentContact = "null";
  private String lastSendingDate;
  private TreeSet<Label> contacts = new TreeSet<>(new LabelComparator());
  private HashMap<String, ArrayList<Row>> conversation = new HashMap<>();
  private int lastMessageOfCurrentContactId;
  private int lastMessageId = 0;
  private int lineCount = 0;
  private int count = 0;

  public void start(Stage stage) throws Exception {
    loginWindow(stage);
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
    login.setId("blackBorder");

    PasswordField password1 = new PasswordField();
    password1.setPrefSize(380, 40);
    password1.setPromptText("Введите пароль");
    password1.setFocusTraversable(false);
    password1.setId("blackBorder");

    PasswordField password2 = new PasswordField();
    password2.setPrefSize(380, 40);
    password2.setPromptText("Повторите пароль");
    password2.setFocusTraversable(false);
    password2.setId("blackBorder");

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

    Scene scene = new Scene(vBox, 445, 340);
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
      } else if (event.getCode().equals(KeyCode.ENTER)) {
        entrance(password1List, loginList, password2List, password1, password2, login, tick1, cross1, tick2, cross2, tick, cross, stage);
      } else {
        removeExtraElement(password1List);
      }
    });

    password2.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.TAB)) {
        button.setFocusTraversable(true);
      } else if (event.getCode().equals(KeyCode.ENTER)) {
        entrance(password1List, loginList, password2List, password1, password2, login, tick1, cross1, tick2, cross2, tick, cross, stage);
      } else {
        removeExtraElement(password2List);
      }
    });

    button.setOnKeyPressed(event -> {
      if (event.getCode().equals(KeyCode.TAB)) {
        login.setFocusTraversable(true);
      }
    });

    button.setOnAction(event -> entrance(password1List, loginList, password2List, password1, password2, login, tick1, cross1, tick2, cross2, tick, cross, stage));

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
    text.setId("label");
    Text column = new Text();
    Text someText = new Text("Здесь должна быть ваша переписка");
    someText.setId("label");

    TextField searchLine = new TextField();
    searchLine.setPromptText("Поиск...");
    searchLine.setPrefHeight(15);
    searchLine.setFocusTraversable(false);
    searchLine.setEditable(true);
    searchLine.setId("line");

    TextField addField = new TextField();
    addField.setPrefSize(300, 15);
    addField.setPromptText("Добавить контакт...");
    addField.setFocusTraversable(false);
    addField.setId("line");

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

    Button button = new Button("Новое сообщение");

    StackPane stackPane = new StackPane(messageScroll, button);
    button.toBack();
    StackPane.setMargin(button, new Insets(510, 200, 0, 250));

    GridPane contactsBox = new GridPane();
    ColumnConstraints column1 = new ColumnConstraints();
    ColumnConstraints column2 = new ColumnConstraints();
    column1.setPrefWidth(25);
    column2.setPrefWidth(325);
    contactsBox.getColumnConstraints().addAll(column1, column2);
    contactsBox.setVgap(20);

    //Достаем все имеющиеся контакты
    contacts.addAll(ORM.readContacts(userLogin));

    for (Label label : contacts) {
      VBox.setMargin(label, new Insets(20, 20, 0, 20));
      label.setPrefWidth(300);
      label.setId("contact");
      label.setAccessibleText("null");

      ArrayList<Row> rowArrayList = new ArrayList<>();
      rowArrayList.addAll(ORM.readMessages(label.getText()));
      conversation.put(label.getText(), rowArrayList);

      if (!rowArrayList.isEmpty()) {
        if (rowArrayList.get(rowArrayList.size() - 1).getId() > lastMessageId) {
          lastMessageId = rowArrayList.get(rowArrayList.size() - 1).getId();
        }
      }

      contactsBox.add(label, 1, lineCount);
      lineCount++;

      label.setOnMouseClicked(subEvent -> setCurrentContact(label, messageList, messageScroll, button, contactsBox));
      label.setOnMouseExited(subEvent -> contactOnMouseExited(label, false));
      label.setOnMouseEntered(subEvent -> contactOnMouseEntered(label));
    }

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
    centerBox.setPrefWidth(80);
    centerBox.setPadding(new Insets(20, 0, 20, 0));
    centerBox.setStyle("-fx-background-color: linear-gradient(#303030, #303030, #68b800, #a2f51d, #68b800, #303030, #303030)");

    VBox rightVBox = new VBox(someText, stackPane, sendBox);
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
    Thread thread = new Thread(() -> {
      ArrayList<Row> rowArrayList = new ArrayList<>();

      while (stage.isShowing()) {
        try {
          if (!currentContact.equals("null")) {
            if (conversation.containsKey(currentContact)) {
              if (conversation.get(currentContact).size() > 0) {
                lastMessageOfCurrentContactId = conversation.get(currentContact).get(conversation.get(currentContact).size() - 1).getId();
              }
            } else {
              lastMessageOfCurrentContactId = 0;
            }

            rowArrayList.addAll(ORM.checkMessages(currentContact, lastMessageOfCurrentContactId));

            if (rowArrayList.size() > 0) {
              Platform.runLater(() -> {
                for (Row row : rowArrayList) {
                  if (row.getId() > lastMessageOfCurrentContactId) {
                    addMessage(row.getMessage(), row.getDate(), messageList, false, true);
                    conversation.get(currentContact).add(row);
                  }
                }
                if ((messageScroll.getVvalue() != 1) && (!isButtonFront)) {
                  button.toFront();
                  isButtonFront = true;

                  Thread thread1 = new Thread(() -> {
                    while (stage.isShowing()) {
                      if (messageScroll.getVvalue() == 1) {
                        Platform.runLater(button::toBack);
                        isButtonFront = false;
                        break;
                      }
                    }
                  });
                  thread1.start();
                }
                rowArrayList.clear();
              });
            }
          }

          for (Map.Entry<String, Integer> contact : ORM.checkMessagesFromOthers(currentContact, lastMessageId).entrySet()) {
            if (contact.getValue() > lastMessageId) {
              lastMessageId = contact.getValue();
            }

            if (conversation.containsKey(contact.getKey())) {
              count = 0;

              for (Label label : contacts) {
                if (label.getText().equals(contact.getKey())) {
                  Platform.runLater(() ->
                      addCircle(contact.getKey(), contactsBox, count));
                  break;
                }
                count++;
              }
              System.out.println(contact.getKey());
            } else {
              Platform.runLater(() -> {
                addCircle(contact.getKey(), contactsBox, lineCount);
                addNewContact(contact.getKey(), contactsBox, button, messageScroll, messageList);
              });
            }
          }
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
      }
    });
    thread.start();

    //Events
    button.setOnAction(event -> {
      messageScroll.setVvalue(1.0);
      button.toBack();
    });

    send.setOnMousePressed(event -> sendMessage(sendArea, messageList));

    send.setOnMouseReleased(event -> messageScroll.setVvalue(1));

    searchLine.setOnKeyReleased(event -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        double position, i = 1;
        for (Label label : contacts) {
          if (label.getText().equals(searchLine.getText())) {
            setCurrentContact(label, messageList, messageScroll, button, contactsBox);
            position = i;
            contactsScroll.setVvalue(position / contacts.size());
          }
          i++;
        }
      }
    });

    addField.setOnKeyPressed(event -> {
      try {
        removeExtraElement(addList);
        if (event.getCode().equals(KeyCode.ENTER)) {
          if ((ORM.checkAccount("'" + addField.getText() + "'")) && (
              !contacts.contains(new Label(addField.getText()))) && (!addField.getText().equals(userLogin))) {
            Label newContact = addNewContact(addField.getText(), contactsBox, button, messageScroll, messageList);

            addField.clear();
            addList.add(tick);

            setCurrentContact(newContact, messageList, messageScroll, button, contactsBox);
          } else {
            addList.add(cross);
          }
        }
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    });
  }

  private void entrance(ObservableList<Node> password1List, ObservableList<Node> loginList, ObservableList<Node> password2List,
                        PasswordField password1, PasswordField password2, TextField login, ImageView tick1, ImageView cross1,
                        ImageView tick2, ImageView cross2, ImageView tick, ImageView cross, Stage stage) {
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
        long startTime = System.currentTimeMillis();
        mainWindow(stage);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println(estimatedTime * 0.001);
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void addCircle(String text, GridPane contactsBox, int digit) {
    Circle circle = new Circle();
    circle.setRadius(5);
    circle.setCenterX(20);
    circle.setFill(Color.web("#cb45b1"));
    circle.setAccessibleText(text);

    contactsBox.add(circle, 0, digit);
  }

  private Label addNewContact(String text, GridPane contactsBox, Button button, ScrollPane messageScroll, ObservableList<Node> messageList) {
    Label newContact = new Label(text);
    newContact.setId("contact");
    newContact.setAccessibleText("null");
    VBox.setMargin(newContact, new Insets(20, 20, 0, 20));
    newContact.setPrefWidth(300);

    try {
      ORM.add(text);
      contacts.add(newContact);

      contactsBox.add(newContact, 1, lineCount);
      lineCount++;

      newContact.setOnMouseClicked(subEvent -> setCurrentContact(newContact, messageList, messageScroll, button, contactsBox));
      newContact.setOnMouseExited(subEvent -> contactOnMouseExited(newContact, false));
      newContact.setOnMouseEntered(subEvent -> contactOnMouseEntered(newContact));
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return newContact;
  }

  private void sendMessage(TextArea sendArea, ObservableList<Node> messageList) {
    try {
      if ((sendArea.getText().length() > 0) && (!currentContact.isEmpty())) {
        getLastDate();
        ORM.add(currentContact, sendArea.getText(), lastSendingDate);
        addMessage(sendArea.getText(), lastSendingDate, messageList, true, true);
        sendArea.clear();
      }
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void addMessage(String messageText, String dateText, ObservableList<Node> messageList, boolean keyISent, boolean keyNewMessage) {
    try {
      Label message = new Label(messageText);
      message.setAccessibleText(messageText);
      double x, distance, length = message.getText().length();

      if (length <= 45) {
        x = length * 11;
        distance = 600 - x;
      } else {
        x = 450;
        distance = 150;
      }

      message.setPrefWidth(x);
      message.setWrapText(true);

      Label date = new Label(dateText);
      date.setAccessibleText(dateText);

      if (keyISent) {
        if (keyNewMessage) {
          conversation.get(currentContact).add(new Row(ORM.getLastId(userLogin, currentContact), userLogin, messageText, dateText));
          lastMessageId = ORM.getLastId(userLogin, currentContact);
        }
        message.setAlignment(Pos.TOP_CENTER);
        date.setAlignment(Pos.TOP_RIGHT);
        VBox.setMargin(message, new Insets(20, 0, 0, distance));
        VBox.setMargin(date, new Insets(0, 0, 0, 485));
        message.setStyle("-fx-font-size: 16px; -fx-background-color: #A2F51D;" +
            " -fx-background-radius:  0px 20px 20px 10px;");
      } else {
        message.setAlignment(Pos.TOP_CENTER);
        date.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(message, new Insets(20, distance, 0, 10));
        VBox.setMargin(date, new Insets(0, 470, 0, 10));
        message.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: #303030;" +
            " -fx-background-radius:  0px 20px 20px 20px;");
      }
      date.setStyle("-fx-font-size: 16px; -fx-font-family: 'Cambria'");

      messageList.addAll(message, date);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void contactOnMouseExited(Label thisLabel, boolean key) {
    if ((!thisLabel.getText().equals(currentContact)) || key) {
      thisLabel.setStyle("-fx-font-size: 20px; -fx-border-style: hidden hidden solid hidden;" +
          "-fx-border-color: #262626; -fx-font-family: \"Monotype Corsiva\", serif; -fx-text-fill: black");
    }
  }

  private void contactOnMouseEntered(Label thisLabel) {
    if (!thisLabel.getText().equals(currentContact)) {
      thisLabel.setStyle("-fx-font-size: 20px; -fx-border-style: hidden hidden solid hidden;" +
          "-fx-border-color: #cb45b1; -fx-font-family: \"Monotype Corsiva\", serif; -fx-text-fill: #96127d");
    }
  }

  private void setCurrentContact(Label thisLabel, ObservableList<Node> messageList, ScrollPane messageScroll,
                                 Button button, GridPane contactsBox) {
    try {
      button.toBack();

      for (Label label : contacts) {
        if (label.getText().equals(currentContact)) {
          contactOnMouseExited(label, true);
        }
      }
      currentContact = thisLabel.getText();
      thisLabel.setStyle("-fx-font-size: 20px; -fx-border-style: hidden hidden solid hidden;" +
          "-fx-text-fill: green; -fx-border-color: #A2F51D; -fx-font-family: \"Monotype Corsiva\", fantasy;");

      //Удаляем значок нового сообщения
      count = 1000;
      for (Label label : contacts) {
        if ((label.getText().equals(currentContact)) && (contactsBox.getChildren().size() > contacts.size())) {
          for (int i = 0; i < contactsBox.getChildren().size(); i++) {
            if (contactsBox.getChildren().get(i).getAccessibleText().equals(currentContact)) {
              count = i;
            }
          }
          if (count != 1000) {
            contactsBox.getChildren().remove(count);
            break;
          }
        }
      }

      messageList.clear();
      ArrayList<Row> rowArrayList = new ArrayList<>();

      if (conversation.containsKey(currentContact)) {
        rowArrayList.addAll(conversation.get(currentContact));
      } else {
        rowArrayList.addAll(ORM.readMessages(currentContact));
        conversation.put(currentContact, rowArrayList);
      }

      for (Row row : rowArrayList) {
        if (row.getLogin_who().equals(userLogin)) {
          addMessage(row.getMessage(), row.getDate(), messageList, true, false);
        } else {
          addMessage(row.getMessage(), row.getDate(), messageList, false, false);
        }
      }

      Platform.runLater(() -> messageScroll.setVvalue(1));
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void getLastDate() {
    lastSendingDate = new SimpleDateFormat("HH:mm dd/MM/yy").format(new Date());
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

