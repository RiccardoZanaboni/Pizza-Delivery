package graphicElements;

import graphicElements.PizzeriaPages.PizzeriaHomePage;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Pizzeria;

public class ProfilePage {

    public void display(Stage window, Pizzeria pizzeria) {

        Button pizzeriaButton = new Button("PIZZERIA (cliccare prima questo)");
        pizzeriaButton.setMinSize(200, 200);
        pizzeriaButton.setOnAction(e->{
            PizzeriaHomePage pizzeriaHomePage = new PizzeriaHomePage();
            pizzeriaHomePage.display(pizzeria);
        });

        Button userButton = new Button("USER (e poi questo)");
        userButton.setMinSize(200, 200);
        userButton.setOnAction(e->{
            MenuPage menuPage = new MenuPage();
            menuPage.display(window, pizzeria);
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(pizzeriaButton, userButton);
        layout.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if(ke.getCode()== KeyCode.DOWN) {
                    userButton.fire();
                }if(ke.getCode()== KeyCode.UP)
                {   pizzeriaButton.fire();
                }
            }
        });

        Scene scene = new Scene(layout, 880, 600);
        window.setScene(scene);
        window.show();
    }
}
