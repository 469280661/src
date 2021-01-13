package dao.impl;

import been.Flight;
import dao.IFlightDao;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FlightDaolml implements IFlightDao {
    @Override
    public void insertFlight(Flight flight) throws SQLException {
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username="";
        String password="";
        Connection/*连接*/ conn= DriverManager.getConnection(url,username,password);
        String sql="INSERT INTO flight VALUES(?,?,?,?,?,?,?)";

        PreparedStatement/*编制报表*/ pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,flight.getId());
        pstmt.setString(2, flight.getFlightId());
        pstmt.setString(3, flight.getPlaneType());
        pstmt.setInt(4, flight.getCurrentSeatsNum());
        pstmt.setString(5, flight.getDepartureAirPort());
        pstmt.setString(6, flight.getDestinationAirPort());
        pstmt.setString(7, flight.getDepartureTime());

        pstmt.executeUpdate();//用于执行数据库语句的方法，返回值是一个整数，指示受影响的行数（即更新计数）

    }

    @Override
    public Set<Flight> getAllFlights() throws SQLException {
       Set<Flight>allFlights=new HashSet<Flight>();//容器//HashSet类，是存在于java.util包中的类 。同时也被称为集合，该容器中只能存储不重复的对象。
       // HashSet是set接口的实现类， 储存的是无序，唯一的对象。 HashSet的元素不能重复,HashSet中允许有NULL值
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String username="";
        String password="";
        Connection conn=DriverManager.getConnection(url,username,password);
        String sql="SELECT * FROM flight";
        PreparedStatement/*编制报表*/ pstmt=conn.prepareStatement(sql);
        ResultSet/*结果集*/ rs=pstmt.executeQuery();//执行查询
        while(rs.next()){
            String id = rs.getString("ID");
            String flightId = rs.getString("FLIGHT_ID");
            String planeType = rs.getString("PLANE_TYPE");
            int currentSeatsNum = rs.getInt("TOTAL_SEATS_NUM");
            String departureAirPort = rs.getString("DEPARTURE_AIRPORT");
            String destinationAirPort = rs.getString("DESTINATION_AIRPORT");
            String departureTime = rs.getString("DEPARTURE_TIME");

            Flight flight = new Flight(id, flightId, planeType, currentSeatsNum,
                    departureAirPort, destinationAirPort, departureTime);
            allFlights.add(flight);
        }
        return allFlights;
    }

    @Override
    public Flight getFlightByDepartureTime(String departureTime) {
        return null;
    }

    @Override
    public Flight getFlightByDepartureAirPort(String departureAirPort) {
        return null;
    }

    @Override
    public Flight getFlightByDestinationAirPort(String destinationAirPort) {
        return null;
    }

    @Override
    public void updateFlight(Flight flight) {

    }
}
