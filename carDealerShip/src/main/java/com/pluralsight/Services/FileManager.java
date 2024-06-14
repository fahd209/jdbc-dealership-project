package com.pluralsight.Services;

import com.pluralsight.Model.DealerShip;
import com.pluralsight.Model.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public static DealerShip getDealership()
    {
        DealerShip dealerShip = null;

        //reading file
        File inventoryFile = new File("files//inventory.csv");
        try(
                FileReader filereader = new FileReader(inventoryFile);
                Scanner reader = new Scanner(filereader)
        )
        {
            // getting dealership from first line of the file
            String firstLine = reader.nextLine();
            String[] dealerShipColumns = firstLine.split("[|]");

            String dealerShipName = dealerShipColumns[0];
            String dealerShipAddress = dealerShipColumns[1];
            String dealerShipPhone = dealerShipColumns[2];

            dealerShip = new DealerShip(dealerShipName, dealerShipAddress, dealerShipPhone);

            while(reader.hasNextLine())
            {
                // reading the rest of the lines
                String line = reader.nextLine();
                String[] vehicleColumns = line.split("[|]");

                int vin = Integer.parseInt(vehicleColumns[0].strip());
                int year = Integer.parseInt(vehicleColumns[1].strip());
                String make = vehicleColumns[2].strip();
                String model = vehicleColumns[3].strip();
                String vehicleType = vehicleColumns[4].strip();
                String color = vehicleColumns[5].strip();
                int odometer = Integer.parseInt(vehicleColumns[6].strip());
                double price = Double.parseDouble(vehicleColumns[7].strip());

                // creating a vehicle object with the data from the csv file
                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

                // adding vehicle to inventory
                dealerShip.addVehicle(vehicle);
            }
        }
        catch (IOException e)
        {
            System.out.println("File does not exist");
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong");
        }
        return dealerShip;
    }

    public static void saveDealerShip(DealerShip dealerShip)
    {

        File inventoryFile = new File("files/inventory.csv");

        // saving data to the csv file when the user leaves
        try(
                FileWriter fileWriter = new FileWriter(inventoryFile);
                PrintWriter writer = new PrintWriter(fileWriter)
                )
        {
            // getting the dealership name and writing it to the file
            String dealerShipName = dealerShip.getName();
            String dealerShipAddress = dealerShip.getAddress();
            String dealerShipPhone = dealerShip.getPhone();

            writer.printf(" %s | %s | %s \n", dealerShipName, dealerShipAddress, dealerShipPhone);

            ArrayList<Vehicle> inventory = dealerShip.getAllVehicles();

            // getting the vehicles in the inventory and writing it to the file
            for(Vehicle vehicle : inventory)
            {
                writer.printf(" %d | %d | %s | %s | %s | %s | %d | %.2f \n", vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice());
            }

        }
        catch (IOException e)
        {
            System.out.println("File does not exist");
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong");
        }
    }
}
