package com.pluralsight.ui;

import com.pluralsight.Model.*;
import com.pluralsight.Services.ContractFileManager;
import com.pluralsight.Services.FileManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;


public class UserInterface {
    String vinTitle = "Vin";
    String yearTitle = "Year";
    String makeTitle = "Make";
    String modelTitle = "Model";
    String typeTitle = "type";
    String colorTitle = "Color";
    String odometerTitle = "Odometer";
    String priceTitle = "Price";
    private static Scanner userInput = new Scanner(System.in);

    public UserInterface()
    {
    }

    public void run()
    {
        DealerShip dealerShip = FileManager.getDealership();
        display(dealerShip);
    }

    // displaying menu
    public void display(DealerShip dealerShip)
    {
        System.out.println("----------------Year Ups dealer ship----------------");
        String input = "";
        int choice = 10;
        while(choice != 0)
        {
            try{
                System.out.println();
                System.out.println(ColorCodes.YELLOW + "(1)" + ColorCodes.RESET + ColorCodes.CYAN + " - List All vehicles" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(2)" + ColorCodes.RESET + ColorCodes.CYAN + " - Add a vehicle" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(3)" + ColorCodes.RESET + ColorCodes.CYAN + " - Remove a vehicle" + ColorCodes.RESET );
                System.out.println(ColorCodes.YELLOW + "(4)" + ColorCodes.RESET + ColorCodes.CYAN + " - Find vehicles within a price range" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(5)" + ColorCodes.RESET + ColorCodes.CYAN + " - Find vehicles by make / model" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(6)" + ColorCodes.RESET + ColorCodes.CYAN + " - Find vehicles by year range" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(7)" + ColorCodes.RESET + ColorCodes.CYAN + " - Find vehicles by color" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(8)" + ColorCodes.RESET + ColorCodes.CYAN + " - Find vehicles by mileage range" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(9)" + ColorCodes.RESET + ColorCodes.CYAN + " - Find vehicles by type (Sedan, truck, SUV, van)" + ColorCodes.RESET);
                System.out.println(ColorCodes.YELLOW + "(10)" + ColorCodes.RESET + ColorCodes.CYAN +" - Buy Vehicle/ Lease Vehicle" + ColorCodes.RESET);
                System.out.println(ColorCodes.RED + "(0) - Save and quit" + ColorCodes.RESET);
                System.out.print("Enter your input: ");
                input = userInput.nextLine().strip().replace(" ", "");
                choice = Integer.parseInt(input);
                switch (choice)
                {
                    case 1:
                        displayAllVehicles(dealerShip);
                        break;
                    case 2:
                        addVehicle(dealerShip);
                        break;
                    case 3:
                        removeVehicle(dealerShip);
                        break;
                    case 4:
                        findVehicleWithPriceRange(dealerShip);
                        break;
                    case 5:
                        findVehiclesByMakeAndModel(dealerShip);
                        break;
                    case 6:
                        findByYearRange(dealerShip);
                        break;
                    case 7:
                        findVehiclesByColor(dealerShip);
                        break;
                    case 8:
                        findVehiclesByMileageRange(dealerShip);
                        break;
                    case 9:
                        findVehicleByType(dealerShip);
                        break;
                    case 10:
                        leaseOrBuyVehicle(dealerShip);
                        break;
                    case 0:
                        System.out.println();
                        FileManager.saveDealerShip(dealerShip);
                        ContractFileManager.saveContract(dealerShip);
                        System.out.println("Good bye :)");
                        break;
                    default:
                        System.out.println();
                        System.out.println("Invalid input");
                }
            }
            catch (InputMismatchException e)
            {
                userInput.nextLine();
                System.out.println();
                System.out.println("Invalid input");
            }
            catch (NumberFormatException e)
            {
                System.out.println();
                System.out.println("Invalid input please enter a number");
            }
            catch (Exception e)
            {
                System.out.println();
                System.out.println("Something went wrong, try again");
            }
        }
    }

    private void displayAllVehicles(DealerShip dealerShip)
    {
        System.out.println();
        System.out.println("-------------------------------------------All vehicles---------------------------------------------");
        System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
        System.out.println("-".repeat(100));
        for (Vehicle vehicle : dealerShip.getAllVehicles())
        {
            System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
            System.out.println("-".repeat(100));
        }
    }

    public void addVehicle(DealerShip dealerShip)
    {
        // getting new vehicles info
        try {
            System.out.println();
            System.out.println("Enter vehicle information");

            System.out.print("Enter the vin of the vehicle: ");
            String vinInput = userInput.nextLine().strip().replace(" ", "");
            int vin = Integer.parseInt(vinInput);

            System.out.print("Enter the year of the vehicle: ");
            String yearInput = userInput.nextLine().strip().replace(" ", "");
            int year = Integer.parseInt(yearInput);

            System.out.print("Enter vehicle make: ");
            String make = userInput.nextLine().strip();

            System.out.print("Enter vehicle model: ");
            String model = userInput.nextLine().strip();

            System.out.print("Enter vehicle type: ");
            String vehicleType = userInput.nextLine().strip();

            System.out.print("Enter vehicle color: ");
            String color = userInput.nextLine();

            System.out.print("Enter vehicle odometer: ");
            String odometerInput = userInput.nextLine().strip();
            int odometer = Integer.parseInt(odometerInput);

            System.out.print("Enter vehicle price: ");
            String priceInput = userInput.nextLine().strip();
            double price = Double.parseDouble(priceInput);

            // adding vehicle to dealership
            Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
            dealerShip.addVehicle(vehicle);

            System.out.println();
            System.out.println(vehicle.getMake() + " " + vehicle.getModel() + " added to inventory");
        }
        catch (NumberFormatException e)
        {
            System.out.println();
            System.out.println("Failed to convert input, try again");
        }
        catch (InputMismatchException e)
        {
            userInput.nextLine();
            System.out.println("Invalid input");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong try again");
        }

    }

    public void removeVehicle(DealerShip dealerShip)
    {
        // getting vehicle vin
        int vin = 0;
        try {
            System.out.println();
            System.out.println("Enter the vin for the vehicle you want to remove");
            System.out.print("Enter input: ");
            vin = userInput.nextInt();
            userInput.nextLine();

            ArrayList<Vehicle> allVehicles = dealerShip.getAllVehicles();

            for (int i = 0; i < allVehicles.size(); i++)
            {
                Vehicle vehicle = allVehicles.get(i);
                if(vehicle.getVin() == vin)
                {
                    // removing vehicle from inventory
                    dealerShip.removeVehicle(vehicle);
                    System.out.println();
                    System.out.println(vehicle.getMake() + " " + vehicle.getModel() + " removed from inventory");
                }
            }

        }
        catch (FormatFlagsConversionMismatchException e)
        {
            System.out.println("Invalid input please enter only numbers");
        }
        catch (InputMismatchException e)
        {
            userInput.nextLine();
            System.out.println("Invalid input");
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong try again");
        }

    }

    public void findVehicleWithPriceRange(DealerShip dealerShip)
    {
        try
        {
            // getting min and max price
            System.out.println();
            System.out.print("Enter minimum price: ");
            double minPrice = userInput.nextDouble();
            userInput.nextLine();

            System.out.print("Enter maximum price: ");
            double maxPrice = userInput.nextDouble();
            userInput.nextLine();

            // searching in my dealership inventory and displaying it on the screen
            ArrayList<Vehicle> priceRangeVehicles = dealerShip.getVehicleByPriceRange(minPrice, maxPrice);

            // displaying vehicles
            System.out.println();
            System.out.println("-----------------------------------------Vehicles by price range------------------------------------");
            System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
            System.out.println("-".repeat(100));
            for(Vehicle vehicle : priceRangeVehicles)
            {
                System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
                System.out.println("-".repeat(100));
            }
            if(priceRangeVehicles.isEmpty())
            {
                System.out.println("No vehicles found");
            }

        }
        catch (NumberFormatException e)
        {
            System.out.println();
            System.out.println("Please enter your input in numbers");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong, try again");
        }
    }

    public void findVehiclesByMakeAndModel(DealerShip dealerShip)
    {
        // prompting user for make and model
        try
        {
            System.out.println();
            System.out.print("Enter the make: ");
            String make = userInput.nextLine().strip();

            System.out.print("Enter the model: ");
            String model = userInput.nextLine().strip();

            // filtering and adding the vehicle that match input make and model to the arrayList
            ArrayList<Vehicle> vehiclesByMakeAndModel = dealerShip.getVehiclesByMakeAndModel(make, model);

            // displaying vehicles
            System.out.println("-----------------------------------------Vehicles by model name------------------------------------");
            System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
            System.out.println("-".repeat(100));
            for(Vehicle vehicle : vehiclesByMakeAndModel)
            {
                System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
                System.out.println("-".repeat(100));
            }

            if (vehiclesByMakeAndModel.isEmpty())
            {
                System.out.println("No vehicles found");
            }

        }
        catch (InputMismatchException e)
        {
            System.out.println();
            System.out.println("Invalid input");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong, try again");
        }
    }

    public void findByYearRange(DealerShip dealerShip)
    {
        try
        {
            //prompting user for start year and end year
            System.out.println();
            System.out.print("Enter start year: ");
            int startYear = userInput.nextInt();

            System.out.print("Enter end year: ");
            int endYear = userInput.nextInt();
            userInput.nextLine();

            // getting all the vehicles with in that year range
            ArrayList<Vehicle> vehiclesByYearRange = dealerShip.getVehiclesByYearRange(startYear, endYear);

            //displaying the vehicles
            System.out.println();
            System.out.println("-----------------------------------------Vehicles by year range------------------------------------");
            System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
            System.out.println("-".repeat(100));
            for(Vehicle vehicle : vehiclesByYearRange)
            {
                System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
                System.out.println("-".repeat(100));
            }

            if(vehiclesByYearRange.isEmpty())
            {
                System.out.println("No vehicles found");
            }

        }
        catch (NumberFormatException e)
        {
            System.out.println();
            System.out.println("Invalid input please enter only numbers");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong");
        }
    }

    public void findVehiclesByColor(DealerShip dealerShip)
    {
        try
        {
            // promoting user for vehicle color
            System.out.println();
            System.out.print("Enter vehicles color: ");
            String color = userInput.nextLine();

            // filtering and checking if the vehicles color is equal to the color provided
            ArrayList<Vehicle> vehiclesByColor = dealerShip.getVehiclesByColor(color);

            // displaying vehicles
            System.out.println();
            System.out.println("--------------------------------------------Vehicles by color---------------------------------------");
            System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
            System.out.println("-".repeat(100));
            for(Vehicle vehicle : vehiclesByColor)
            {
                System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
                System.out.println("-".repeat(100));
            }

            if(vehiclesByColor.isEmpty())
            {
                System.out.println("No vehicles found");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println();
            System.out.println("Invalid input");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong try again");
        }
    }

    public void findVehiclesByMileageRange(DealerShip dealerShip)
    {
        // prompting user for input
        System.out.println();
        System.out.print("Enter starting miles range: ");
        int startingMileage = userInput.nextInt();

        System.out.print("Enter ending miles range: ");
        int endingMileage = userInput.nextInt();
        userInput.nextLine();

        // filtering and checking if vehicles mileage is in between what the user provided
        ArrayList<Vehicle> vehiclesByMileRange = dealerShip.getVehicleByMileageRange(startingMileage, endingMileage);

        //displaying vehicles
        System.out.println("------------------------------------------Vehicles by mileage range-------------------------------------");
        System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
        System.out.println("-".repeat(100));
        for (Vehicle vehicle : vehiclesByMileRange)
        {
            System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
            System.out.println("-".repeat(100));
        }

        if(vehiclesByMileRange.isEmpty())
        {
            System.out.println("No vehicles found");
        }
    }

    public void findVehicleByType(DealerShip dealerShip)
    {
        try
        {
            // prompting user for input
            System.out.println();
            System.out.print("Enter the vehicle type: ");
            String vehicleType = userInput.nextLine().strip();

            // filtering and checking if the vehicle type is equal to what the user provided
            ArrayList<Vehicle> vehiclesByVehicleType = dealerShip.getVehiclesByType(vehicleType);

            // displaying vehicles to the screen
            System.out.println("------------------------------------------Vehicles by type---------------------------------------");
            System.out.printf(" %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s \n", vinTitle, yearTitle, makeTitle, modelTitle, typeTitle, colorTitle, odometerTitle, priceTitle);
            System.out.println("-".repeat(100));
            for (Vehicle vehicle : vehiclesByVehicleType)
            {
                System.out.printf(" %-10d | %-10d | %-10s | %-10s | %-10s | %-10s | %-10d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
                System.out.println("-".repeat(100));
            }

            if(vehiclesByVehicleType.isEmpty())
            {
                System.out.println("No vehicles found");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid input");
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong, try again");
        }
    }

    public void leaseOrBuyVehicle(DealerShip dealerShip)
    {
        // prompting user to buy or lease
        System.out.println();
        System.out.println("(1) - buy vehicle");
        System.out.println("(2) - lease vehicle");
        System.out.println("(3) - Cancel");
        System.out.print("Enter choice:");
        int choice = Integer.parseInt(userInput.nextLine());
        switch (choice)
        {
            case 1:
                buyVehicle(dealerShip);
                break;
            case 2:
                leaseVehicle(dealerShip);
                break;
            case 3:
                break;
        }
    }
    public void buyVehicle(DealerShip dealerShip)
    {
        // prompting user for info and searching for vehicle with vin number
        try {
            System.out.println();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            String date = currentDate.format(formatter);
            System.out.print("Enter you're Name: ");
            String name = userInput.nextLine().strip();
            System.out.print("Enter you're Email: ");
            String email = userInput.nextLine().strip();
            System.out.print("Enter the Vin of the Vehicle you would like to purchase: ");
            int vin = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("Would you like to Finance the vehicle (Yes/No): ");
            boolean isFinance = userInput.nextLine().strip().equalsIgnoreCase("yes");
            System.out.print("AWESOME, Let's go start the paperwork!");
            System.out.println();
            System.out.println();

            //Search for vehicle in dealership arrayList
            Vehicle vehicle = dealerShip.getVehicleByVinNumber(vin);
            Contract sale = new Sales(date, name, email, vehicle, isFinance);
            dealerShip.addContract(sale);
            dealerShip.removeVehicle(vehicle);
            System.out.println();
            System.out.println(vehicle.getMake() + " " + vehicle.getModel() + " Vin:" + vehicle.getVin() + "| was sold to " + name);
        }
        catch (NumberFormatException e)
        {
            System.out.println();
            System.out.println("Please enter a number for the vin number");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong, try again");
        }
    }

    public void leaseVehicle(DealerShip dealerShip)
    {
        // prompting user for info and searching for vehicle with vin number
        try {
            System.out.println();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
            String date = currentDate.format(formatter);
            System.out.print("Enter you're Name: ");
            String name = userInput.nextLine().strip();
            System.out.print("Enter you're Email: ");
            String email = userInput.nextLine().strip();
            System.out.print("Enter the Vin of the Vehicle you would like to purchase: ");
            int vin = Integer.parseInt(userInput.nextLine().strip());
            System.out.print("AWESOME, Let's go start the paperwork!");
            System.out.println();
            System.out.println();

            //Search for vehicle in dealership arrayList
            Vehicle vehicle = dealerShip.getVehicleByVinNumber(vin);
            Contract lease = new Lease(date, name, email, vehicle);
            dealerShip.addContract(lease);
            dealerShip.removeVehicle(vehicle);
            System.out.println();
            System.out.println(vehicle.getMake() + " " + vehicle.getModel() + " Vin:" + vehicle.getVin() + "| was leased to " + name);
        }
        catch (NumberFormatException e)
        {
            System.out.println();
            System.out.println("Invalid input, please enter only numbers for vin number");
        }
        catch (Exception e)
        {
            System.out.println();
            System.out.println("Something went wrong, try again");
        }
    }
}
