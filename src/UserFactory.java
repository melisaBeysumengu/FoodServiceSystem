
public class UserFactory {
	
	public static User getUser(String type, String name, String surname, Address address, Phone phone, String password) {
		if(type.equals("admin")) {
			return new Admin(name, surname, address, phone, password);
		}
		else if(type.equals("customer")) {
			return new Customer(name, surname, address, phone, password);
		}
		return null;
	}

}
