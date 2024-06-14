package com.pluralsight.Services;

import com.pluralsight.Model.Contract;
import com.pluralsight.Model.Sales;
import com.pluralsight.Services.Daos.ContractDao;

import javax.sql.DataSource;
import java.rmi.dgc.Lease;
import java.util.List;

public class MySqlContractDao implements ContractDao {

    DataSource dataSource;

    public MySqlContractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Lease addLeaseContract() {
        return null;
    }

    @Override
    public Sales addSaleContract() {
        return null;
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
