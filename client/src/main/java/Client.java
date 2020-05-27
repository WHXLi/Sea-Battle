import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass()
                .getResource("menu.fxml"));
        primaryStage.getIcons().add(new Image("images/icon.png"));
        primaryStage.setTitle("Морской бой");
        primaryStage.setScene(new Scene(parent,400,400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
