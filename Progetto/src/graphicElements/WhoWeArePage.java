package graphicElements;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pizzeria.Pizzeria;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WhoWeArePage {

    public void display(Stage window, Pizzeria pizzeria) {

        StringBuilder history = new StringBuilder("\n");
        try {
            Scanner fileIn = new Scanner(new File("Progetto/src/pizzeria/history.txt"));
            while (fileIn.hasNextLine()) {
                String line = fileIn.nextLine();
                if (!line.startsWith("*"))
                    history.append(line).append("\n");
                else {
                    line = line.substring(1);
                    history.append(line.toUpperCase()).append("\n");
                }
            }

        } catch (FileNotFoundException fnfe){
            System.out.println("ERROR");
        }


        TextArea textField=new TextArea();
        textField.setText(String.valueOf(history));

        textField.setEditable(false);
        textField.prefWidthProperty().bind(window.widthProperty());
        textField.prefHeightProperty().bind(window.heightProperty());

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(textField);

        Button backButton = new Button("← Torna indietro");
        backButton.setId("backButton");
        backButton.setOnAction(e -> {
            MenuPage menuPage = new MenuPage();
            menuPage.display(window, pizzeria);
        });

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(backButton);
        hBox.setAlignment(Pos.CENTER);

        VBox layout=new VBox();
        layout.getChildren().addAll(stackPane,hBox);
        layout.prefWidthProperty().bind(window.widthProperty());
        layout.prefHeightProperty().bind(window.heightProperty());

        Scene scene5 = new Scene(layout);

        scene5.getStylesheets().addAll(this.getClass().getResource("cssStyle/menuStyle.css").toExternalForm());
        //window.setResizable(false);
        window.setScene(scene5);
        window.setTitle("Wolf of Pizza");

        window.show();
    }
}
