package ru.rak;

import ru.rak.cars.Car;
import ru.rak.details.Engine;
import ru.rak.professions.Driver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main() throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a number: 1 - add a car; 2 - get a list of all cars;" +
                " 3 - delete a car; 4 - get a list of car by class ; 5 - exit the program");
        switch (sc.nextInt()) {
            case 1:
                saveCarToFile(Car.addCarToListCars(sc));
                main();
            case 2:
                System.out.println("List of all cars: ");
                System.out.println(getCarsFromFile());
                main();
            case 3:
                System.out.print("Enter the brand of the car to delete: ");
                String brand = sc.next();
                deleteCarByBrand(brand);
                System.exit(0);
            case 4:
                System.out.println("Enter the car class: ");
                String carClass = sc.next();
                System.out.println("List of all " + carClass + " cars: ");
                System.out.println(filteringCarsByClass(carClass));
                main();
            case 5:
                System.exit(0);
            default:
                throw new IllegalStateException("Unexpected value: " );
        }
    }

    public static List<Car> filteringCarsByClass(String carclass) {
        File file = new File("Car.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<Car> cars = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] car = line.split(" ");
                Car currentCar = new Car(car[0], car[1], new Engine(Integer.parseInt(car[2]), car[3]),
                        new Driver(car[4], car[5], Integer.parseInt(car[6]), Integer.parseInt(car[7])));
                if (car[0].equalsIgnoreCase(carclass))
                    cars.add(currentCar);
            }
            return cars;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteCarByBrand(String brand) throws IOException {
        File file = new File("Car.txt");
        File tempFile = new File("tempFile.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile));

        List<Car> cars = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {

            String[] car = line.split(" ");
            Car currentCar = new Car(car[0], car[1], new Engine(Integer.parseInt(car[2]), car[3]),
                    new Driver(car[4], car[5], Integer.parseInt(car[6]), Integer.parseInt(car[7])));
            cars.add(currentCar);
            if (!car[1].equalsIgnoreCase(brand)) {
                bufferedWriter.write(line + "\n");
            }
        }

        bufferedWriter.close();
        bufferedReader.close();

        file.delete();
        tempFile.renameTo(new File("Car.txt"));
    }

    public static List<Car> getCarsFromFile() {
        File file = new File("Car.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<Car> cars = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] car = line.split(" ");
                Car currentCar = new Car(car[0], car[1], new Engine(Integer.parseInt(car[2]), car[3]),
                        new Driver(car[4], car[5], Integer.parseInt(car[6]), Integer.parseInt(car[7])));
                cars.add(currentCar);
            }
            return cars;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveCarToFile(Car car) {
        File file = new File("Car.txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

            String carClass = car.getCarClass();
            String brand = car.getBrand();
            int power = car.engine.getPower();
            String company = car.engine.getCompany();
            String firstName = car.driver.getFirstName();
            String lastName = car.driver.getLastName();
            int age = car.driver.getAge();
            int experience = car.driver.getExperience();
            bufferedWriter.write(carClass + " " + brand + " " + power + " " +
                    company + " " + firstName + " " + lastName + " " + age + " " + experience + " " +
                    System.getProperty("line.separator"));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
