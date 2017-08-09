
class Row {
  private String login_who;
  private String message;
  private String date;

  Row(String login_who, String message, String date) {
    this.login_who = login_who;
    this.message = message;
    this.date = date;
  }

  String getLogin_who() {return this.login_who;}

  String getMessage() {return this.message;}

  String getDate() {return this.date;}
}
