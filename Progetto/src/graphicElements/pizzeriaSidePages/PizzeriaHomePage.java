package graphicElements.pizzeriaSidePages;

import graphicElements.customerSidePages.loginPages.LoginAccountPage;
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

    /** Lo Stage mostra la HomePage dal lato della Pizzeria, una volta che questa si sia
     * correttamente autenticata come tale. */
    public void display (Pizzeria pizzeria,Stage window) {
        window.setTitle("Wolf of Pizza - Home");
        Label label = new Label("PIZZERIA");
        Button logoutButton = new Button();
        Image image = new Image("graphicElements/images/logout-128.png");
        ImageView imageView1 = new ImageView(image);
        imageView1.setFitHeight(20);
        imageView1.setFitWidth(20);

        /* Bottone di Logout */
        logoutButton.setGraphic(imageView1);
        logoutButton.setOnAction(e->{
            LoginAccountPage loginAccountPage = new LoginAccountPage();
            loginAccountPage.display(window,pizzeria);
        });
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(label,logoutButton);
        hBox.setAlignment(Pos.CENTER);

        /* Bottone per visualizzare l'elenco degli ordini da evadere oggi */
        Button visualizeOrdersButton = new Button("Visualizza \n  Ordini");
        visualizeOrdersButton.setOnAction(e-> {
            pizzeria.updatePizzeriaToday();
            PizzeriaVisualizeOrdersPage.display(pizzeria, window);
        });
        visualizeOrdersButton.prefWidthProperty().bind(window.widthProperty());
        visualizeOrdersButton.prefHeightProperty().bind(window.heightProperty());

        /* Bottone per modificare il menu */
        Button manageMenuButton = new Button("Gestisci Menu");
        manageMenuButton.prefWidthProperty().bind(window.widthProperty());
        manageMenuButton.prefHeightProperty().bind(window.heightProperty());
        manageMenuButton.setOnAction(e-> {
            PizzeriaModifyMenuPage pizzeriaMenuPage = new PizzeriaModifyMenuPage();
            pizzeriaMenuPage.display(pizzeria, window);
        });

        Button mandaEmail = new Button("Manda E-Mail");
        mandaEmail.prefWidthProperty().bind(window.widthProperty());
        mandaEmail.prefHeightProperty().bind(window.heightProperty());
        mandaEmail.setOnAction(e-> {
           SendMailPage sendMailPage =new SendMailPage();
            sendMailPage.display(window,pizzeria);
        });




        GridPane gridPane = new GridPane();
        gridPane.getChildren().addAll(visualizeOrdersButton, manageMenuButton,mandaEmail);
        GridPane.setConstraints(visualizeOrdersButton, 0, 0);
        GridPane.setConstraints(manageMenuButton, 1, 0);
        GridPane.setConstraints(mandaEmail, 2, 0);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(hBox, gridPane);
        Scene scene = new Scene(layout, 800, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("/graphicElements/cssStyle/pizzeriaHomePage.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }
}
