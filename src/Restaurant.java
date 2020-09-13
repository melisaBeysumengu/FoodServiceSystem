import java.util.ArrayList;
public class Restaurant {
	private String  restaurant_name;
	private String ship_min;
	private Address address ;
	private Phone phone;
	private ArrayList<Food> food = new ArrayList<Food>(); 
	private BoundedQueue customerQueue = new BoundedQueue();
	private boolean shutDown = false;//starts with false but admin can change it when s/he wanted.
	public Restaurant(String restaurant_name, Address address, Phone phone) {
		this.restaurant_name = restaurant_name;
		this.address = address;
		this.phone = phone;
	}
	public String getRestaurant_name() {
		return restaurant_name;
	}
	public void setRestaurant_name(String restaurant_name) {
		this.restaurant_name = restaurant_name;
	}
	public String getShip_min() {
		return ship_min;
	}
	public void setShip_min(String ship_min) {
		this.ship_min = ship_min;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	public ArrayList<Food> getFood() {
		return food;
	}
	public void setFood(ArrayList<Food> food) {
		this.food = food;
	}
	public BoundedQueue getCustomerqueue() {
		return customerQueue;
	}
	public void setCustomerqueue(Customer customer) throws QueueFull {
		this.customerQueue.enqueue(customer);
	}
	public void addFood(Food f) {
		food.add(f);
	}
	public void removeFood(Food f) {
		for (int i = 0; i < food.size(); i++) {
			if(food.get(i).getFood_name().equals(f.getFood_name())) {
				food.remove(i);
				return;
			}
		}
	}
	public void displayQueue() {
		System.out.println(customerQueue.toString());
	}
	@Override
	public String toString() {
		return "restaurant_name=" + restaurant_name + ", address=" + address.toString()
				+ ", phone=" + phone.toString();
	}
	public void setRestaurant(String restaurant_name, Address address, Phone phone) {
		this.restaurant_name = restaurant_name;
		this.address = address;
		this.phone = phone;
	}
	public boolean isShutDown() {
		return shutDown;
	}
	public void setShutDown(boolean shutDown) {
		this.shutDown = shutDown;
	}
	//finds the desired food from the restaurant
	public Food FindFood(Restaurant r ,String s){ 
		
		for (int i = 0; i < r.getFood().size(); i++) {
			if(s.equals(r.getFood().get(i).getFood_name())){
				return r.getFood().get(i);
			}
			
		}
		return null;
	}
	
	
	
}
