package com.pluralsight.controllers;

import com.pluralsight.Model.Contract;
import com.pluralsight.Model.Sales;
import com.pluralsight.Services.Daos.ContractDao;

import java.rmi.dgc.Lease;
import java.util.List;

public class ContractController {
    private ContractDao contractDao;

    public ContractController(ContractDao contractdao)
    {
        this.contractDao = contractdao;
    }

    public void leaseVehicle(Contract lease)
    {
        contractDao.addLeaseContract(lease);
    }

    public Contract sellVehicle(Contract sale)
    {
        return sale;
    }

}
