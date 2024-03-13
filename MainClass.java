package thirdproject;

import java.util.Scanner;

import java.util.List;

import java.util.regex.Pattern; 




public class MainClass {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        User nowUser = null;

        UserAuthentication userAuthentication = new UserAuthentication();

        ShoppingCart shoppingCart = new ShoppingCart();

        Catalog catalog = new Catalog();



        // Load products into the catalog

        List<Product> products = ProductLoader.loadProducts();

        catalog.addProducts(products);

        System.out.println("Welcome to DePaul Shopping Centre");
        

        while (true) {


            System.out.println("1. Register as a new User");

            System.out.println("2. Login as an existing User");

            System.out.println("3. View all Products");

            System.out.println("4. Add a Product to Cart");

            System.out.println("5. View Your Cart");

            System.out.println("6. Place an Order");

            System.out.println("7. Exit the Shopping Centre");



            if (nowUser != null) {

                System.out.println("You are Logged in as: " + nowUser.getUsername());

            }



            System.out.print("Select an option from the list: ");

            int choice = scanner.nextInt();

            scanner.nextLine(); // Consume the newline character.



            switch (choice) {

                case 1: // Register

                    System.out.print("Enter your username: ");

                    String username = scanner.nextLine();

                    System.out.print("Enter your password: ");

                    String password = scanner.nextLine();

                    System.out.print("Enter your name: ");

                    String name = scanner.nextLine();

                    System.out.print("Enter your email: ");

                    String email = scanner.nextLine();

                 // Validate input

                    if (!isValidUsername(username)) {

                        System.out.println("Username can contain only lowercase letters.");

                    } else if (!isValidPassword(password)) {

                        System.out.println("Password must contain at least one uppercase letter, one special character, one number, and at least 8 characters.");

                    } else if (!isValidName(name)) {

                        System.out.println("Name must be in the format 'First Last' with the first letter of each word capitalized.");

                    } else if (!isValidEmail(email)) {

                        System.out.println("Enter a valid email.");

                    } else {

                        userAuthentication.registerUser(username, password, name, email);

                        Logger.log("User registered: " + username);

                        System.out.println("User registered successfully!");

                    }

                    break;

                    

                    


                case 2: // Login

                    if (nowUser != null) {

                        System.out.println("You are already logged in.");

                    } else {

                        System.out.print("Enter your username: ");

                        String loginUsername = scanner.nextLine();

                        System.out.print("Enter your password: ");

                        String loginPassword = scanner.nextLine();

                        nowUser = userAuthentication.login(loginUsername, loginPassword);



                        if (nowUser != null) {


                            System.out.println("Logged in . Welcome, " + nowUser.getUsername() + "!");

                        } else {

                            System.out.println("Username/Password does not match");

                        }

                    }

                    break;

                case 3: // Browse Products (Not implemented in this example)

                    



                    displayProductList(catalog.getAllProducts());

                    break;



                    

                case 4: // Add Product to Cart (Not implemented in this example)

                	if (nowUser != null) {

                        // Placeholder for adding a product to the cart

                        System.out.print("Enter the Product's name: ");

                        String productName = scanner.nextLine();

                        Product selectedProduct = findProductByName(catalog.getAllProducts(), productName);





                        if (selectedProduct != null) {

                            shoppingCart.addItem(selectedProduct);

                            System.out.println("Product added to your cart.");

                        } else {

                            System.out.println("No such product exists in the catalog.");

                        }

                    } else {

                        System.out.println("You must be logged in to add products to the cart.");

                    }

                    break;



                 // Inside your main switch statement

                case 5: // View Cart

                    if (nowUser != null) {

                        List<Product> items = shoppingCart.getItems();

                        System.out.println( nowUser.getUsername() + "'s Cart" + ":");

                        for (Product item : items) {

                            System.out.println(item.getName());

                        }



                        double totalPrice = calculateTotalPrice(shoppingCart);

                        System.out.println("Total Price: $" + totalPrice);

                    } else {

                        System.out.println("You must be logged in to view your cart.");

                    }

                    break;



                case 6: // Place Order (Not implemented in this example)

                    if (nowUser != null) {

                        System.out.println("Placing your order...");

                        double totalPrice = calculateTotalPrice(shoppingCart);

                        boolean paymentSuccess = new PaymentProcessor().processPayment(totalPrice, "Credit Card");



                        if (paymentSuccess) {
                            Logger.log("Order successfully placed for user: " + nowUser.getUsername());

                        } else {
                            Logger.log("Payment failed for user: " + nowUser.getUsername());


                        }

                    } else {

                        System.out.println("You must be logged in to place an order.");

                    }

                    break;

                case 7: // Exit

                    System.out.println("Thank you for visiting us, Come again...");

                    scanner.close();

                    System.exit(0);

                default:

                    System.out.println("Please select a valid option");

            }

        }

    }

    

    

    





	// Validation methods

    private static boolean isValidUsername(String username) {

        // Username contains all lowercase letters

        return Pattern.matches("^[a-z]+$", username);

    }



    private static boolean isValidPassword(String password) {

        // Password contains one uppercase, one special character, one number, and all lowercase

        return Pattern.matches("^(?=.*[A-Z])(?=.*[!@#$%^&*()])(?=.*[0-9])(?=.*[a-z]).{8,}$", password);

    }



    private static boolean isValidName(String name) {

        // Name contains the first name and last name

        return Pattern.matches("^[A-Z][a-z]* [A-Z][a-z]*$", name);

    }



    private static boolean isValidEmail(String email) {

        // Email contains the @ symbol

        return email.contains("@");

    }











    private static void displayProductList(List<Product> productList) {

        System.out.println("Product List:");

        for (int i = 0; i < productList.size(); i++) {

            Product product = productList.get(i);

            System.out.println((i + 1) + ". " + product.getName() + " - $" + product.getPrice());

            System.out.println("   Description: " + product.getDescription());

            System.out.println("   Quantity in Stock: " + product.getQuantityInStock());

        }

    }





// Placeholder method to find a product by name

private static Product findProductByName(List<Product> catalogProducts, String productName) {

    for (Product product : catalogProducts) {

        if (product.getName().equalsIgnoreCase(productName)) {

            return product;

        }

    }

    return null;

}



// Placeholder method to calculate the total price of items in the cart

private static double calculateTotalPrice(ShoppingCart shoppingCart) {

    double total = 0.0;

    for (Product product : shoppingCart.getItems()) {

        total += product.getPrice();

    }

    return total;

}

}
