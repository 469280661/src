import been.Flight;
import bll.IFlightService;
import bll.impl.FlightServicelmpl;
/*import cn.edu.hcnu.bean.Flight;
import cn.edu.hcnu.bll.IFlightService;
import cn.edu.hcnu.bll.impl.FlightServiceImpl;*/

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainUI {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);//接受键盘输入
        while (true) {
            System.out.println("请输入相应的数字进行操作：");

            System.out.println("按1，录入航班信息");
            System.out.println("按2，显示所有航班信息");
            System.out.println("按3，查询航班信息");
            System.out.println("按4，机票预订");
            System.out.println("按5，机票退订");
            System.out.println("按6，推出系统");

            int choice = sc.nextInt();

            if (choice == 1) {
                String id = UUID.randomUUID().toString().replace("-", "");

                System.out.print("请输入航班编号：");
                String flightId = sc.next();
                System.out.print("请输入机型：");
                String planeType = sc.next();
                System.out.print("请输入座位数：");
                int currentSeatsNum = sc.nextInt();
                System.out.print("请输入起飞机场：");
                String departureAirPort = sc.next();
                System.out.print("请输入目的机场：");
                String destinationAirPort = sc.next();
                System.out.print("请输入起飞时间：");
                String departureTime = sc.next();

                Flight flight = new Flight(id, flightId, planeType, currentSeatsNum,
                        departureAirPort, destinationAirPort, departureTime);

                IFlightService iFlightService = new FlightServicelmpl();
                iFlightService.insertFlight(flight);
            } else if (choice == 2) {
                IFlightService iFlightService = new FlightServicelmpl();
                Set<Flight> allFlights=iFlightService.getAllFlights();
                    /*
                    Set的遍历需要用到迭代器
                     */
                for(Flight flight:allFlights){
                    System.out.println(flight);
                }
            }else if (choice==3){
                System.out.println("请输入相应的编号选择您要查询航班的方式");
                System.out.println("1，按起飞时间查询");
                System.out.println("2，按空座信息查询");
                System.out.println("3，按起飞城市查询");
                System.out.println("4，按目的城市查询");
                int choose = sc.nextInt();
                if(choose==1){
                    System.out.println("请输入起飞时间");
                    String departureTime=sc.next();
                    IFlightService iFlightService=new FlightServicelmpl();
                    Flight flight=iFlightService.getFlightByDepartureTime(departureTime);
                     System.out.println("查询结果:"+flight);
                }else if(choose==3){
                    System.out.println("请输入起飞城市");
                    String departureAirPort=sc.next();
                    IFlightService iFlightService=new FlightServicelmpl();
                    Flight flight=iFlightService.getFlightByDepartureAirPort(departureAirPort);
                    System.out.println("查询结果:"+flight);
                }else if(choose==4){
                    System.out.println("请输入目的城市");
                    String destinationAirPort=sc.next();
                    IFlightService iFlightService=new FlightServicelmpl();
                    Flight flight=iFlightService.getFlightByDestinationAirPort(destinationAirPort);
                    System.out.println("查询结果:"+flight);
                }
                }
            }
        }}
