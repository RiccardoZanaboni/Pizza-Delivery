package graphicElements.pizzeriaSidePages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Pizzeria;

public class PizzeriaHomePage {

    public void display (Pizzeria pizzeria,Stage window) {

        Label label = new Label("PIZZERIA");
        Button logoutButton = new Button();
        Image image = new Image("graphicElements/images/logout-128.png");
        ImageView imageView1 = new ImageView(image);
        imageView1.setFitHeight(20);
        imageView1.setFitWidth(20);
        logoutButton.setGraphic(imageView1);
        //logoutButton.setMinSize(100, 50);
        logoutButton.setOnAction(e->{
            window.close();
        });
        HBox hBox=new HBox(10);
        hBox.getChildren().addAll(label,logoutButton);
        hBox.setAlignment(Pos.CENTER);

        Button visualizeOrdersButton = new Button("Visualizza Ordini");
        visualizeOrdersButton.setOnAction(e-> {
            //PizzeriaOrdersPage pizzeriaOrderPage = new PizzeriaOrdersPage();
            pizzeria.updatePizzeriaToday();
            PizzeriaOrdersPage.display(pizzeria, window);
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
        layout.getChildren().addAll(hBox, gridPane);
        Scene scene = new Scene(layout, 880, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/pizzeriaHomePage.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
}
