//Base class for all products the store will sell
public abstract class Product {
    private double price;
    private int stockQuantity;
    private int soldQuantity;

    public Product(double initPrice, int initQuantity) {
        price = initPrice;
        stockQuantity = initQuantity;
    }

    public void changeCartQuantity(int amount){
        if(stockQuantity < 11 && stockQuantity > 0){
            stockQuantity += amount;
        }
    }

    public void changeSoldQuantity(int amount){
        if(soldQuantity < 11){
            soldQuantity += amount;
        }
    }
    public int getStockQuantity() {return stockQuantity;}

    public int getSoldQuantity() {return soldQuantity;}

    public double getPrice() {return price;}

    //Returns the total revenue (price * amount) if there are at least amount items in stock
    //Return 0 otherwise (i.e., there is no sale completed)
    public int sellUnits(int amount) {
        if (amount > 0 && stockQuantity >= amount){
            soldQuantity += amount;
        }
        return soldQuantity;
    }
}