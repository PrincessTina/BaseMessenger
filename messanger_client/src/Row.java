
class Row {
  private int id;
  private String login_who;
  private String message;
  private String date;

  Row(int id, String login_who, String message, String date) {
    this.id = id;
    this.login_who = login_who;
    this.message = message;
    this.date = date;
  }

  String getLogin_who() {return this.login_who;}

  String getMessage() {return this.message;}

  String getDate() {return this.date;}

  int getId() {return this.id;}
}
