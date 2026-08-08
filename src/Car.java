public class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isRented;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isRented = false;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }

    public boolean isRented() {
        return isRented;
    }

    public void rent() {
        isRented = true;
    }

    public void returnCar() {
        isRented = false;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-12s | %-12s | $%.2f/day | %s",
                carId, brand, model, basePricePerDay, (isRented ? "Rented" : "Available"));
    }
}
