package bll;

import been.Flight;

import java.sql.SQLException;
import java.util.Set;

public interface IFlightService {
    void insertFlight(Flight flight) throws SQLException;
    Set<Flight> getAllFlights() throws SQLException;
    Flight getFlightByDepartureTime(String departureTime) throws SQLException;//航班起飞时间
    Flight getFlightByDepartureAirPort(String departureAirPort) throws SQLException;//航班起飞机场
    Flight getFlightByDestinationAirPort(String destinationAirPort) throws SQLException;//航班目的机场
    void updateFlight(Flight flight);
}
