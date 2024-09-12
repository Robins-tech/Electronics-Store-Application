//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import javafx.stage.Stage;
import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private int num;
    private ArrayList<String> currStockList;
    private int currCartNum;
    private int[] prodCount = {0,0,0,0,0,0,0,0};
    private int productIndex;
    private int numOfSales;
    private int addOrRemove;
    private double totalRevenue;
    private double averageRevenue;
    private int soldNum;
    private boolean refreshStock;
    private int cartQuantity;

    public ElectronicStore(String initName){
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        currCartNum = 0;
        num = 0;
        currStockList = new ArrayList<>();
        productIndex = 0;
        numOfSales = 0;
        addOrRemove = -1;
        totalRevenue = 0.0;
        averageRevenue = 0.0;
        soldNum = 0;
        refreshStock = false;
    }

    public int setIndex(int index){
        productIndex = index;
        return productIndex;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }
    public int getCurProducts(){return curProducts;}

    public ArrayList<String> getExactStockList(){
        int numProducts = getCurProducts();
        ArrayList<String> exactStockList = new ArrayList<>();

        for (int i = 0; i < numProducts; i++){
            if (stock[i] != null && stock[i].getStockQuantity() > 0){
                exactStockList.add(stock[i].toString());
            }
        }
        return exactStockList;
    }

    public String[] getPopStockList() {
        String[] popStockList = new String[3];
        for (int i = 0; i < 3; i++)
            popStockList[i] = getStock()[i].toString();
        return  popStockList;
    }

    public void prodPrint(){System.out.println("Index of selected item: " + productIndex);}
    public void stockPrint(){
        if (productIndex >= 0 && productIndex < getCurProducts() && stock[productIndex] != null){
            System.out.println("This item has this many products in stock:" + stock[productIndex].getStockQuantity());
        }
    }
    public Product[] getStock(){return stock;}
    public String getName() {return name;}
    public ArrayList<String> addToCart(int index){

        if (stock[index] != null && stock[index].getStockQuantity() > 0){
            revenue = revenue + stock[productIndex].getPrice();
            stock[productIndex].changeCartQuantity(-1);
            currStockList.add(stock[index].toString());
            cartQuantity = updateProdCount(0,index);
            System.out.println("Current product in cart: " + cartQuantity);
            currCartNum++;
        }
        removeOutOfStockItems();
        getExactStockList();

        return currStockList;
    }

    public ArrayList<String> removeFromCart(int indexToRemove){
        if (indexToRemove >= 0 && indexToRemove < currStockList.size() && cartQuantity != 10){
            String cartItem = currStockList.get(indexToRemove);
            for(int i = 0; i < getCurProducts(); i++){
                if(getExactStockList().get(i).equals(cartItem)){
                    productIndex = i;
                    break;
                }
            }
            removeChangeStock(cartItem);
            currStockList.remove(indexToRemove);
            currCartNum--;
        }
        else if(cartQuantity == 10){
            System.out.println("I forgor");
        }
        return currStockList;
    }

    private void removeOutOfStockItems() {
        for (int i = 0; i < stock.length; i++) {
            if (stock[i] != null && stock[i].getStockQuantity() == 0) {
                stock[i] = null;
                refreshStock = true;
            }
        }
    }

    public boolean getRefreshStock(){return refreshStock;}
    public double getRevenue(){return revenue;}

    public void removeChangeStock(String cart){
        if (productIndex >= 0 && productIndex < getCurProducts() && getExactStockList().get(productIndex).equals(cart)){
            revenue = revenue - stock[productIndex].getPrice();
            stock[productIndex].changeCartQuantity(1);
            cartQuantity = updateProdCount(1,productIndex);
            System.out.println("Current product in cart: " + cartQuantity);
            System.out.println("Stock amount after removing product from cart:" + stock[productIndex].getStockQuantity());
        }
    }

    public void setAddOrRemove(int selection){addOrRemove = selection;}
    public ArrayList<String> getCartProducts(){
        ArrayList<String> cartProductList = new ArrayList<>();
        if(addOrRemove == 0) {

            ArrayList<String> updatedCart = addToCart(productIndex);
            int numProductsInCart = updatedCart.size();

            for(int i = 0; i < numProductsInCart; i++){
                if(updatedCart.get(i) != null){
                    cartProductList.add(updatedCart.get(i));
                }
            }
            return cartProductList;
        }
        else if(addOrRemove == 1){
            ArrayList<String> updatedCart = removeFromCart(productIndex);
            int numProductsInCart = updatedCart.size();

            for(int i = 0; i < numProductsInCart; i++){
                cartProductList.add(updatedCart.get(i));
            }
            return cartProductList;
        }
        return cartProductList;
    }
    private int updateProdCount(int addOrRemove, int index){
        if(prodCount[index] < MAX_PRODUCTS && addOrRemove == 0){
            prodCount[index]++;
            num = prodCount[index];
        }
        else if(prodCount[index] > 0 && addOrRemove == 1){
            prodCount[index]--;
            num = prodCount[index];
        }
        return num;
    }

    public void processSale(){
        numOfSales++;
        totalRevenue += getRevenue();
        averageRevenue = totalRevenue/numOfSales;
        updateSold();
        resetCart();
    }

    public void resetCart() {
        currStockList.clear();
        revenue = 0.0;
        Arrays.fill(prodCount, 0);
    }
    public String getNumOfSales(){return String.valueOf(numOfSales);}
    public String getTotalRevenue(){return String.valueOf(totalRevenue);}
    public String getAverageRevenue(){return String.valueOf(averageRevenue);}
    public int getRealNumOfSales(){return numOfSales;}

    public int updateSold(){
        for(int i=0; i < MAX_PRODUCTS; i++){
            if (stock[i] != null) {
                soldNum = MAX_PRODUCTS - stock[i].getStockQuantity();
                stock[i].changeSoldQuantity(soldNum);
            }
        }
        return soldNum;
    }

    public ArrayList<Product> getTop3SoldProducts() {
        if (numOfSales == 0) {
            ArrayList<Product> initialProducts = new ArrayList<>();
            for (int i = 0; i < 3 && i < stock.length; i++) {
                if (stock[i] != null) {
                    initialProducts.add(stock[i]);
                }
            }
            return initialProducts;
        }
        else {
            PriorityQueue<Product> queue = new PriorityQueue<>(Comparator.comparingInt(Product::getSoldQuantity));
            for (Product product : stock) {
                if (product != null) {
                    queue.add(product);
                    if (queue.size() > 3) {
                        queue.poll();
                    }
                }
            }
            ArrayList<Product> top3Products = new ArrayList<>(queue);
            Collections.reverse(top3Products);
            return top3Products;
        }
    }
    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
    public void start(Stage primaryStage) {}
}