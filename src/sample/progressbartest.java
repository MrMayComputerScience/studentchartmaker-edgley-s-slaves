package sample;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ProgressBar;

public class progressbartest  extends Application {

    final Float[] values = new Float[] {-1.0f, 0f, 0.6f, 1.0f};
    final Label [] labels = new Label[values.length];
    final ProgressBar[] pbs = new ProgressBar[values.length];
    final ProgressIndicator[] pins = new ProgressIndicator[values.length];
    final HBox hbs [] = new HBox [values.length];

    @Override
    public void start(Stage stage) {
        Group root = new Group();

        HBox hb = new HBox(1);


        Scene scene = new Scene(root, 300, 150);
        //scene.getStylesheets().add("progresssample/Style.css");

        stage.setScene(scene);
        stage.setTitle("Progress Controls");

        ProgressBar pb = new ProgressBar();
        pb.setProgress(.1);

        ProgressIndicator pi = new ProgressIndicator();
        pi.setProgress(.1);

        hb.getChildren().addAll(pb, pi);



        /*
        for (int i = 0; i < values.length; i++) {
            final Label label = new Label();
            label.setText("progress");
            System.out.println(values[i]);

            final ProgressBar pb  = new ProgressBar(.6);
            pb.setProgress(.5);
            System.out.println(pbs[i]);

            final ProgressIndicator pin = new ProgressIndicator(.7);
            pin.setProgress(.5);

            System.out.println("teststst:  "+hbs[i]);

            hb.setSpacing(5);
            hb.setAlignment(Pos.CENTER);

        }
*/

        final VBox vb = new VBox();
        vb.setSpacing(5);
        //vb.add(p2);
        scene.setRoot(vb);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}