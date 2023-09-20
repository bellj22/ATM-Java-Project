/*
 * CSC 260 001
 * By: Jade Bell(bellj22)
 * HW3: ATM Machine
 * Professor Sunday 
 * 30 April 2023
*/

// About ATM Program 
/* I have created a new program because I could not understand the sample program given.
    The main program has a ATM & Customer class with multiple public & private methods.
 * This program has methods for:
    1. Opening an account
    2. Withdrawing Money
    3. Depositing money
    4. Checks balance
    5. Ability to change pin number
    6. Ability to change card number 
    7. Ability for customer to pay bills
    8. request a new card
    Each method requires validation of cusotmer's pin & cardNumber
 * The new abilities added were numbers 5-8
 * I had issues adding checking and savings account, so I did not incorporate it
 * Since there were only two main classes, the uml diagram I created was fairly small
*/

// Imports 
import java.util.Scanner;

public class UpdateAtm {
    private double balance;
    private Customer customer;
    private Scanner scanner;

    public UpdateAtm() {
        // sets balance to 0
        this.balance = 0;
        this.customer = null;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            // Gives customer options
            System.out.println("\n***************************************************");
            System.out.println("Welcome Valued Customer! What would you like to do?");
            System.out.println("***************************************************");
            System.out.println("1. Open a new account");
            System.out.println("2. Withdraw money");
            System.out.println("3. Deposit money");
            System.out.println("4. Check account balance");
            System.out.println("5. Change PIN");
            System.out.println("6. Change card number");
            System.out.println("7. Pay Bill");
            System.out.println("8. Request a New Card");
            System.out.println("9. Exit");

            int choice = readIntInput("Enter your choice (1-9): ");

            switch (choice) {
                case 1:
                // Opens a account
                    openAccount();
                    break;
                case 2:
                // withdraws money from account
                    withdraw();
                    break;
                case 3:
                // deposits money from account
                    deposit();
                    break;
                case 4:
                // checks current balance
                    checkBalance();
                    break;
                case 5:
                // changes pin number
                    changePin();
                    break;
                case 6:
                // changes card number
                    changeCardNumber();
                    break;
                case 7:
                // pay bills
                    payBill();
                    break;
                case 8:
                // requests new card 
                    newCardRequest();
                    break;
                case 9:
                    System.out.println("\nThank you for using the ATM! Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method: Create an account 
    private void openAccount() {
        // if customer has an account prior
        if (customer != null) {
            System.out.println("\nYou already have an account.");
            return;
        }
        // Get customer's name & pin 
        String name = readStringInput("Enter your name: ");
        String pin = readStringInput("Enter your PIN: ");
        // if pin < 4, print an error message 
        if (pin.length() != 4) {
            System.out.println("\nPIN should be 4 digits long. Try again.");
            return;
        }
        // If card number < 16, report an error
        String cardNumber = readStringInput("Enter your card number: ");
        if (cardNumber.length() != 16) {
            System.out.println("\nCard number should be 16 digits long. Try again.");
            return;
        }

        customer = new Customer(name, pin, cardNumber);
        // Display customer's account 
        System.out.println("\nYour account has been created successfully!");
        System.out.println("Your pin number:" + pin);
        System.out.println("Your card number:" + cardNumber);
    }

    // Method: Withdraw
    private void withdraw() {
        if (customer == null) {
            System.out.println("\nTo withdraw, You need to open an account first.");
            return;
        }
        // validate customer
        if (!validateCustomer()) {
            System.out.println("\nInvalid PIN or card number.");
            return;
        }

        System.out.print("\n===================\nWithdraw Operation \n===================\n");
        double amount = readDoubleInput("Enter the amount to withdraw: ");

        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }
        balance -= amount;
        System.out.println("\nWithdrawal successful. Your new balance is: " + balance);
    }

    // Method: Deposit 
    private void deposit() {
        if (customer == null) {
            System.out.println("\nTo deposit, you need to open an account first.");
            return;
        }
        // validate customer 
        if (!validateCustomer()) {
            System.out.println("Invalid PIN or card number.");
            return;
        }

        System.out.print("\n===================\nDeposit Operation \n===================\n");
        double amount = readDoubleInput("Enter the amount to deposit: ");
        if (amount <= 0) {
            System.out.println("\nInvalid deposit amount.");
            return;
        }
        // Deposits money and displays current balance
        balance += amount;
        System.out.println("\nDeposit successful. Your new balance is: " + balance);
    }
    
    // Method: Check Balance
    // This allows a customer to view their current balance
    private void checkBalance() {
        if (customer == null) {
            System.out.println("You need to open an account first.");
            return;
        }
        if (!validateCustomer()) {
            System.out.println("\nInvalid PIN or card number.");
            return;
        }
        System.out.print("\n===================\nCheck Current Balance \n===================\n");
        System.out.println("\nYour current balance is: " + balance);
    }

    // Method: Validate customer
    private boolean validateCustomer() {
        String pin = readStringInput("Enter your PIN: ");
        String cardNumber = readStringInput("Enter your card number: ");

        if (customer.getPin().equals(pin) && customer.getCardNumber().equals(cardNumber)) {
            return true;
        }
        return false;
    }

    // Method: Change Pin
    // This allows user to change their pin number
    private void changePin(){
        if (customer == null) {
            System.out.println("\nPlease open an account first.");
            return;
        }
        // validates user
        if (!validateCustomer()){
            System.out.println("\nInvalid PIN or card number.");
            return;
        }
        System.out.print("\n===================\nNew Pin\n===================\n");
        String newPin = readStringInput("Enter new PIN: ");
        // Checks if pin is 4 digits
        if (newPin.length() != 4) {
            System.out.println("\nPIN should be 4 digits long. Try again.");
            return;
        }
        // change pin number and display the resuts 
        customer.setPin(newPin);
        System.out.println("\nYour pin was updated successfully.");
    }

    // Method: Change Card 
    // This method allows a user to change their card number
    private void changeCardNumber() {
        if (customer == null) {
            System.out.println("\nYou need to open an account first.");
            return;
        }
        // validate user 
        if (!validateCustomer()) {
            System.out.println("\nInvalid PIN/card number.");
            return;
        }
        System.out.print("\n===================\nNew Card Operation\n===================\n");
        String newCardNumber = readStringInput("Enter new card number: ");
        // Checks if card is longer/shorter than 16 digits
        if (newCardNumber.length() != 16) {
            System.out.println("Card number should be 16 digits long. Please try again.");
            return;
        }
        // change card numebr and display result 
        customer.setCardNumber(newCardNumber);
        System.out.println("\nYour card number was updated successfully.");
    }

    // Method: Pay Bill
    // Allows the customer to pay a bill
    private void payBill(){
        if (customer == null){
            System.out.println("\nYou need to open an account first.");
            return;
        }
        // validate user 
        if (!validateCustomer()) {
            System.out.println("\nInvalid PIN or card number.");
            return;
        }
        System.out.print("\n===================\nPay Bill Operation \n===================\n");
        double amount = readDoubleInput("Enter the amount to pay: ");
        if (amount <= 0) {
            System.out.println("\nInvalid payment amount.");
            return;
        }
        // Checks sufficiency
        if (amount > balance) {
            System.out.println("Insufficient funds.");
            return;
        }
        // Deducts amount from the balance
        balance -= amount;
        System.out.println("\nPayment successful. Your new balance is: " + balance);
    }
    
    // Method: Request a new Card
    private void newCardRequest(){
        if (customer == null){
            System.out.println("\nTo request a new card, You need to open an account first.");
            return;
        }
        if (!validateCustomer()) {
            System.out.println("\nInvalid PIN or card number.");
            return;
        }
        System.out.print("\n===================\nRequest Card Operation \n===================\n");

        // Ask customer to select a reason for a card request
        System.out.println("Pleas explain to us why do you need to replace your card?");
        System.out.println("1. Lost");
        System.out.println("2. Stolen");
        System.out.println("3. Damaged");

        int reason = readIntInput("Enter your choice (1-3)");

        // Process the card request based on the reason
        switch(reason){
            case 1:
            // lost card
            System.out.println("Your card has been reported as lost.");
            break;
            case 2:
            // stolen card
            System.out.println("Your card has been reported as stolen.");
            break;
            case 3:
            // damaged card
            System.out.println("Your card has been reported as damaged.");
            case 4:
            // invalid
            System.out.println("Invaid choice. Please try again.");
            break;
        }
        // Ask customer if they are sure to request a new card
        String request = readStringInput("\nAre you sure you want to request a new card? Type (Y/N) ");
        if(request.equalsIgnoreCase("y")){
            // Confirm card replacement and provide instructions for next steps 
            System.out.println("\nYour request for a new card has been successfully submitted.");
            System.out.println("Your new card will be sent to your mailing address within 7-10 business days.");
            System.out.println("Thank you for using our replacement services! Goodbye.");
        } else {
            System.out.println("\nRequest cancelled.");
        }
        
    }
    // reads string input 
    private String readStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    // reads int input 
    private int readIntInput(String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine());
    }
    // reads double input 
    private double readDoubleInput(String prompt)
    {
        System.out.print(prompt);
        return Double.parseDouble(scanner.nextLine());
    }

    // Main Program
    public static void main(String[] args) {
        UpdateAtm atm = new UpdateAtm();
        atm.start();
    }
}

// Customer class 
class Customer {
    private String name;
    private String pin;
    private String cardNumber;
  

    public Customer(String name, String pin, String cardNumber) {
        this.name = name;
        this.pin = pin;
        this.cardNumber = cardNumber;
    }
    // returns customer(s) name
    public String getName() {
        return name;
    }
    // return pin number
    public String getPin() {
        return pin;
    }
    // return card number 
    public String getCardNumber() {
        return cardNumber;
    }
    // set the pin numebr 
    public void setPin(String pin) {
        this.pin = pin;
    }
    // set card number 
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
