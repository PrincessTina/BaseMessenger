import javafx.scene.control.Label;

import java.util.Comparator;

class LabelComparator implements Comparator<Label> {
    public int compare(Label a, Label b){
      return a.getText().compareTo(b.getText());
    }
}
