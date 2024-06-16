package com.pluralsight;

import com.pluralsight.Services.Daos.ContractDao;
import com.pluralsight.Services.Daos.VehiclesDao;
import com.pluralsight.Services.MySqlContractDao;
import com.pluralsight.Services.MySqlVehiclesDao;
import com.pluralsight.Views.UserInterface;
import com.pluralsight.controllers.ContractController;
import com.pluralsight.controllers.VehiclesController;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args)
    {
        DataSource dataSource = configDataSource();
        VehiclesDao vehiclesDao = new MySqlVehiclesDao(dataSource);
        ContractDao contractDao = new MySqlContractDao(dataSource);
        VehiclesController vehiclesController = new VehiclesController(vehiclesDao);
        ContractController contractController = new ContractController(contractDao);
        UserInterface app = new UserInterface(vehiclesController, contractController);
        app.run();
    }

    public static DataSource configDataSource()
    {
        try(FileInputStream stream = new FileInputStream("src/main/resources/config.properties"))
        {
            Properties properties = new Properties();
            properties.load(stream);

            // getting data source properties from config file
            String connectionString = properties.getProperty("db.connectionString");
            String userName = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // setting data source properties
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(connectionString);
            basicDataSource.setUsername(userName);
            basicDataSource.setPassword(password);

            return basicDataSource;
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
}