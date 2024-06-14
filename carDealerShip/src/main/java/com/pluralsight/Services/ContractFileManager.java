package com.pluralsight.Services;

import com.pluralsight.Model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ContractFileManager {
    public static void saveContract(DealerShip dealerShip)
    {
        // getting all the contract from the dealership
        ArrayList<Contract> contracts = dealerShip.getAllContracts();
        File file = new File("files/contracts.csv");

        // writing to file
        try(
                FileWriter fileWriter = new FileWriter(file,true);
                PrintWriter writer = new PrintWriter(fileWriter)
                )
        {
           for (Contract contract : contracts)
           {
               // getting the vehicle being sold
               Vehicle vehicle = contract.getVehicleSold();

               // checking if the contract is sale or lease
               if(contract instanceof Sales)
               {

                   // checking if the vehicle is financed
                   String isFinanced = ((Sales) contract).isFinanced() ? "Yes" : "No";
                   if(isFinanced.equalsIgnoreCase("yes")) {
                       writer.printf(" Sale | %s | %s | %s | %d | %d | %s | %s | %s | %s | %d | %.2f | %.2f | %.2f | %.2f | %.2f | %s | %.2f \n",
                               contract.getDate(), contract.getCustomerName(), contract.getCustomerEmail(), vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice(),
                               ((Sales) contract).getSALES_TAX(), ((Sales) contract).getRECORDING_FEE(), ((Sales) contract).getProcessingFee(), contract.getTotalPrice(),
                               isFinanced, contract.getMonthlyPayment());
                   }
                   else {
                       writer.printf(" Sale | %s | %s | %s | %d | %d | %s | %s | %s | %s | %d | %.2f | %.2f | %.2f | %.2f | %.2f | %s  \n",
                               contract.getDate(), contract.getCustomerName(), contract.getCustomerEmail(), vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice(),
                               ((Sales) contract).getSALES_TAX(), ((Sales) contract).getRECORDING_FEE(), ((Sales) contract).getProcessingFee(), contract.getTotalPrice(),
                               isFinanced);
                   }
               }
               else if(contract instanceof Lease)
               {
                   writer.printf(" LEASE | %s | %s | %s | %d | %d | %s | %s | %s | %s | %d | %.2f | %.2f | %.2f | %.2f | %.2f \n",
                           contract.getDate(), contract.getCustomerName(), contract.getCustomerEmail(), vehicle.getVin(), vehicle.getYear(), vehicle.getMake(), vehicle.getModel(), vehicle.getVehicleType(), vehicle.getColor(), vehicle.getOdometer(), vehicle.getPrice(),
                           ((Lease) contract).getExpectedEndingValue(), ((Lease) contract).getLEASE_FEE(), contract.getTotalPrice(), contract.getMonthlyPayment());
               }
           }
        }
        catch (IOException e)
        {
            System.out.println("File not found");
        }
        catch (Exception e)
        {
            System.out.println("Something went wrong");
        }
    }
}
