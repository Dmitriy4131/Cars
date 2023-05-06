package ru.rak;

import ru.rak.cars.Car;
import ru.rak.details.Engine;
import ru.rak.professions.Driver;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
                List<Map<String, Object>>  listCars = readFile();
                for (Map<String, Object> car : listCars){
                    System.out.println(car);
                }
                main();
            case 3:
                System.out.print("Enter the brand of the car to delete: ");
                String brand = sc.next();
                deleteCarByBrand(brand);
                System.exit(0);
            case 4:
                sc.nextLine();
                Map<String, Object> listParameters = new HashMap<>();
                System.out.println("Possible parameters for filtering(class, brand, color, power)");

                System.out.print("Enter the car class: ");
                listParameters.put("carClass", sc.nextLine());
                System.out.print("Enter the car brand: ");
                listParameters.put("brand", sc.nextLine());
                System.out.print("Enter the car color: ");
                listParameters.put("color", sc.nextLine());
                System.out.print("Enter the car power: ");
                listParameters.put("power", sc.nextInt());
                sc.nextLine();

                System.out.println("List of all cars with parameters " + listParameters);
                System.out.println("List of filtered cars:");
                List<Map<String, Object>> filteredListCars = filteringCarsByParameters(Objects.requireNonNull(readFile()), listParameters);
                for (Map<String, Object> car : filteredListCars) {
                    System.out.println(car);
                }
                main();
            case 5:
                System.exit(0);
            default:
                throw new IllegalStateException("Unexpected value: ");
        }
    }

    public static List<Map<String, Object>> readFile() {
        File file = new File("Car.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            List<Map<String, Object>> cars = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Map<String, Object> item = new LinkedHashMap<>();
                String[] car = line.split(" ");
                item.put("carClass", car[0]);
                item.put("brand", car[1]);
                item.put("color", car[2]);
                item.put("power", car[3]);
                item.put("company", car[4]);
                item.put("firstName", car[5]);
                item.put("lastName", car[6]);
                item.put("age", car[7]);
                item.put("experience", car[8]);
                cars.add(item);
            }
            return cars;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Map<String, Object>> filteringCarsByParameters(List<Map<String, Object>> carList, Map<String, Object> listParameters) {
        List<Map<String, Object>> filterList = new ArrayList<>();

        for (Map<String, Object> item : carList) {
            boolean isMatch = true;
            for (Map.Entry<String, Object> entry : listParameters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (!item.containsKey(key) ||
                        (value instanceof String stringValue &&
                                (!stringValue.isEmpty() && !stringValue.equals(item.get(key)))) ||
                        (value instanceof Integer intValue &&
                                (intValue != 0 && intValue < Integer.parseInt((String) item.get(key))))) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                filterList.add(item);
            }
        }
        return filterList;
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
            Car currentCar = new Car(car[0], car[1], car[2], new Engine(Integer.parseInt(car[3]), car[4]),
                    new Driver(car[5], car[6], Integer.parseInt(car[7]), Integer.parseInt(car[8])));
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

    public static void saveCarToFile(Car car) {
        File file = new File("Car.txt");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

            String carClass = car.getCarClass();
            String brand = car.getBrand();
            String color = car.getColor();
            int power = car.engine.getPower();
            String company = car.engine.getCompany();
            String firstName = car.driver.getFirstName();
            String lastName = car.driver.getLastName();
            int age = car.driver.getAge();
            int experience = car.driver.getExperience();
            bufferedWriter.write(carClass + " " + brand + " " + color + " " + power + " " +
                    company + " " + firstName + " " + lastName + " " + age + " " + experience + " " +
                    System.getProperty("line.separator"));
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
