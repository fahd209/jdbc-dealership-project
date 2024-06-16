package com.pluralsight.controllers;

import com.pluralsight.Model.Vehicle;
import com.pluralsight.Services.Daos.VehiclesDao;

import java.util.List;

public class VehiclesController {

    private VehiclesDao vehiclesDao;

    public VehiclesController(VehiclesDao vehiclesDao) {
        this.vehiclesDao = vehiclesDao;
    }

    public List<Vehicle> getAllVehicles()
    {
        List<Vehicle> allVehicles = vehiclesDao.getAllVehicles();

        return allVehicles;
    }

    public void addVehicle(Vehicle vehicle)
    {
        vehiclesDao.addVehicle(vehicle);
    }

    public void removeVehicle(int vinNumber)
    {
        vehiclesDao.removeVehicle(vinNumber);
    }

    public List<Vehicle>  getVehiclesByPriceRange(double minPrice, double maxPrice)
    {
        List<Vehicle> vehiclesByPriceRange = vehiclesDao.getByPriceRange(minPrice, maxPrice);

        return vehiclesByPriceRange;
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model)
    {
        List<Vehicle> vehiclesByMakeModel = vehiclesDao.getVehicleByMakeMode(make, model);

        return vehiclesByMakeModel;
    }

    public List<Vehicle> getVehiclesByYearRange(int startYear, int endYear)
    {
        List<Vehicle> vehiclesByYearRange = vehiclesDao.getVehicleByYearRange(startYear, endYear);

        return vehiclesByYearRange;
    }

    public List<Vehicle> getVehicleByColor(String color)
    {
        List<Vehicle> getVehicleByColor = vehiclesDao.getVehicleByColor(color);

        return getVehicleByColor;
    }

    public List<Vehicle> getVehicleByMileage(int startMileage, int endMileage)
    {
        List<Vehicle> vehiclesByMileage = vehiclesDao.getVehicleByMileageRange(startMileage, endMileage);

        return vehiclesByMileage;
    }

    public List<Vehicle> getVehicleByType(String type)
    {
        List<Vehicle> vehiclesByType = vehiclesDao.getVehicleByType(type);

        return vehiclesByType;
    }

    public Vehicle getVehicleByVin(int vin)
    {
        Vehicle vehicle = vehiclesDao.getVehicleByVin(vin);

        return vehicle;
    }
 }
