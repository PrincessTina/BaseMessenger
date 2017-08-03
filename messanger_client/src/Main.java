
public class Main {
  public static void main(String[] args) {
    try {
      ORM.connection = DataBaseController.connection();
      Interface.createInterface();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }
}
