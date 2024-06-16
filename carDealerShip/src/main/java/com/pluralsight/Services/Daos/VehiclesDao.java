package com.pluralsight.Services.Daos;

import com.pluralsight.Model.Vehicle;
import java.util.List;

public interface VehiclesDao {
    List<Vehicle> getAllVehicles();

    void addVehicle(Vehicle vehicle);

    void removeVehicle(int vinNumber);

    List<Vehicle> getByPriceRange(double minPrice, double maxPrice);

    List<Vehicle> getVehicleByMakeMode(String make, String model);

    List<Vehicle> getVehicleByYearRange(int startYear, int endYear);

    List<Vehicle> getVehicleByColor(String color);

    List<Vehicle> getVehicleByMileageRange(int startMileage, int endMileage);

    List<Vehicle> getVehicleByType(String vehicleType);

    Vehicle getVehicleByVin(int vin);
}
