package bll;

import been.Flight;

import java.util.Set;

public interface IFlightService {
    void insertFlight(Flight flight);
    Set<Flight> getAllFlights();
    Flight getFlightByDepartureTime(String departureTime);//航班起飞时间
    Flight getFlightByDepartureAirPort(String departureAirPort);//航班起飞机场
    Flight getFlightByDestinationAirPort(String destinationAirPort);//航班目的机场
    void updateFlight(Flight flight);
}
