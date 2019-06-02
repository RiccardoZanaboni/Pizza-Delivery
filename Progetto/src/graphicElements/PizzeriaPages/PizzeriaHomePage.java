package graphicElements.PizzeriaPages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Pizzeria;

public class PizzeriaHomePage {
    private Stage window = new Stage();

    public void display (Pizzeria pizzeria) {

        Label label = new Label("PIZZERIA");

        Button visualizeOrdersButton = new Button("Visualizza Ordini");
        visualizeOrdersButton.setOnAction(e-> {
            //PizzeriaOrderPage pizzeriaOrderPage = new PizzeriaOrderPage();
            PizzeriaOrderPage.display(pizzeria, window);
        });
        visualizeOrdersButton.prefWidthProperty().bind(window.widthProperty());
        visualizeOrdersButton.prefHeightProperty().bind(window.heightProperty());

        Button manageMenuButton = new Button("Gestisci Menu");
        manageMenuButton.prefWidthProperty().bind(window.widthProperty());
        manageMenuButton.prefHeightProperty().bind(window.heightProperty());
        manageMenuButton.setOnAction(e-> {
            PizzeriaMenuPage pizzeriaMenuPage = new PizzeriaMenuPage();
            pizzeriaMenuPage.display(pizzeria, window);
        });

        Button manageDeployeesButton = new Button("Gestisci personale");
        manageDeployeesButton.prefWidthProperty().bind(window.widthProperty());
        manageDeployeesButton.prefHeightProperty().bind(window.heightProperty());

        Button altroButton = new Button("Altro");
        altroButton.prefWidthProperty().bind(window.widthProperty());
        altroButton.prefHeightProperty().bind(window.heightProperty());

        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(visualizeOrdersButton, manageDeployeesButton, manageMenuButton, altroButton);
        GridPane.setConstraints(visualizeOrdersButton, 0, 0);
        GridPane.setConstraints(manageDeployeesButton, 1, 0);
        GridPane.setConstraints(manageMenuButton, 0, 1);
        GridPane.setConstraints(altroButton, 1, 1);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, gridPane);
        Scene scene = new Scene(layout, 880, 600);
        window.setScene(scene);
        window.show();
    }
}
