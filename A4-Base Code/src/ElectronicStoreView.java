import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ElectronicStoreView extends Pane {
    private ElectronicStore model;
    private ListView<String> popularList;
    private ListView<String> stockList;
    private ListView<String> currList;
    private Button reset;
    private Button addCart;
    private Button remove;
    private Button completeSale;
    private TextField salesField;
    private TextField revenueField;
    private TextField salesField2;
    private double cartTotal;
    private Label currCart;

    public ListView<String> getPopularList(){return popularList;}
    public ListView<String> getStockList(){return stockList;}
    public ListView<String> getCurrList(){return currList;}
    public Button getReset(){return reset;}
    public Button getAddCart(){return addCart;}
    public Button getRemove(){return remove;}
    public Button getCompleteSale(){return completeSale;}
    public Label getCurrCart(){return currCart;}
    public ElectronicStoreView(){

        Label summaryLabel = new Label ("Store Summary:");
        summaryLabel.relocate(30,15);
        summaryLabel.setPrefSize(100,20);
        getChildren().addAll(summaryLabel);

        Label salesLabel = new Label("# Sales:");
        salesLabel.relocate(23,38);
        salesLabel.setPrefSize(60,20);
        getChildren().addAll(salesLabel);

        Label revenueLabel = new Label("Revenue:");
        revenueLabel.relocate(18, 68);
        revenueLabel.setPrefSize(60,20);
        getChildren().addAll(revenueLabel);

        Label salesLabel2 = new Label("$ / Sale:");
        salesLabel2.relocate(23,98);
        salesLabel2.setPrefSize(60,20);
        getChildren().addAll(salesLabel2);

        Label popularLabel = new Label("Most Popular Items:");
        popularLabel.relocate(23, 128);
        popularLabel.setPrefSize(120,20);
        getChildren().addAll(popularLabel);

        Label stockLabel = new Label ("Store Stock:");
        stockLabel.relocate(320,15);
        stockLabel.setPrefSize(100,20);
        getChildren().add(stockLabel);

        currCart = new Label("Current Cart:");
        currCart.relocate(590,15);
        currCart.setPrefSize(135,20);
        getChildren().add(currCart);

        popularList = new ListView<>();
        popularList.relocate(15, 158);
        popularList.setPrefSize(213,180);
        getChildren().add(popularList);

        stockList = new ListView<>();
        stockList.relocate(240,38);
        stockList.setPrefSize(260,300);
        getChildren().add(stockList);

        currList = new ListView<>();
        currList.relocate(515,38);
        currList.setPrefSize(260,300);
        getChildren().add(currList);

        reset = new Button("Reset Store");
        reset.relocate(52,346);
        reset.setPrefSize(120,40);
        getChildren().add(reset);

        addCart = new Button("Add to Cart");
        addCart.relocate(310,346);
        addCart.setPrefSize(120,40);
        addCart.setDisable(true);
        getChildren().add(addCart);

        remove = new Button("Remove from Cart");
        remove.relocate(525,346);
        remove.setPrefSize(120,40);
        remove.setDisable(true);
        getChildren().add(remove);

        completeSale = new Button("Complete Sale");
        completeSale.relocate(648,346);
        completeSale.setPrefSize(120,40);
        completeSale.setDisable(true);
        getChildren().add(completeSale);

        salesField = new TextField();
        salesField.relocate(70,38);
        getChildren().addAll(salesField);

        revenueField = new TextField();
        revenueField.relocate(70, 68);
        getChildren().addAll(revenueField);

        salesField2 = new TextField();
        salesField2.relocate(70, 98);
        getChildren().addAll(salesField2);
    }

    public void update(ElectronicStore model){
        this.model = model;

        stockList.setItems(FXCollections.observableArrayList(model.getExactStockList()));
        popularList.setItems(FXCollections.observableArrayList(model.getPopStockList()));

        if(model.getRealNumOfSales() == 0) {
            salesField.setText("0");
            revenueField.setText("0.00");
            salesField2.setText("N/A");
        }
        else{
            salesField.setText(model.getNumOfSales());
            revenueField.setText(model.getTotalRevenue());
            salesField2.setText(model.getAverageRevenue());
            getCurrCart().setText("Current Cart: $" + model.getRevenue());
        }

        ArrayList<String> cartProductList = model.getCartProducts();
        if(cartProductList.size() == 0){
            System.out.println("Length of cart is " + cartProductList.size());
            setCompleteSale(true);
        }
        getCurrList().setItems(FXCollections.observableArrayList(cartProductList));

        if(model.getRefreshStock() == true){
            stockList.setItems(FXCollections.observableArrayList(model.getExactStockList()));
        }

        ArrayList<Product> top3SoldProducts = model.getTop3SoldProducts();
        ArrayList<String> top3SoldProductNames = new ArrayList<>();
        for (Product product : top3SoldProducts) {
            top3SoldProductNames.add(product.toString());
        }
        getPopularList().setItems(FXCollections.observableArrayList(top3SoldProductNames));
    }
    public void setCompleteSale(boolean disableButton){completeSale.setDisable(disableButton);}
}
