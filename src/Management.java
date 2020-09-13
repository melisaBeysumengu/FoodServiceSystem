import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Management {
	public UserFactory userFactory = new UserFactory();
	public ArrayList<Admin> aList = new ArrayList<Admin>();
	public ArrayList<Customer> cList = new ArrayList<Customer>();
	public ArrayList<Food> fList = new ArrayList<Food>();
	public ArrayList<Food> rList = new ArrayList<Food>();

	Management() throws QueueFull, IOException, QueueEmpty{
		//first elements of the aList and cList are filled with empty users. it is added for us to understand the working process of the code clearly
		aList.add(new Admin("", "", new Address("", "", "", ""), new Phone("", ""), ""));
		cList.add(new Customer("", "", new Address("", "", "", ""), new Phone("", ""), ""));
		//at the take data part the text files opened and the informations in it added into the system
		takeData();

	}
	
	//select file method helps the program to add new informations to the files.
	public void selectfile(String User,int i) throws IOException{
		if(i==1){
			File file =new File("Admin.txt");//1
			writeFile(User,file);
		}
		else if(i==2){    
			File cust =new File("Customers.txt");//2
			writeFile(User,cust);
		}
		else if(i==3){   
			File ord =new File("Restaurant.txt");//3
			writeFile(User,ord);
		}
		else if(i==4){   
			File ord =new File("Food.txt");//4
			writeFile(User,ord);
		}
		else if(i==5){   
			File ord =new File("Orders.txt");//5
			writeFile(User,ord);
		}
	}
	
	//writes the information to the desired file.
	public void writeFile(String text,File fileName) throws IOException {

		BufferedWriter writer = new BufferedWriter(
				new FileWriter(fileName, true)  //Set true for append mode
				);  
		writer.newLine();   //Add new line
		writer.write(text);
		writer.close();

	}


	//implemented for taking data from the text files. basically all files read starting with admin, customer, restaurant, food and order and the data inside them
	//added into the Admin Customer Restaurants.
	public void takeData() throws IOException,  QueueFull{

		File f =new File("Admin.txt");
		if(!f.exists()){
			f.createNewFile();
		}
		BufferedReader br = new BufferedReader(new FileReader(f)); 
		String st; 
		while ((st = br.readLine()) != null) {
			if(!st.equals("")){

				String sp[] =st.split(",");
				Admin a = (Admin)userFactory.getUser("admin", sp[1],sp[2],new Address(sp[3],sp[4],sp[5],sp[6]),new Phone(sp[7],sp[8]),sp[9]); 
				//Admin a=new Admin(sp[1],sp[2],new Address(sp[3],sp[4],sp[5],sp[6]),new Phone(sp[7],sp[8]),sp[9]);
				if(Integer.valueOf(sp[0])<aList.size()) {
					aList.remove(aList.get(Integer.valueOf(sp[0])));
					aList.add(Integer.valueOf(sp[0]), a);

				}
				else {
					a.createAdmin(a, aList);
				}
			}
		}
		br.close();
		f =new File("Customers.txt");
		if(!f.exists())
		{
			f.createNewFile();
		}
		BufferedReader br2 = new BufferedReader(new FileReader(f)); 
		String st1; 
		while ((st1 = br2.readLine()) != null) {

			if(!st1.equals("")){
				String sp[] =st1.split(",");
				Customer c = (Customer)userFactory.getUser("customer", sp[1],sp[2],new Address(sp[3],sp[4],sp[5],sp[6]),new Phone(sp[7],sp[8]),sp[9]); 
				//Customer c=new Customer(sp[1],sp[2],new Address(sp[3],sp[4],sp[5],sp[6]),new Phone(sp[7],sp[8]),sp[9]);
				if(Integer.valueOf(sp[0])<cList.size()) {
					cList.remove(cList.get(Integer.valueOf(sp[0])));
					cList.add(Integer.valueOf(sp[0]), c);

				}
				else {
					c.CreateCustomer(c, cList);
				}
			}
		}
		br2.close();
		f =new File("Restaurant.txt");
		if(!f.exists())
		{
			f.createNewFile();
		}
		BufferedReader br3 = new BufferedReader(new FileReader(f)); 
		String st3; 
		while ((st3 = br3.readLine()) != null) {
			if(!st3.equals("")){

				String sp[] =st3.split(",");
				aList.get(Integer.parseInt(sp[0])).setRestaurant(new Restaurant(sp[1], new Address(sp[2],sp[3],sp[4],sp[5]), new Phone(sp[6],sp[7])));
				aList.get(Integer.parseInt(sp[0])).getRestaurant().setShutDown(Boolean.valueOf(sp[8]));
			}
		}
		br3.close();
		f =new File("Food.txt");
		if(!f.exists()){
			f.createNewFile();
		}
		BufferedReader br4 = new BufferedReader(new FileReader(f)); 
		String st4; 
		while ((st4 = br4.readLine()) != null) {

			if(!st4.equals("")){
				String sp[] =st4.split("-");
				if(!Boolean.valueOf(sp[4])) {
					fList.add(new Food(sp[1], sp[2], Integer.valueOf(sp[3]), 
							((Restaurant)aList.get(Integer.valueOf(sp[0])).getRestaurant())));
					aList.get(Integer.parseInt(sp[0])).getRestaurant().getFood().add(new Food(sp[1], sp[2], Integer.valueOf(sp[3]), 
							((Restaurant)aList.get(Integer.valueOf(sp[0])).getRestaurant())));
				}
				else continue;
			}
		}
		br4.close();
		f =new File("Orders.txt");
		if(!f.exists()){
			f.createNewFile();
		}
		BufferedReader br5 = new BufferedReader(new FileReader(f)); 
		String st5; 
		while ((st5 = br5.readLine()) != null) {
			if(!st5.equals("")){
				String sp[] =st5.split(";");
				if (!Boolean.valueOf(sp[5])) {
					aList.get((int)Integer.valueOf((String)sp[0])).getRestaurant().getCustomerqueue().enqueue(cList.get((int)Integer.valueOf((String)sp[1])));
					cList.get(Integer.parseInt(sp[1])).setOrders(new Food(sp[2], sp[3], Integer.parseInt(sp[4]), aList.get(Integer.parseInt(sp[0])).getRestaurant()));
				}
			}
		}
		br5.close();
	}
	
	//fileUpdate method helps us to update the files we are using it only in the food and Order text files. it reads the files line by line and adds into a arraylist.
	//after that array list's elements controlled element by element and when we find the information we desired the program changes it and writes all the data
	//again too the text file.
	public void fileUpdate(String fName,String line) throws IOException {
		File inputFile = new File(fName);
		BufferedReader br4 = new BufferedReader(new FileReader(inputFile)); 
		ArrayList<String>n= new ArrayList<String>();
		String st4=""; 
		//System.out.println(line);
		while ((st4 = br4.readLine()) != null) {
			if(!st4.equals("")){
				n.add(st4);

			}
		}

		br4.close();
		line=line.replace("-[", ";").replace("]-",";");

		for (int i = 0; i < n.size(); i++) {

			if(n.get(i).substring(0, n.get(i).lastIndexOf(fName.equals("Orders.txt")?";":"-")).equals(line.substring(0, line.lastIndexOf(fName.equals("Orders.txt")?";":"-")))){

				n.remove(n.get(i));
				n.add(i, line);
			}
			else continue;
		}
		BufferedWriter br3 = new BufferedWriter(new FileWriter(inputFile)); 
		for (int i = 0; i < n.size(); i++) {
			br3.write(n.get(i));
			br3.newLine();
		}

		br3.close();


	}
	
	//if the customer is exists in the system returns true.
	public Boolean searchCustomer(String phone,String password) throws QueueFull, IOException, QueueEmpty {
		boolean enter = false;

		for (int i = 0; i < cList.size(); i++) {
			if(cList.get(i).getPhone().getNumber().equals(phone)&&cList.get(i).getPassword().equals(password)) {//user entered the system
				enter = true;

			}

		}
		return enter;
	}

	//returns the desired customer's id to the program.
	public int findcustomerid(String phone,String password) throws QueueFull, IOException, QueueEmpty {
		int customerid=0;
		for (int i = 0; i < cList.size(); i++) {
			if(cList.get(i).getPhone().getNumber().equals(phone)&&cList.get(i).getPassword().equals(password)) {//user entered the system
				customerid=i;
			}
		}
		return customerid;
	}
	//if the admin is exists in the system returns true.
	public Boolean searchAdmin(String phone,String password) throws QueueFull, IOException, QueueEmpty {
		boolean enter = false;
		for (int i = 0; i < aList.size(); i++) {
			if(aList.get(i).getPhone().getNumber().equals(phone)&&aList.get(i).getPassword().equals(password)) {//user entered the system
				enter = true;
			}
		}
		return enter;
	}
	//returns the desired admin's id to the program.
	public int findAdminid(String phone,String password) throws QueueFull, IOException, QueueEmpty {
		int adminId = 0 ;
		for (int i = 0; i < aList.size(); i++) {
			if(aList.get(i).getPhone().getNumber().equals(phone)&&aList.get(i).getPassword().equals(password)) {//user entered the system
				adminId=i;
			}
		}
		return adminId;
	}




















}

