package com.pluralsight.Model;

import com.pluralsight.Services.FileManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DealerShipTest {

    @Test
    public void getAllVehicles_shouldReturn_theInventory()
    {
        //Arrange
        DealerShip dealerShip = new DealerShip("D & B Used Cars", "\"111 Old Benbrook Rd, Dallas, TX 45137\"","817-555-5555");

        //act
        ArrayList<Vehicle> allVehicles = dealerShip.getAllVehicles();

        //assert
        assertEquals(dealerShip.inventory,allVehicles, "Because get allAllVehicles should return the inventory");
    }

    @Test
    public void addVehicle_ShouldAdd_AVehicleToTheInvenotry()
    {
        //arrange
        DealerShip dealerShip = new DealerShip("D & B Used Cars", "\"111 Old Benbrook Rd, Dallas, TX 45137\"","817-555-5555");
        Vehicle vehicle = new Vehicle(10112, 1993, "Ford", "Explorer", "SUV", "Red", 525123, 995);

        //act
        dealerShip.addVehicle(vehicle);

        //assert
        assertTrue(dealerShip.getAllVehicles().contains(vehicle), "because add vehicle should add a vehicle to the dealership inventory");
    }

    @Test
    public void removeVehicle_ShouldRemove_VehicleFromTheInventory()
    {
        //arrange
        DealerShip dealerShip = new DealerShip("D & B Used Cars", "\"111 Old Benbrook Rd, Dallas, TX 45137\"","817-555-5555");
        Vehicle vehicle = new Vehicle(10112, 1993, "Ford", "Explorer", "SUV", "Red", 525123, 995);

        //act
        dealerShip.removeVehicle(vehicle);

        //assert
        assertFalse(dealerShip.getAllVehicles().contains(vehicle), "because remove vehicle should remove a vehicle from the dealerships inventory");
    }

    @Test
    public void findVehicleByPrice_shouldReturn_allVehiclesWithInThatPriceRange()
    {
        //arrange
        DealerShip dealerShip = FileManager.getDealership();
        double startingPrice = 10000;
        double endingPrice = 40000;


        //act
        ArrayList<Vehicle> vehiclesByPriceRange = dealerShip.getVehicleByPriceRange(startingPrice, endingPrice);

        //assert
        for (Vehicle vehicle : vehiclesByPriceRange)
        {
            assertTrue(vehicle.getPrice() >= startingPrice && vehicle.getPrice() <= endingPrice, "because findVehiclesByPriceRange() should return all vehicles within that price range");
        }
    }

    @Test
    public void getVehiclesByMakeAndModel_shouldReturn_allTheVehiclesByMakeAndMode()
    {
        //arrange
        DealerShip dealerShip = FileManager.getDealership();
        String make = "Honda";
        String model = "Accord";

        //act
        ArrayList<Vehicle> vehiclesByMakeAndModel = dealerShip.getVehiclesByMakeAndModel(make, model);

        //assert
        for (Vehicle vehicle : vehiclesByMakeAndModel)
        {
            assertTrue(vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model), "because getVehicleByMakeAndMode should return all vehicles by make and model");
        }
    }

    @Test
    public void getVehiclesByYearRange_shouldReturn_allVehiclesWithInTheYearRange()
    {
        //arrange
        DealerShip dealerShip = FileManager.getDealership();
        int startingYear = 2015;
        int endingYear = 2020;

        //act
        ArrayList<Vehicle> vehiclesByMakeAndModel = dealerShip.getVehiclesByYearRange(startingYear, endingYear);

        //assert
        for(Vehicle vehicle : vehiclesByMakeAndModel)
        {
            assertTrue(vehicle.getYear() >= startingYear && vehicle.getYear() <= endingYear, "because getVehicles by year range should return all vehicles by year range");
        }
    }

    @Test
    public void getVehiclesByColor_shouldReturn_allVehicleWithProvidedColor()
    {
        //arrange
        DealerShip dealerShip = FileManager.getDealership();
        String color = "Red";

        //act
        ArrayList<Vehicle> vehiclesByColor = dealerShip.getVehiclesByColor(color);

        //assert
        for(Vehicle vehicle : vehiclesByColor)
        {
            assertTrue(vehicle.getColor().equalsIgnoreCase(color), "because getAllVehiclesByColor should return all the vehicles by the color provided");
        }
    }

    @Test
    public void getVehiclesByType_shouldReturn_allVehiclesByTheTypeProvide()
    {
        //arrange
        DealerShip dealerShip = FileManager.getDealership();
        String type = "sedan";

        //act
        ArrayList<Vehicle> vehiclesByType = dealerShip.getVehiclesByType(type);

        //assert
        for (Vehicle vehicle : vehiclesByType)
        {
            assertTrue(vehicle.getVehicleType().equalsIgnoreCase(type), "because getVehiclesByType() should return all vehicles by the time provided");
        }
    }
}