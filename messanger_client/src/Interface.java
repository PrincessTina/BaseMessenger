import javafx.application.*;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.io.FileInputStream;
import java.util.Objects;

//ToDo: Попробовать переписать события для заголовков начального окна через css
public class Interface extends Application {
  private boolean key;

  public void start(Stage stage) throws Exception {
    //loginWindow(stage);
   // chatOnlineWindow(stage);
    mainWindow(stage);
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
        BlurType.THREE_PASS_BOX, Color.color(0,0,0,0.7) , 6, 0.0 , 0 , 2)));

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
        BlurType.THREE_PASS_BOX, Color.color(0,0,0,0.7) , 6, 0.0 , 0 , 2)));

    button.setOnAction(event -> {
      int ticksCounter = 0;
      try {
        if (key) {
          ORM.setSettings("PersonalData");
          PersonalData data = new PersonalData();
          data.login = login.getText();
          data.password = password2.getText();

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

          if ((login.getText().length() > 0) && (!ORM.checkAccount("'" + login.getText() + "'"))) {
            loginList.add(tick);
            ticksCounter++;
          } else {
            loginList.add(cross);
          }

          if (ticksCounter == 3) {
            ORM.add(data.toString());
            chatsWindow(stage);
          }
        } else if (!ORM.checkAccount("'" + login.getText() + "'", "'" + password1.getText() + "'")) {
          removeExtraElement(password1List, loginList, password2List);
          loginList.add(cross);
          password1List.add(cross1);
        } else {
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

    Text contacts = new Text("Контакты");
    Text column = new Text();
    Text someText = new Text("Здесь должна быть ваша переписка");
    Text fishText2 = new Text("Задача организации, в особенности же реализация намеченных плановых заданий требуют определения и уточнения существенных финансовых и административных условий. Равным образом новая модель организационной деятельности позволяет оценить значение новых предложений.\n" +
        "\n" +
        "С другой стороны укрепление и развитие структуры в значительной степени обуславливает создание систем массового участия. Повседневная практика показывает, что дальнейшее развитие различных форм деятельности обеспечивает широкому кругу (специалистов) участие в формировании дальнейших направлений развития. Идейные соображения высшего порядка, а также начало повседневной работы по формированию позиции в значительной степени обуславливает создание дальнейших направлений развития. Идейные соображения высшего порядка, а также новая модель организационной деятельности требуют от нас анализа систем массового участия. Идейные соображения высшего порядка, а также консультация с широким активом играет важную роль в формировании системы обучения кадров, соответствует насущным потребностям.\n" +
        "\n" +
        "Идейные соображения высшего порядка, а также укрепление и развитие структуры обеспечивает широкому кругу (специалистов) участие в формировании модели развития. Равным образом укрепление и развитие структуры представляет собой интересный эксперимент проверки форм развития. Не следует, однако забывать, что дальнейшее развитие различных форм деятельности в значительной степени обуславливает создание дальнейших направлений развития.\n" +
        "\n" +
        "Значимость этих проблем настолько очевидна, что сложившаяся структура организации представляет собой интересный эксперимент проверки модели развития. Не следует, однако забывать, что рамки и место обучения кадров требуют от нас анализа соответствующий условий активизации. С другой стороны новая модель организационной деятельности представляет собой интересный эксперимент проверки направлений прогрессивного развития.\n" +
        "\n" +
        "Значимость этих проблем настолько очевидна, что укрепление и развитие структуры требуют определения и уточнения дальнейших направлений развития. С другой стороны консультация с широким активом способствует подготовки и реализации систем массового участия.\n" +
        "\n");
    fishText2.setWrappingWidth(600);

    TextField searchLine = new TextField();
    searchLine.setPromptText("Поиск...");
    searchLine.setPrefHeight(15);
    searchLine.setFocusTraversable(false);

    TextField addField = new TextField();
    addField.setPrefSize(500, 15);
    addField.setPromptText("Добавить контакт...");
    addField.setFocusTraversable(false);

    TextArea messageArea = new TextArea();
    messageArea.setPrefSize(600, 80);
    messageArea.setWrapText(true);
    messageArea.setPromptText("Напишите сообщение...");
    messageArea.setFocusTraversable(false);

    Button sendButton = new Button("Отправить");
    sendButton.setFocusTraversable(false);

    ImageView add = new ImageView(new Image(new FileInputStream("..\\6.png")));
    add.setFitHeight(30);
    add.setFitWidth(30);

    VBox messageBox = new VBox(fishText2);
    ObservableList<Node> messageList = messageBox.getChildren();
    messageBox.setPrefHeight(600);

    VBox contactsBox = new VBox();
    ObservableList<Node> contactList = contactsBox.getChildren();
    contactsBox.setPrefSize(590, 600);

    ScrollPane messageScroll = new ScrollPane();
    messageScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    messageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    messageScroll.setContent(messageBox);

    ScrollPane contactsScroll = new ScrollPane();
    contactsScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    contactsScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    contactsScroll.setContent(contactsBox);
    contactsScroll.setId("scroll");

    HBox rightScrollBox = new HBox(messageBox, messageScroll);

    HBox leftScrollBox = new HBox(contactsScroll, contactsBox);

    HBox sendBox = new HBox(messageArea, sendButton);
    sendBox.setSpacing(20);

    HBox addBox = new HBox();
    ObservableList<Node> addList = addBox.getChildren();
    addList.addAll(addField, add);
    addBox.setSpacing(20);

    VBox leftVBox = new VBox(contacts, searchLine, leftScrollBox, addBox);
    leftVBox.setSpacing(20);
    leftVBox.setPadding(new Insets(20, 0, 20, 20));

    VBox centerBox = new VBox(column);
    centerBox.setPrefSize(80, 740);

    VBox rightVBox = new VBox(someText, rightScrollBox, sendBox);
    rightVBox.setSpacing(20);
    rightVBox.setPadding(new Insets(20, 20, 20, 0));

    HBox mainBox = new HBox(leftVBox, centerBox, rightVBox);
    mainBox.setSpacing(20);

    Scene scene = new Scene(mainBox, 1480, 740);
    scene.getStylesheets().add(Main.class.getResource("style2.css").toExternalForm());
    stage.setScene(scene);
    stage.setTitle("Вход");
    stage.show();

    //Events
    add.setOnMouseClicked(event -> {
      try {
        if (ORM.checkAccount("'" + addField.getText() + "'")) {
          Label newContact = new Label();
          newContact.setText(addField.getText());
          newContact.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-border-color: #262626");
          newContact.setPrefWidth(600);

          newContact.setOnMouseClicked(subEvent -> {
            Text text = new Text(newContact.getText());
            messageList.add(text);
          });

          VBox.setMargin(newContact, new Insets(20, 20, 0, 20));

          contactList.add(newContact);
          addField.clear();
        } else {
          addList.remove(add);
          addList.add(cross);
        }
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }
    });

    addField.setOnKeyPressed(event -> {
      addList.remove(1);
      addList.add(add);
    });
  }

  private void removeExtraElement(ObservableList... listing) {
    for (ObservableList list : listing) {
      if (list.size() == 2) {
        list.remove(1);
      }
    }
  }

  private void chatsWindow(Stage stage) {
    try {
      ImageView addChat = new ImageView(new Image(new FileInputStream("..\\plus3.png")));
      addChat.setFitHeight(60);
      addChat.setFitWidth(60);
      addChat.setStyle("-fx-opacity: 0.9");

      // List of created chats
      Text text = new Text("Ку");
      Text text2 = new Text("Ку");
      Text text3 = new Text("Ку");
      Text text4 = new Text("Ку");
      Text text5 = new Text("Ку");
      Text text6 = new Text("Ку");
      Text text7 = new Text("Ку");
      Text text8 = new Text("Ку");

      VBox.setMargin(text, new Insets(20, 20, 10, 20));
      VBox.setMargin(text2, new Insets(10, 20, 10, 20));
      VBox.setMargin(text3, new Insets(10, 20, 10, 20));
      VBox.setMargin(text4, new Insets(10, 10, 10, 20));
      VBox.setMargin(text5, new Insets(10, 20, 10, 20));
      VBox.setMargin(text6, new Insets(10, 20, 10, 20));
      VBox.setMargin(text7, new Insets(10, 20, 10, 20));
      VBox.setMargin(text8, new Insets(10, 20, 10, 20));

      // Column for chats
      VBox vBox = new VBox();
      vBox.getChildren().addAll(text, text2, text3, text4, text5, text6, text7, text8);
      vBox.setManaged(false);

      // Table for chats and button of adding
      BorderPane pane = new BorderPane();
      BorderPane.setAlignment(vBox, Pos.TOP_LEFT);
      BorderPane.setAlignment(addChat, Pos.BOTTOM_RIGHT);
      pane.setLeft(vBox);
      pane.setRight(addChat);

      Scene scene = new Scene(pane, 440, 460);
      stage.setScene(scene);
      stage.show();

      addChat.setOnMouseMoved(event -> addChat.setStyle("-fx-opacity: 1"));
      addChat.setOnMouseExited(event -> addChat.setStyle("-fx-opacity: 0.9"));
      addChat.setOnMouseClicked(event -> writeWhoWindow(new Stage(), vBox));

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  private void writeWhoWindow(Stage stage, VBox vBox) {
    Text text = new Text("С кем начать диалог ?");
    TextField inputLogin = new TextField();
    inputLogin.setPromptText("Введите логин");
    Button ok = new Button("Oк");

    VBox.setMargin(text, new Insets(20, 20, 10, 20));
    VBox.setMargin(inputLogin, new Insets(10, 20, 10, 20));
    VBox.setMargin(ok, new Insets(10, 20, 10, 20));

    VBox root = new VBox();
    root.getChildren().addAll(text, inputLogin, ok);

    Scene scene = new Scene(root, 280, 160);
    stage.setScene(scene);
    stage.show();

    ok.setOnAction(event -> {
      Text text1 = new Text(inputLogin.getText());
      VBox.setMargin(text1, new Insets(10, 20, 10, 20));
      vBox.getChildren().add(text1);
    });
  }

  static void createInterface() {
    launch();
  }
}

