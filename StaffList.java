import java.util.ArrayList;


public class StaffList
{
   private ArrayList<Staff> staff_list;
   
   public StaffList()
   {
	   staff_list=new ArrayList <Staff>(); //How to determine the length of this array?
       /*for (int i=0; i< staff_list.size(); i++)
		 { 
		     staff_list[i]= new Staff("a","m","b",i+1,0);
		 }*/
	   //---------------need to replace above code with reading of members from a file----------------------
}
	
   public boolean checkStaff(int staff_employeeID){ //to check membership by memberID
		for (int i=0; i<staff_list.size(); i++)
			if (staff_employeeID==staff_list.get(i).getEmployeeID()){
				return true;
			}
		return false;
	    }
	
	
	 public void createStaff(int staff_employeeID, String staff_name, char staff_gender, String staff_jobTitle, int staff_age){
	 int i=staff_employeeID-1;
	 Staff staff = new Staff();
	 staff.create(staff_name, staff_gender, staff_jobTitle, staff_age);
	     if (!checkStaff(staff_employeeID)){
	    	 staff_list.add(staff);
	         System.out.println("The StaffID "+staff_list.get(i).getEmployeeID()+ " is assigned to "+staff_list.get(i).getName());
	         }
	     else
	    	 System.out.println("This StaffID is already taken!");
	     }
	       
	 public void printStaff(int staff_employeeID){ //to print details of a member given membership ID
	 for (int i=0; i<staff_list.size(); i++)
	    if (staff_employeeID==staff_list.get(i).getEmployeeID()){
	        System.out.println ("Staff name"+staff_list.get(i).getName());
	        System.out.println("Staff gender"+staff_list.get(i).getGender());
	        System.out.println("Staff job title"+staff_list.get(i).getJobTitle());
	        System.out.println("Staff age"+staff_list.get(i).getAge());
	    }
	 }
	  
	 public void sortStaff(){
		Staff temporaryStaff; //used for array operations
		Staff [] staffCopy = new Staff[100];
		System.arraycopy(staff_list.toArray(), 0, staffCopy, 0, staff_list.size());
		for(int j=0;j<staff_list.size();j++)	
			for(int i=staffCopy.length-1;i>j;i--)
				if(staffCopy[i-1].getEmployeeID() >= staffCopy[i].getEmployeeID()){
					temporaryStaff = staffCopy[i];
					staffCopy[i] = staffCopy[i-1];
					staffCopy[i-1] = temporaryStaff;
				}
		System.out.println("The sorted list is as follows  : ");
		for(int k=0;k<staffCopy.length;k++){
			System.out.println("Staff ID "+staffCopy[k].getEmployeeID()+" is staff "+staffCopy[k].getName());
			}
		}
			 
	 public void printAllStaff(){
		 System.out.println ("The following is the list of staff and their employee IDs: ");
		 for (int i=0; i<staff_list.size(); i++)
	        System.out.println("Name: "+staff_list.get(i).getName()+" Member ID: "+staff_list.get(i).getEmployeeID());
	    }
}

