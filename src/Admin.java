import java.util.ArrayList;

public class Admin extends User{
	private int  ID_admin;
	private static int count = 1 ;
	private Restaurant restaurant;

	public Restaurant getRestaurant() {
		return restaurant;
	}
	//setRestaurant method is useful when the admin changes its address phone etc.
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant.setAddress(restaurant.getAddress());
		this.restaurant.setPhone(restaurant.getPhone());
		this.restaurant.setRestaurant_name(restaurant.getRestaurant_name());
		this.restaurant.setShutDown(restaurant.isShutDown());
	}
	public Admin(String name, String surname, Address address, Phone phone, String password) {
		super(name, surname, address, phone,password);
		setID_admin(count);
		this.restaurant = new Restaurant("Restaurant Name Here",new Address("","","",""),new Phone("",""));
		//when first time we create an admin type of user we have to add an Restaurant object associated with it.
		//so that when admin enters the system it is easy for us to connect its restaurant.

	}
	public int getID_admin() {
		return ID_admin;
	}
	public void setID_admin(int iD_admin) {
		ID_admin = iD_admin;
	}
	public int getCount() {
		return count;
	}
	@SuppressWarnings("static-access")
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "getName()=" + getName() + ", getSurname()=" + getSurname()
		+ ", getAddress()=" + getAddress().toString() + ", getPhone()=" + getPhone().toString() + ", password= "+ getPassword();
	}
	//create admin method helps us add the admin into the array list.
	public void createAdmin(Admin ad, ArrayList<Admin> a) {
			a.add(ad);
			count++;
	}



}
