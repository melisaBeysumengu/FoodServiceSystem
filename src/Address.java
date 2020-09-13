
public class Address {
	private String StreetName;
	private String town; 
	private String city;
	private String description;
	
	public String getStreetName() {
		return StreetName;
	}
	public void setStreetName(String streetName) {
		StreetName = streetName;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Address(String streetName, String town, String city, String description) {
		StreetName = streetName;
		this.town = town;
		this.city = city;
		this.description = description;
	}
	@Override
	public String toString() {
		return StreetName + "," + town + "," + city + ","+ description ;
	}
	
	
}
