import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ElectronicStoreApp extends Application{
    private ElectronicStore model;
    private ElectronicStoreView view;

    @Override
    public void start(Stage primaryStage){
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView();

        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        Scene scene = new Scene(view, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent mouseEvent) {
                view.getAddCart().setDisable(view.getStockList().getSelectionModel().getSelectedIndex() < 0);
            }
        });

        view.getCurrList().setOnMousePressed(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent mouseEvent) {
                view.getRemove().setDisable(view.getCurrList().getSelectionModel().getSelectedIndex() < 0);
            }
        });

        view.getAddCart().setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) { handleAddButtonPress(); }
        });

        view.getRemove().setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) { handleRemoveButtonPress(); }
        });

        view.getCompleteSale().setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) { handleCompleteSaleButtonPress(); }
        });

        view.getReset().setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent actionEvent) { handleResetButtonPress(); }
        });

        view.update(model);
    }
    private void handleAddButtonPress() {
        int index = view.getStockList().getSelectionModel().getSelectedIndex();
        if (index >= 0) { // Check if an item is selected
            model.setIndex(index);
            int addOrRemove = 0; //0 for add
            model.setAddOrRemove(addOrRemove);

            view.update(model);

            double cartTotal = model.getRevenue();
            view.getCurrCart().setText("Current Cart: $" + String.format("%.2f", cartTotal));

            view.setCompleteSale(false);

            model.prodPrint();
            model.stockPrint();
            System.out.println("-------------------------");
        }
    }

    private void handleRemoveButtonPress(){
        int index = view.getCurrList().getSelectionModel().getSelectedIndex();

        model.setIndex(index);
        int addOrRemove = 1; //1 for remove
        model.setAddOrRemove(addOrRemove);

        view.update(model);

        double cartTotal = model.getRevenue();
        view.getCurrCart().setText("Current Cart: $" + String.format("%.2f", cartTotal));

        System.out.println("-------------------------");
    }

    public void handleCompleteSaleButtonPress(){
        model.processSale();
        int addOrRemove = 1;
        model.setAddOrRemove(addOrRemove);
        view.update(model);
    }

    public void handleResetButtonPress() {
        ElectronicStore reset = ElectronicStore.createStore();
        model = reset;  // Assign new model to the current reference
        view.update(model);  // Update the view with the new model state
    }

    public static void main(String[] args){launch(args);}
}
