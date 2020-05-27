import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FieldController {
    public Label enemyLabel;
    public GridPane enemyGrid;
    public Label myLabel;
    public GridPane myGrid;



    public void fieldInit(int fieldSize){
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                enemyGrid.add(new Button(), i, j);
            }
        }
    }
}
