package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private int CarId;
    private  String Model;
    private  String Brand;
    private int Price;
    private  boolean isAvailable;

    public  Car(int CarId,String Model, String Brand, int Price, boolean isAvailable){
        this.CarId = CarId;
        this.Model = Model;
        this.Brand = Brand;
        this.Price = Price;
        this.isAvailable = true;
    }

    public int  getCarId(){
        return CarId;
    }
    public  String getModel(){
        return  Model;
    }
    public  String getBrand(){
        return  Brand;
    }
    public int getPrice(){
        return  Price;
    }
    public  boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public  void  returnCar(){
        isAvailable= true;
    }
    public int CalculateRent(int rentalDays){
        return Price * rentalDays;
    }

}
class Customer{
     private int CustomerId;
     private int Aadhar;
     private  String Name;

     public Customer(int CustomerId, int Aadhar, String Name){
         this.CustomerId = CustomerId;
         this.Aadhar = Aadhar;
         this.Name = Name;
     }
     public  int getCustomerId(){
         return CustomerId;
     }

    public int getAadhar() {
        return Aadhar;
    }
    public String getName(){
         return Name;
    }
}
class Rental{
    private Car car;
    private Customer customer;
    private int Days;

    public Rental(Car car, Customer customer, int Days){
        this.car = car;
        this.customer = customer;
        this.Days = Days;
    }
     public Car getCar(){
        return  car;
     }
     public Customer getCustomer(){
        return customer;
     }

    public int getDays() {
        return Days;
    }
}

class RentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public RentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public  void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }
        else{
            System.out.println("Sorry! this car is not available");
        }
    }
    public  void returnCar(Car car){
        Rental rentalToRemove = null;
        for(Rental rent:rentals){
            if(rent.getCar() == car){
                rentalToRemove = rent;
                break;
            }
        }
        if(rentalToRemove != null){
            car.returnCar();
            System.out.println("Car returned successfully");
        }
        else{
            System.out.println("car not rented");
        }
    }
    public  void menu(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("***Roody's Car Rental System***");
            System.out.println("1. Car Rent");
            System.out.println("2. Return a Car");
            System.out.println("3.Exit");
            System.out.println("Enter your choixe");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice ==1){
                System.out.println("Renting a car");
                System.out.println("Enter your name");
                String customerName = sc.nextLine();

                System.out.println("Available cars");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }
                System.out.println("ENter the car id you want to rent");
                int carId = sc.nextInt();

                System.out.println("Enter number of days you want to rent");
                int rentalDays = sc.nextInt();

                Customer newCustomer = new Customer(1, (customers.size()+1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car : cars){
                    if(car.getCarId()==(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
               }
                if(selectedCar != null){

                    System.out.println("Rental Information");
                    System.out.println("CustomerId:" + newCustomer.getCustomerId());
                    System.out.println("CustomerName:" + newCustomer.getName());
                    System.out.println("Customer Aadhar:" + newCustomer.getAadhar());
                    System.out.println("Selected Car:" + selectedCar.getBrand() + " "+ selectedCar.getModel());
                    System.out.println("Total rent days"+ rentalDays);
                    System.out.println("Total Price:" + selectedCar.CalculateRent(rentalDays));

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                }
                else {
                    System.out.println("\nInvalid car selection or car not available for rent.");

                }
            }
            else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
              int carId = sc.nextInt();
                Car carToReturn = null;
                for(Car car:cars){
                    if(car.getCarId() == carId && !car.isAvailable()){
                        carToReturn = car;
                        break;
                    }
                }
                if(carToReturn != null){
                    Customer cust  = null;
                    for(Rental rent:rentals){
                        if (rent.getCar() == carToReturn){
                            cust = rent.getCustomer();
                            break;
                        }
                    }
                    if(cust !=null){
                        returnCar(carToReturn);
                        System.out.println("Car retuend successfuly" + cust.getName());

                    }
                    else{
                        System.out.println("Car was not rented or somthing is missing");
                    }
                }
                else {
                    System.out.println("Car was not rented or somthing is missing");
                }

            }
            else if(choice == 3){
                break;
            }
            else{
                System.out.println("Enter valid option ");
            }
        }
        System.out.println("\nThank you for using the Car Rental System");
    }

}

public class Main {
    public static void main(String[] args) {
        RentalSystem carRent = new RentalSystem();
        Car car1 = new Car(001,"Thar","Mahindra",2000,true);
        carRent.addCar(car1);
        carRent.menu();

    }
}