package com.pluralsight.Services;

import com.pluralsight.Model.Vehicle;
import com.pluralsight.Services.Daos.VehiclesDao;
import com.pluralsight.Views.ColorCodes;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlVehiclesDao implements VehiclesDao {
    DataSource dataSource;

    public MySqlVehiclesDao(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public List<Vehicle> getAllVehicles()
    {
        List<Vehicle> allVehicles = new ArrayList<>();

        try(Connection connection = dataSource.getConnection()) {
            String sql = """
                    SELECT V.Vin_number
                              , V.year
                              , V.make
                              , V.model
                              , V.VehicleType
                              , V.Color
                              , V.Odometer
                              , V.Price
                            FROM inventory AS I
                            INNER JOIN vehicles AS V
                                   ON V.Vin_number = I.Vin_number
                            ORDER BY V.Vin_number;
                    """;

            Statement statement = connection.createStatement();
            ResultSet row = statement.executeQuery(sql);

            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String make = row.getString("V.make");
                String model = row.getString("V.model");
                String vehicleType = row.getString("VehicleType");
                String color = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                allVehicles.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return allVehicles;
    }

    @Override
    public void addVehicle(Vehicle vehicle)
    {
        try(Connection connection = dataSource.getConnection()) {
            String sql = """
                    INSERT INTO vehicles
                    (
                    	Vin_number,
                        year,
                        make,
                        model,
                        VehicleType,
                        Color,
                        Odometer,
                    	Price,
                    	Sold
                    )
                    VALUES
                    	(?, ? , ? , ? , ? , ? , ? , ? , ?);
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getVehicleType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.setBoolean(9, false);
            preparedStatement.executeUpdate();

            String addToInventorySql = """
                    INSERT INTO inventory
                    (
                    	dealership_id
                    	, Vin_number
                    )
                    VALUES
                    	(1, ?);
                    """;

            PreparedStatement preparedStatement1 = connection.prepareStatement(addToInventorySql);
            preparedStatement1.setInt(1, vehicle.getVin());

            int rowAffected = preparedStatement1.executeUpdate();
            if (rowAffected > 0)
            {
                System.out.println(ColorCodes.GREEN + vehicle.getMake() + " " + vehicle.getModel() + " was added" + ColorCodes.RESET);
            }
            else
            {
                System.out.println(ColorCodes.RED+ "Failed to add vehicle "+ ColorCodes.RESET);
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void removeVehicle(int vinNumber) {

        try(Connection connection = dataSource.getConnection()) {
            String removingFromVehicleTableSql = """
                    DELETE FROM vehicles
                    WHERE Vin_number = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(removingFromVehicleTableSql);
            preparedStatement.setInt(1, vinNumber);

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(ColorCodes.GREEN + "Vehicle with vin:" + vinNumber + " was removed" + ColorCodes.RESET);
            }
            else{
                System.out.println(ColorCodes.RED+"Failed to remove vehicle"+ColorCodes.RESET);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public List<Vehicle> getByPriceRange(double minPrice, double maxPrice) {

        List<Vehicle> vehiclesByPriceRange = new ArrayList<>();
        try(Connection connection = dataSource.getConnection())
        {
            String sql =
                    """
                        SELECT V.Vin_number
                                	, V.year
                                    , V.make
                                    , V.model
                                    , V.VehicleType
                                    , V.Color
                                    , V.Odometer
                                    , V.Price
                                FROM inventory AS I
                                INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                                WHERE price BETWEEN ? AND ?
                                ORDER BY V.Price;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);

            ResultSet row = preparedStatement.executeQuery();
            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String make = row.getString("V.make");
                String model = row.getString("V.model");
                String vehicleType = row.getString("VehicleType");
                String color = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
                vehiclesByPriceRange.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return vehiclesByPriceRange;
    }

    @Override
    public List<Vehicle> getVehicleByMakeMode(String make, String model) {
        List<Vehicle> vehiclesByMakeModel = new ArrayList<>();
        try(Connection connection = dataSource.getConnection())
        {
            String sql =
                    """
                        SELECT V.Vin_number
                                	, V.year
                                    , V.make
                                    , V.model
                                    , V.VehicleType
                                    , V.Color
                                    , V.Odometer
                                    , V.Price
                                FROM inventory AS I
                                INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                                WHERE V.make = ? AND V.model = ?
                                ORDER BY V.Vin_number;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            ResultSet row = preparedStatement.executeQuery();
            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String vehicleMake = row.getString("V.make");
                String vehicleModel = row.getString("V.model");
                String vehicleType = row.getString("VehicleType");
                String color = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, vehicleMake, vehicleModel, vehicleType, color, odometer, price);
                vehiclesByMakeModel.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return vehiclesByMakeModel;
    }

    @Override
    public List<Vehicle> getVehicleByYearRange(int startYear, int endYear) {

        List<Vehicle> vehiclesByYearRange = new ArrayList<>();
        try(Connection connection = dataSource.getConnection())
        {
            String sql =
                    """
                        SELECT V.Vin_number
                                	, V.year
                                    , V.make
                                    , V.model
                                    , V.VehicleType
                                    , V.Color
                                    , V.Odometer
                                    , V.Price
                                FROM inventory AS I
                                INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                                WHERE V.year BETWEEN ? AND ?
                                ORDER BY V.year;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, startYear);
            preparedStatement.setInt(2, endYear);

            ResultSet row = preparedStatement.executeQuery();
            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String vehicleMake = row.getString("V.make");
                String vehicleModel = row.getString("V.model");
                String vehicleType = row.getString("VehicleType");
                String color = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, vehicleMake, vehicleModel, vehicleType, color, odometer, price);
                vehiclesByYearRange.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return vehiclesByYearRange;
    }

    @Override
    public List<Vehicle> getVehicleByColor(String color) {
        List<Vehicle> vehiclesByYearRange = new ArrayList<>();
        try(Connection connection = dataSource.getConnection())
        {
            String sql =
                    """
                        SELECT V.Vin_number
                                	, V.year
                                    , V.make
                                    , V.model
                                    , V.VehicleType
                                    , V.Color
                                    , V.Odometer
                                    , V.Price
                                FROM inventory AS I
                                INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                                WHERE V.Color = ?
                                ORDER BY V.Vin_number;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, color);

            ResultSet row = preparedStatement.executeQuery();
            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String vehicleMake = row.getString("V.make");
                String vehicleModel = row.getString("V.model");
                String vehicleType = row.getString("VehicleType");
                String vehicleColor = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, vehicleMake, vehicleModel, vehicleType, vehicleColor, odometer, price);
                vehiclesByYearRange.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return vehiclesByYearRange;
    }

    @Override
    public List<Vehicle> getVehicleByMileageRange(int startMileage, int endMileage) {
        List<Vehicle> vehiclesByMileageRange = new ArrayList<>();
        try(Connection connection = dataSource.getConnection())
        {
            String sql =
                    """
                        SELECT V.Vin_number
                                	, V.year
                                    , V.make
                                    , V.model
                                    , V.VehicleType
                                    , V.Color
                                    , V.Odometer
                                    , V.Price
                                FROM inventory AS I
                                INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                                WHERE V.Odometer BETWEEN ? AND ?
                                ORDER BY V.Odometer;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, startMileage);
            preparedStatement.setInt(2, endMileage);

            ResultSet row = preparedStatement.executeQuery();
            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String vehicleMake = row.getString("V.make");
                String vehicleModel = row.getString("V.model");
                String vehicleType = row.getString("VehicleType");
                String vehicleColor = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, vehicleMake, vehicleModel, vehicleType, vehicleColor, odometer, price);
                vehiclesByMileageRange.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return vehiclesByMileageRange;
    }

    @Override
    public List<Vehicle> getVehicleByType(String vehicleType) {
        List<Vehicle> vehiclesByMileageRange = new ArrayList<>();
        try(Connection connection = dataSource.getConnection())
        {
            String sql =
                    """
                        SELECT V.Vin_number
                                	, V.year
                                    , V.make
                                    , V.model
                                    , V.VehicleType
                                    , V.Color
                                    , V.Odometer
                                    , V.Price
                                FROM inventory AS I
                                INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                                WHERE V.VehicleType = ?
                                ORDER BY V.Vin_number;
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, vehicleType);

            ResultSet row = preparedStatement.executeQuery();
            while (row.next())
            {
                int vin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String vehicleMake = row.getString("V.make");
                String vehicleModel = row.getString("V.model");
                String Type = row.getString("VehicleType");
                String vehicleColor = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");

                Vehicle vehicle = new Vehicle(vin, year, vehicleMake, vehicleModel, Type, vehicleColor, odometer, price);
                vehiclesByMileageRange.add(vehicle);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return vehiclesByMileageRange;
    }

    public Vehicle getVehicleByVin(int vin)
    {
        Vehicle vehicle = null;
        try(Connection connection = dataSource.getConnection())
        {
            String sqlGetVehicleByVin = """
                    SELECT V.Vin_number
                           , V.year
                           , V.make
                           , V.model
                           , V.VehicleType
                           , V.Color
                           , V.Odometer
                           , V.Price
                    FROM inventory AS I
                    INNER JOIN vehicles AS V
                                	ON V.Vin_number = I.Vin_number
                    WHERE V.Vin_number = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(sqlGetVehicleByVin);
            statement.setInt(1, vin);
            ResultSet row = statement.executeQuery();

            if (row.next())
            {
                int vehicleVin = row.getInt("V.Vin_number");
                int year = row.getInt("V.year");
                String vehicleMake = row.getString("V.make");
                String vehicleModel = row.getString("V.model");
                String Type = row.getString("VehicleType");
                String vehicleColor = row.getString("V.Color");
                int odometer = row.getInt("V.Odometer");
                double price = row.getDouble("V.price");
                vehicle = new Vehicle(vehicleVin, year, vehicleMake, vehicleModel, Type, vehicleColor, odometer, price);
                return vehicle;
            }
            else
            {
                System.out.println(ColorCodes.RED+"No vehicles found"+ColorCodes.RESET);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }
}
