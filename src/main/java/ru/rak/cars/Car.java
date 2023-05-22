package ru.rak.cars;

import ru.rak.details.Engine;
import ru.rak.professions.Driver;

import java.util.Scanner;

public class Car {
    private String carClass, brand, color;
    public Engine engine;
    public Driver driver;

    public Car(String carClass, String brand,String color, Engine engine, Driver driver) {
        this.carClass = carClass;
        this.brand = brand;
        this.color = color;
        this.engine = engine;
        this.driver = driver;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public static Car addCarToListCars(Scanner scanner) {
        Car car = new Car(" ", " "," ", new Engine(0, ""),
                new Driver(" ", " ", 0, 0));
        Scanner sc = new Scanner(System.in);
        System.out.print("CarClass ");
        String carClass = sc.next();
        car.setCarClass(carClass);
        System.out.print("Brand ");
        String brand = sc.next();
        car.setBrand(brand);
        System.out.print("Color ");
        String color = sc.next();
        car.setColor(color);

        System.out.print("Power ");
        int power = sc.nextInt();
        car.engine.setPower(power);
        System.out.print("Company ");
        String company = sc.next();
        car.engine.setCompany(company);

        System.out.print("FirstName ");
        String firstName = sc.next();
        car.driver.setFirstName(firstName);
        System.out.print("LastName ");
        String lastName = sc.next();
        car.driver.setLastName(lastName);
        System.out.print("age ");
        int age = sc.nextInt();
        car.driver.setAge(age);
        System.out.print("Experience ");
        int experience = sc.nextInt();
        car.driver.setExperience(experience);
        return car;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carClass='" + getCarClass() + '\'' +
                ", brand='" + getBrand() + '\'' +
                ", color='" + getColor() + '\'' +
                ", power=" + engine.getPower() +
                ", company='" + engine.getCompany() + '\'' +
                ", firstName='" + driver.getFirstName() + '\'' +
                ", lastName='" + driver.getLastName() + '\'' +
                ", age=" + driver.getAge() +
                ", experience=" + driver.getExperience() +
                "}\n";
    }
}
