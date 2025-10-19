package com.pluralsight.Services;

import com.pluralsight.Model.Contract;
import com.pluralsight.Model.Sales;
import com.pluralsight.Model.Vehicle;
import com.pluralsight.Services.Daos.ContractDao;
import com.pluralsight.Views.ColorCodes;

import javax.sql.DataSource;
import java.rmi.dgc.Lease;
import java.sql.*;
import java.util.List;

public class MySqlContractDao implements ContractDao {

    DataSource dataSource;

    public MySqlContractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addLeaseContract(Contract contract) {
        try (Connection connection = dataSource.getConnection()) {
            Vehicle vehicle = contract.getVehicleSold();

            // Update the vehicle to mark it as sold
            String updateVehicleToSoldSql = """ 
                                                UPDATE vehicles
                                                SET Sold = true
                                                WHERE Vin_number = ?;
                                                """;
            try (PreparedStatement updateVehicle = connection.prepareStatement(updateVehicleToSoldSql)) {
                updateVehicle.setInt(1, vehicle.getVin());
                int rowsAffected = updateVehicle.executeUpdate();

                if (rowsAffected > 0) {
                    // Vehicle update successful, proceed to insert lease contract
                    String sqlAddLease = """
                        INSERT INTO lease_contract
                        (Customer_name, Customer_email, lease_date, total_price, monthly_Payment, Vin_number, dealership_id)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                        """;

                    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlAddLease)) {
                        preparedStatement.setString(1, contract.getCustomerName());
                        preparedStatement.setString(2, contract.getCustomerEmail());
                        preparedStatement.setString(3, contract.getDate());
                        preparedStatement.setDouble(4, contract.getTotalPrice());
                        preparedStatement.setDouble(5, contract.getMonthlyPayment());
                        preparedStatement.setInt(6, vehicle.getVin());
                        preparedStatement.setInt(7, 1); // Assuming dealership_id is 1

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                } else {
                    // No rows were updated in vehicles table, likely due to incorrect VIN or vehicle already sold
                    System.out.println("Wrong VIN number or the vehicle is already sold");
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    @Override
    public void addSaleContract(Contract sale) {
        try(Connection connection = dataSource.getConnection())
        {
            // updating the vehicle in sale to sold
            Vehicle vehicle = sale.getVehicleSold();

            String updateVehicleToSoldSql = """ 
                                                UPDATE vehicles
                                                SET Sold = true
                                                WHERE Vin_number = ?;
                                                """;
            try(PreparedStatement updateVehicle = connection.prepareStatement(updateVehicleToSoldSql))
            {
                updateVehicle.setInt(1, vehicle.getVin());
                int rowsAffected = updateVehicle.executeUpdate();

                // if the row was changed then add sale
                if(rowsAffected > 0)
                {
                    String addSaleSql = """
                            INSERT INTO sales_contract
                            (
                                Customer_name
                                , Customer_email
                                , sale_date
                                , isFinanced
                                , total_price
                                , monthly_Payment
                                , Vin_number
                                , dealership_id
                            )
                            VALUES
                            (?, ?, ?, ?, ?, ?, ?, ?);
                            """;
                    try(PreparedStatement addSaleVehicle = connection.prepareStatement(addSaleSql))
                    {
                        addSaleVehicle.setString(1, sale.getCustomerName());
                        addSaleVehicle.setString(2, sale.getCustomerEmail());
                        addSaleVehicle.setString(3, sale.getDate());
                        addSaleVehicle.setBoolean(4, ((Sales) sale).isFinanced());
                        addSaleVehicle.setDouble(5, sale.getTotalPrice());
                        addSaleVehicle.setDouble(6, sale.getMonthlyPayment());
                        addSaleVehicle.setInt(7, vehicle.getVin());
                        addSaleVehicle.setInt(8, 1);
                        addSaleVehicle.executeUpdate();
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
                else
                {
                    System.out.println("Wrong VIN number or the vehicle is already sold");
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public List<Contract> getLeaseContracts() {
        return List.of();
    }

    @Override
    public List<Contract> getSaleContracts() {
        return List.of();
    }
}
