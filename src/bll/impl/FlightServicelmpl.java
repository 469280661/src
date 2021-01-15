package bll.impl;

import been.Flight;
import bll.IFlightService;
import dao.IFlightDao;
import dao.impl.FlightDaolml;

import java.sql.SQLException;
import java.util.Set;

public class FlightServicelmpl implements IFlightService {
    IFlightDao iFlightDao;
    public FlightServicelmpl(){
        iFlightDao=new FlightDaolml();
    }
    @Override
    public void insertFlight(Flight flight) throws SQLException {
        iFlightDao.insertFlight(flight);
    }

    @Override
    public Set<Flight> getAllFlights() throws SQLException {
        return iFlightDao.getAllFlights();
    }

    @Override
    public Flight getFlightByDepartureTime(String departureTime) throws SQLException {
        return iFlightDao.getFlightByDepartureTime(departureTime);
    }

    @Override
    public Flight getFlightByDepartureAirPort(String departureAirPort) throws SQLException {
        return iFlightDao.getFlightByDepartureAirPort(departureAirPort);
    }

    @Override
    public Flight getFlightByDestinationAirPort(String destinationAirPort) throws SQLException {
        return iFlightDao.getFlightByDestinationAirPort(destinationAirPort);
    }

    @Override
    public void updateFlight(Flight flight) {

    }
}
