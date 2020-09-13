
public abstract class User implements UserInterface{
	private String  name;
	private String surname;
	private Address address; 
	private Phone phone;
	private String password;
	public User(String name, String surname, Address address, Phone phone, String password) {
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.phone = phone;
		this.password =password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", address=" + address + ", phone=" + phone;
	}
	
	public void setUser(String name, String surname, Address address, Phone phone, String password) {
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.phone = phone;
		this.password =password;
	}
	
	
	
	
}
