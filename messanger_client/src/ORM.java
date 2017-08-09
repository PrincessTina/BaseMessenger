import com.sun.rowset.CachedRowSetImpl;
import javafx.scene.control.Label;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

class ORM {
  static Connection connection;
  private static int id = 0;

  static void add(String... args) throws Exception {
    fixConnection();

    StringBuilder request = new StringBuilder();
    switch (args.length) {
      //Add Contact
      case 1:
        request.append("insert into contacts values((select id from users where login = '");
        request.append(Interface.userLogin);
        request.append("'), (select id from users where login = '");
        request.append(args[0]);
        request.append("'));");
        break;

      //Add user
      case 2:
        request.append("insert into users(login, password) values('");
        request.append(args[0]);
        request.append("', '");
        request.append(args[1]);
        request.append("');");
        break;

      //Add message
      case 3:
        request.append("insert into messages(id_who, id_whose, message, date) values((select id from users where login = '");
        request.append(Interface.userLogin);
        request.append("'), (select id from users where login = '");
        request.append(args[0]);
        request.append("'), '");
        request.append(args[1]);
        request.append("', '");
        request.append(args[2]);
        request.append("');");
    }

    connection.prepareStatement(request.toString()).execute();
  }

  static ArrayList<Row> readMessages(String currentContact) throws Exception {
    fixConnection();

    ArrayList<Row> searchedList = new ArrayList<>();
    StringBuilder request = new StringBuilder();
    CachedRowSet resultSet = new CachedRowSetImpl();
    Statement statement = connection.createStatement();

    request.append("select login, message, date from messages inner join users on(id_who = users.id) where login in('");
    request.append(Interface.userLogin);
    request.append("', '");
    request.append(currentContact);
    request.append("') and id_whose in(select id from users where login in('");
    request.append(Interface.userLogin);
    request.append("', '");
    request.append(currentContact);
    request.append("'));");

    resultSet.populate(statement.executeQuery(request.toString()));
    while (resultSet.next()) {
      searchedList.add(new Row(resultSet.getString(1), resultSet.getString(2),
          resultSet.getString(3)));
    }
    return searchedList;
  }

  static TreeSet<Label> readContacts(String myLogin) throws Exception {
    fixConnection();

    TreeSet<Label> searchedSet = new TreeSet<>(new LabelComparator());
    CachedRowSet resultSet = new CachedRowSetImpl();

    resultSet.populate(connection.createStatement().executeQuery("select login from contacts inner join users " +
        "on(id_whose = id) where id_who = (select id from users where login = '" + myLogin + "');"));

    while (resultSet.next()) {
      searchedSet.add(new Label(resultSet.getString(1)));
    }
    return searchedSet;
  }

  static ArrayList<String> checkMessages(String currentContact) throws Exception {
    fixConnection();

    ArrayList<String> searchedList = new ArrayList<>();
    StringBuilder request = new StringBuilder();
    CachedRowSet resultSet = new CachedRowSetImpl();
    Statement statement = connection.createStatement();

    request.append("select * from messages where id_who = (select id from users where login = '");
    request.append(currentContact);
    request.append("') and id_whose = (select id from users where login = '");
    request.append(Interface.userLogin);
    request.append("') and id > ");
    if (id != 0) {
      request.append(id);
    } else {
      request.append("(select id from messages where id_who = (select id from users where login = '");
      request.append(Interface.userLogin);
      request.append("') and id_whose = (select id from users where login = '");
      request.append(currentContact);
      request.append("') order by id desc limit 1)");
    }
    request.append(";");
    resultSet.populate(statement.executeQuery(request.toString()));

    while (resultSet.next()) {
      searchedList.add(resultSet.getString(4));
      searchedList.add(resultSet.getString(5));
      id = resultSet.getInt(1);
    }
    return searchedList;
  }

  static boolean checkAccount(String... args) throws Exception {
    fixConnection();

    boolean answer;
    StringBuilder builder = new StringBuilder();
    CachedRowSet resultSet = new CachedRowSetImpl();
    Statement statement = connection.createStatement();

    builder.append("select 1 from users where login = ");
    builder.append(args[0]);

    if (args.length > 1) {
      builder.append(" and password = ");
      builder.append(args[1]);
    }
    builder.append(";");

    resultSet.populate(statement.executeQuery(builder.toString()));

    switch (resultSet.size()) {
      case 1:
        answer = true;
        break;
      default:
        answer = false;
    }
    return answer;
  }

  private static void fixConnection() throws Exception {
    if (connection.isClosed()) {
      connection = DataBaseController.connection();
    }
  }
}
