package com.pluralsight.Services.Daos;

import com.pluralsight.Model.Contract;
import com.pluralsight.Model.Sales;

import java.rmi.dgc.Lease;
import java.util.List;

public interface ContractDao {
    void addLeaseContract(Contract lease);

    void addSaleContract(Contract sales);

    List<Contract> getLeaseContracts();
    List<Contract> getSaleContracts();


}
