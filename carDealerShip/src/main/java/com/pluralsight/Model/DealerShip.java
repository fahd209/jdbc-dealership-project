package com.pluralsight.Model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DealerShip {
    private String name;
    private String address;
    private String phone;
    ArrayList<Vehicle> inventory = new ArrayList<>();
    ArrayList<Contract> contracts = new ArrayList<>();

    public DealerShip(String name, String address, String phone)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Vehicle> getAllVehicles()
    {

        return inventory;
    }

    public void addContract(Contract contract)
    {
        contracts.add(contract);
    }

    public ArrayList<Contract> getAllContracts()
    {
        return contracts;
    }

    public void addVehicle(Vehicle vehicle)
    {
        inventory.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle)
    {
        inventory.remove(vehicle);
    }

    public ArrayList<Vehicle> getVehicleByPriceRange(double min, double max)
    {
        return (ArrayList<Vehicle>) getAllVehicles().stream()
                .filter(vehicle -> vehicle.getPrice() >= min)
                .filter(vehicle -> vehicle.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public ArrayList<Vehicle> getVehiclesByMakeAndModel(String make, String model)
    {
        return (ArrayList<Vehicle>) getAllVehicles().stream()
                .filter(vehicle -> vehicle.getMake().equalsIgnoreCase(make) && vehicle.getModel().equalsIgnoreCase(model))
                .collect(Collectors.toList());
    }

    public ArrayList<Vehicle> getVehiclesByYearRange(int startYear, int endYear)
    {
        return (ArrayList<Vehicle>) getAllVehicles().stream()
                .filter(vehicle -> vehicle.getYear() >= startYear && vehicle.getYear() <= endYear)
                .collect(Collectors.toList());
    }

    public ArrayList<Vehicle> getVehiclesByColor(String color)
    {
        return (ArrayList<Vehicle>) getAllVehicles().stream()
                .filter(vehicle -> vehicle.getColor().equalsIgnoreCase(color))
                .collect(Collectors.toList());
    }

    public ArrayList<Vehicle> getVehicleByMileageRange(int startingMileage, int endingMileage)
    {
        return (ArrayList<Vehicle>) getAllVehicles().stream()
                .filter(vehicle -> vehicle.getOdometer() >= startingMileage && vehicle.getOdometer() <= endingMileage)
                .collect(Collectors.toList());
    }

    public ArrayList<Vehicle> getVehiclesByType (String vehicleType)
    {
        return (ArrayList<Vehicle>) getAllVehicles().stream()
                .filter(vehicle -> vehicle.getVehicleType().equalsIgnoreCase(vehicleType))
                .collect(Collectors.toList());
    }

    public Vehicle getVehicleByVinNumber(int vinNumber)
    {
        return getAllVehicles().stream()
                .filter(vehicle1 -> vehicle1.getVin() == vinNumber)
                .findFirst().get();
    }
}
