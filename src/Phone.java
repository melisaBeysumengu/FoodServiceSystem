
public class Phone {
	private String country_code;
	private String number;
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Phone(String country_code, String number) {
		this.country_code = country_code;
		this.number = number;
	}
	@Override
	public String toString() {
		return country_code + "," + number;
	}
	
	
}
