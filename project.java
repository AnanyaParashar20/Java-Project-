import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

abstract class Shopping {
    String modeOfShopping; // online or offline
    float discount;
    String category;
    float price;

    Shopping() {
        modeOfShopping = "offline";
        discount = 0;
    }

    Shopping(String s, float d, String c, float a) {
        modeOfShopping = s;
        discount = d;
        category = c;
        price = a;
    }

    abstract void display();
}

class Electronics extends Shopping {
    static Map<String, Float> electronicsMap = new HashMap<>();

    static {
        electronicsMap.put("Laptop", 15000f);
        electronicsMap.put("Mobile", 10000f);
        electronicsMap.put("Tablet", 12000f);
        electronicsMap.put("Speaker", 2500f);
        electronicsMap.put("Headphones", 3000f);
    }

    String selectedElectronics;

    Electronics() {
        super();
    }

    Electronics(String s, float d, String s2, float a, String c) {
        super(s, d, s2, a);
        selectedElectronics = c;
    }

    void display() {
        System.out.printf("Mode of shopping: %s\nDiscount: %.2f\nCategory: %s\nItem: %s Price after Discount: %f\n",
                modeOfShopping, discount, category, selectedElectronics, price);
    }
}

class Clothes extends Shopping {
    static Map<String, Float> clothesMap = new HashMap<>();

    static {
        clothesMap.put("Dresses", 2000f);
        clothesMap.put("Jeans", 1000f);
        clothesMap.put("Tops", 500f);
        clothesMap.put("Hoodies", 1500f);
        clothesMap.put("Sweatshirts", 1600f);
    }

    String selectedClothes;

    Clothes() {
        super();
    }

    Clothes(String s, float d, String s2, float a, String c) {
        super(s, d, s2, a);
        selectedClothes = c;
    }

    void display() {
        System.out.printf("Mode of shopping: %s\nDiscount: %.2f\nCategory: %s\nItem: %s Price after Discount: %f\n",
                modeOfShopping, discount, category, selectedClothes, price);
    }
}

class Grocery extends Shopping {
    static Map<String, Float> groceryMap = new HashMap<>();

    static {
        groceryMap.put("Fruits", 5f);
        groceryMap.put("Vegetables", 4f);
        groceryMap.put("Dairy", 3f);
        groceryMap.put("Cereals", 2.5f);
        groceryMap.put("Snacks", 1.5f);
    }

    String selectedGrocery;

    Grocery() {
        super();
    }

    Grocery(String s, float d, String s2, float a, String c) {
        super(s, d, s2, a);
        selectedGrocery = c;
    }

    void display() {
        System.out.printf("Mode of shopping: %s\nDiscount: %.2f\nCategory: %s\nItem: %s Price after Discount: %f\n",
                modeOfShopping, discount, category, selectedGrocery, price);
    }
}

class BooksMoviesDVDs extends Shopping {
    static Map<String, Float> booksMoviesDvDsMap = new HashMap<>();

    static {
        booksMoviesDvDsMap.put("Books", 15f);
        booksMoviesDvDsMap.put("Movies", 10f);
        booksMoviesDvDsMap.put("DVDs", 5f);
    }

    String selectedBooksMoviesDvDs;

    BooksMoviesDVDs() {
        super();
    }

    BooksMoviesDVDs(String s, float d, String s2, float a, String c) {
        super(s, d, s2, a);
        selectedBooksMoviesDvDs = c;
    }

    void display() {
        System.out.printf("Mode of shopping: %s\nDiscount: %.2f\nCategory: %s\nItem: %s Price after Discount: %f\n",
                modeOfShopping, discount, category, selectedBooksMoviesDvDs, price);
    }
}

interface PersonalDetails {
    void display();
}

class Address {
    String state;
    String city;
    int houseNo;

    Address(String state, String city, int houseNo) {
        this.state = state;
        this.city = city;
        this.houseNo = houseNo;
    }
}

class Details implements PersonalDetails {
    private String name;
    private Address address;
    private int age;

    Details() {
        name = "";
        address = new Address("Unknown", "Unknown", 0000);
        age = 10;
    }

    Details(String name, int age, String state, String city, int houseNo) {
        address = new Address(state, city, houseNo);
        this.name = name;
        this.age = age;
    }

    public void display() {
        System.out.println("\nUser Details:");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("State: " + address.state);
        System.out.println("City: " + address.city);
        System.out.println("House No: " + address.houseNo);
    }
}

public class project {

    static void updateDatabase(List<Shopping> cart) {
        String url = "jdbc:mysql://localhost:3306/shopping_cart";
        String username = "root";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            for (Shopping item : cart) {
                String insertQuery = "INSERT INTO cart (category, item, price) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, item.category);
                    preparedStatement.setString(2, getItemName(item));
                    preparedStatement.setFloat(3, item.price);
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static String getItemName(Shopping item) {
        if (item instanceof Electronics) {
            return ((Electronics) item).selectedElectronics;
        } else if (item instanceof Clothes) {
            return ((Clothes) item).selectedClothes;
        } else if (item instanceof Grocery) {
            return ((Grocery) item).selectedGrocery;
        } else if (item instanceof BooksMoviesDVDs) {
            return ((BooksMoviesDVDs) item).selectedBooksMoviesDvDs;
        } else {
            return "";
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Taking personal details of the user
        System.out.println("--ENTER YOUR DETAILS--");
        System.out.print("Age: ");
        int age = 0;
        try {
            age = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for age. Please enter a valid number.");
        }

        System.out.print("Name: ");
        String name = sc.next();
        System.out.print("State of residence: ");
        String state = sc.next();
        System.out.print("City of residence: ");
        String city = sc.next();
        System.out.print("House number: ");
        int houseNo = 0;
        try {
            houseNo = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for house number. Please enter a valid number.");
        }
        Details user = new Details(name, age, state, city, houseNo);

        // Displaying user details
        user.display();
        System.out.println("\nThanks for the details! Have a nice day shopping!!");

        // Shopping Cart
        List<Shopping> cart = new ArrayList<>();
        float totalSpent = 0;

        // Electronics
        System.out.println("\n-----------------------------------------------------------------");
        System.out.print("\n\nAre you interested in buying electronics -> 1:Yes\t0:No : ");
        int choice1 = 0;
        try {
            choice1 = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for choice. Please enter 0 or 1.");
        }

        if (choice1 == 1) {
            System.out.println("Enter the electronic product you wish to buy: ");
            for (int i = 0; i < Electronics.electronicsMap.size(); i++) {
                System.out.printf("%d. %s\n", i, Electronics.electronicsMap.keySet().toArray()[i]);
            }
            int choice2 = 0;
            try {
                choice2 = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for choice. Please enter a valid number.");

            }

            if (choice2 >= 0 && choice2 < Electronics.electronicsMap.size()) {
                String selectedElectronic = Electronics.electronicsMap.keySet().toArray()[choice2].toString();
                float price = Electronics.electronicsMap.get(selectedElectronic);
                float discountedPrice = price - (0.1f * price);
                totalSpent += discountedPrice;

                Electronics electronicItem = new Electronics("Online", 10f, "Electronics", discountedPrice,
                        selectedElectronic);
                electronicItem.display();
                cart.add(electronicItem);
            } else {
                System.out.println("Enter a valid option");
            }
        } else {
            System.out.println("\nNot interested in buying electronics ");
        }

        // Clothes
        System.out.println("\n-----------------------------------------------------------------");
        System.out.print("\nAre you interested in buying clothes -> 1:Yes\t0:No : ");
        choice1 = 0;
        try {
            choice1 = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for choice. Please enter 0 or 1.");

        }

        if (choice1 == 1) {
            System.out.println("Enter the clothing product you wish to buy: ");
            for (int i = 0; i < Clothes.clothesMap.size(); i++) {
                System.out.printf("%d. %s\n", i, Clothes.clothesMap.keySet().toArray()[i]);
            }
            int choice2 = 0;
            try {
                choice2 = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for choice. Please enter a valid number.");

            }

            if (choice2 >= 0 && choice2 < Clothes.clothesMap.size()) {
                String selectedClothing = Clothes.clothesMap.keySet().toArray()[choice2].toString();
                float price = Clothes.clothesMap.get(selectedClothing);
                float discountedPrice = price - (0.15f * price);
                totalSpent += discountedPrice;

                Clothes clothingItem = new Clothes("In-store", 15f, "Clothes", discountedPrice, selectedClothing);
                clothingItem.display();
                cart.add(clothingItem);
            } else {
                System.out.println("Enter a valid option");
            }
        } else {
            System.out.println("\nNot interested in buying clothes ");
        }

        // Grocery
        System.out.println("\n-----------------------------------------------------------------");
        System.out.print("\nAre you interested in buying groceries -> 1:Yes\t0:No : ");
        choice1 = 0;
        try {
            choice1 = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for choice. Please enter 0 or 1.");

        }

        if (choice1 == 1) {
            System.out.println("Enter the grocery item you wish to buy: ");
            for (int i = 0; i < Grocery.groceryMap.size(); i++) {
                System.out.printf("%d. %s\n", i, Grocery.groceryMap.keySet().toArray()[i]);
            }
            int choice2 = 0;
            try {
                choice2 = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for choice. Please enter a valid number.");

            }

            if (choice2 >= 0 && choice2 < Grocery.groceryMap.size()) {
                String selectedGroceryItem = Grocery.groceryMap.keySet().toArray()[choice2].toString();
                float price = Grocery.groceryMap.get(selectedGroceryItem);
                totalSpent += price;

                Grocery groceryItem = new Grocery("In-store", 0, "Grocery", price, selectedGroceryItem);
                groceryItem.display();
                cart.add(groceryItem);
            } else {
                System.out.println("Enter a valid option");
            }
        } else {
            System.out.println("\nNot interested in buying groceries ");
        }

        // BooksMoviesDVDs
        System.out.println("\n-----------------------------------------------------------------");
        System.out.print("\nAre you interested in buying BooksMoviesDVDs -> 1:Yes\t0:No : ");
        choice1 = 0;
        try {
            choice1 = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for choice. Please enter 0 or 1.");

        }

        if (choice1 == 1) {
            System.out.println("Enter the book, movie, or DVD you wish to buy: ");
            for (int i = 0; i < BooksMoviesDVDs.booksMoviesDvDsMap.size(); i++) {
                System.out.printf("%d. %s\n", i, BooksMoviesDVDs.booksMoviesDvDsMap.keySet().toArray()[i]);
            }
            int choice2 = 0;
            try {
                choice2 = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for choice. Please enter a valid number.");

            }

            if (choice2 >= 0 && choice2 < BooksMoviesDVDs.booksMoviesDvDsMap.size()) {
                String selectedBookMovieDvd = BooksMoviesDVDs.booksMoviesDvDsMap.keySet().toArray()[choice2].toString();
                float price = BooksMoviesDVDs.booksMoviesDvDsMap.get(selectedBookMovieDvd);
                float discountedPrice = 0.1f * price + price;
                totalSpent += discountedPrice;

                BooksMoviesDVDs bookMovieDvdItem = new BooksMoviesDVDs("Online", 10f, "BooksMoviesDVDs",
                        discountedPrice, selectedBookMovieDvd);
                bookMovieDvdItem.display();
                cart.add(bookMovieDvdItem);
            } else {
                System.out.println("Enter a valid option");
            }
        } else {
            System.out.println("\nNot interested in buying Books, Movies, DVDs");
        }

        // Display how much the user spent
        System.out.println("\nTotal amount spent: " + totalSpent);

        // Display the user's cart
        System.out.println("\nUser's Cart:");
        for (int i = 0; i < cart.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, cart.get(i).getClass().getSimpleName());
        }

        updateDatabase(cart);
    }
}