import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ReservationList {
	private ArrayList <Reservation> reservation;
	private TableList tableList; //needed to occupy table on creating and vacate table on cancellation/invalid reservation
	   BufferedWriter out;
	   BufferedReader in;
	
	//constructor
	//also reads the reservation txt file to retrieve older reservations
	public ReservationList(TableList tableList){
		reservation = new ArrayList<Reservation>();
		this.tableList = tableList;
		
		
		//read from file
		
		try {
			File file = new File("reservationlist.txt");
	        BufferedReader br = new BufferedReader(new FileReader(file));
	        List<String> lines = new ArrayList<String>();
	        String line = br.readLine();
	        String tempName;
			int tempPhoneNumber;
			int tempNoOfPeople;
			int tempTableID;
			int year, month, date, hour, minute;
			Calendar bookingTime = Calendar.getInstance();
	        while(line != null) {
	            lines.add(line.replace(">", ""));
	            line = br.readLine();
	        }
	        int i=0;
	        
	while (i<lines.size()){
	tempName = lines.get(i);
	tempPhoneNumber = Integer.parseInt(lines.get(i+1));
	tempNoOfPeople= Integer.parseInt(lines.get(i+2));
	tempTableID = Integer.parseInt(lines.get(i+3));
	
	year = Integer.parseInt(lines.get(i+4));
	month = Integer.parseInt(lines.get(i+5));
	date = Integer.parseInt(lines.get(i+6));
	hour = Integer.parseInt(lines.get(i+7));
	minute = Integer.parseInt(lines.get(i+8));
	
	bookingTime.set(Calendar.YEAR, year);
	bookingTime.set(Calendar.MONTH, month-1);
	bookingTime.set(Calendar.DAY_OF_MONTH, date);
	bookingTime.set(Calendar.HOUR_OF_DAY, hour);
	bookingTime.set(Calendar.MINUTE, minute);
	Reservation tempReservation = new Reservation(tempName, tempPhoneNumber, tempNoOfPeople, tempTableID, bookingTime.getTime());
	reservation.add(tempReservation);
	tableList.occupyTable(tempReservation.getTableId());
	i=i+10;
	}

	        br.close();
	        }
	        
	         		  catch(IOException e){
	             System.out.println("There was a problem:" + e);
	         }
	 			catch (NumberFormatException e) {
	           //System.out.println("This is not a number");
	       }
		
		
	}
	
	//function to return ArrayList index of given reservation name
	public int getIndexByName(String name){
		for(int i=0;i<reservation.size();i++)
			if(reservation.get(i).getName().equalsIgnoreCase(name))
				return i;
		return -1;
	}
	
	//function to create a reservation
	public void createReservation(){
		Scanner sc = new Scanner(System.in);
		String name;
		int phoneNumber;
		int noOfPeople;
		//how to handle date?
		int year, month, date, hour, minute;
		Calendar bookingTime = Calendar.getInstance();
		System.out.print("Input the following details\nName : ");
		name = sc.nextLine();
		System.out.print("Phone Number : ");
		phoneNumber = sc.nextInt();
		do{
		System.out.print("Reservation for how many people : ");
		noOfPeople = sc.nextInt();
		if(noOfPeople>10)
			System.out.println("Sorry. We can seat a maximum of 10 people");
		}while(noOfPeople>10);
		System.out.print("Year of reservation : ");
		year  = sc.nextInt();
		System.out.print("Month of reservation : ");
		month  = sc.nextInt();
		System.out.print("Day of reservation : ");
		date  = sc.nextInt();
		System.out.print("Hour of reservation : ");
		hour  = sc.nextInt();
		System.out.print("Minute of reservation : ");
		minute  = sc.nextInt();
		bookingTime.set(year, month-1, date, hour, minute);
		int tableID = tableList.getBestFit(noOfPeople);
		Reservation tempReservation = new Reservation(name, phoneNumber, noOfPeople, tableID, bookingTime.getTime());
		reservation.add(tempReservation);
		tableList.occupyTable(tempReservation.getTableId());
		System.out.println("\nTable "+ tempReservation.getTableId() + " is now occupied");
		
		reservationListOverwrite();
	}
	
	//function to check reservation with given name. If reservation is invalid, it is removed
	public void checkPrintReservation(String name){
		int index = getIndexByName(name);
		Date currentDate = new Date();
		
		if(index==-1)
			System.out.println("No reservation by the name "+name+" was found");
		else{
			reservation.get(index).print();
			Date endDate = reservation.get(index).getEndDate();
			
			if(currentDate.after(endDate)){
				System.out.println("Reservation is now invalid.\nRemoving reservation.");
				removeReservation(name);
			}
			
			
			else
					System.out.print("Reservation is still active");
				
		}
		
	}
	
	//function to remove reservation with given reservation name
	public void removeReservation(String name){
		int index = getIndexByName(name);
		Date currentDate = new Date();
		if(index==-1)
			System.out.println("No reservation by the name "+name+" was found");
		else{
			if(currentDate.after(reservation.get(index).getDate()) && currentDate.before(reservation.get(index).getEndDate()))
				tableList.vacateTable(reservation.get(index).getTableId());
			System.out.println("Reservation removed and table "+ reservation.get(index).getTableId() +" vacated");
			reservation.remove(index);
			reservationListOverwrite();

		}
	}
	
	//function to write reservation to the txt file
	public void reservationListOverwrite(){
        try{
        Calendar date = Calendar.getInstance();
        int year, month, day, hour, minute;
         out = new BufferedWriter(new FileWriter("reservationlist.txt",false)); 
         for(int counter=0;counter<reservation.size();counter++){
        	 	date.setTime(reservation.get(counter).getDate());
        	 	year = date.get(Calendar.YEAR);
        	 	month = date.get(Calendar.MONTH);
        	 	day = date.get(Calendar.DAY_OF_MONTH);
        	 	hour = date.get(Calendar.HOUR);
        	 	minute = date.get(Calendar.MINUTE);
        	 	out.write(reservation.get(counter).getName()+"\n"+(reservation.get(counter).getPhoneNumber())+"\n"+	String.valueOf(reservation.get(counter).getNoOfPeople())+"\n"+
    		   	String.valueOf(reservation.get(counter).getTableId())+
        "\n"+String.valueOf(year)+"\n"+String.valueOf(month)+"\n"+String.valueOf(day)+"\n"+String.valueOf(hour)+"\n"+String.valueOf(minute));
        	out.newLine();
			out.newLine();
         }
         out.close();
         }
         catch(IOException e){
         System.out.println("There was a problem:" + e);
         }

}
	
	public void printReservation(){
		for (int i=0; i<reservation.size(); i++){
		 	 System.out.println("Reservation number: "+(i+1));
		 	 reservation.get(i).print();
		 	 System.out.println("\n");
			}
		  }
		
	}
	
	
	