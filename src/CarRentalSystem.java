import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (!car.isRented()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Error: Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        if (car.isRented()) {
            car.returnCar();
            Rental rentalToRemove = null;
            for (Rental rental : rentals) {
                if (rental.getCar() == car) {
                    rentalToRemove = rental;
                    break;
                }
            }
            if (rentalToRemove != null) {
                rentals.remove(rentalToRemove);
                System.out.println("Car returned successfully!");
            } else {
                System.out.println("Car was not returned as rental record was not found.");
            }
        } else {
            System.out.println("Error: Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("       🚗 CAR RENTAL SYSTEM 🚗           ");
            System.out.println("==========================================");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. View Available Cars");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                continue;
            }

            if (choice == 1) {
                System.out.println("\n--- Rent a Car ---");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine().trim();

                System.out.print("Enter your phone number: ");
                String phoneNumber = scanner.nextLine().trim();

                System.out.println("\nAvailable Cars:");
                boolean availableFound = false;
                for (Car car : cars) {
                    if (!car.isRented()) {
                        System.out.println(car);
                        availableFound = true;
                    }
                }

                if (!availableFound) {
                    System.out.println("No cars are currently available for rent.");
                    continue;
                }

                System.out.print("\nEnter the Car ID you want to rent: ");
                String carId = scanner.nextLine().trim();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays;
                try {
                    rentalDays = Integer.parseInt(scanner.nextLine().trim());
                    if (rentalDays <= 0) {
                        System.out.println("Rental days must be greater than 0.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number of days.");
                    continue;
                }

                Customer newCustomer = new Customer("CUST" + (customers.size() + 1), customerName, phoneNumber);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && !car.isRented()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n=== Rental Information ===");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f\n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine().trim();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\n🎉 Car rented successfully!");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car is unavailable.");
                }

            } else if (choice == 2) {
                System.out.println("\n--- Return a Car ---");
                System.out.print("Enter the Car ID you want to return: ");
                String carId = scanner.nextLine().trim();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && car.isRented()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    returnCar(carToReturn);
                } else {
                    System.out.println("Invalid Car ID or car is not currently rented.");
                }

            } else if (choice == 3) {
                System.out.println("\n--- Car Fleet Status ---");
                System.out.printf("%-8s | %-12s | %-12s | %-12s | %s\n", "Car ID", "Brand", "Model", "Rate/Day", "Status");
                System.out.println("------------------------------------------------------------------");
                for (Car car : cars) {
                    System.out.println(car);
                }

            } else if (choice == 4) {
                System.out.println("\nThank you for using the Car Rental System. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
        scanner.close();
    }
}
