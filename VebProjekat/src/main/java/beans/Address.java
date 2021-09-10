package beans;

public class Address {
	
	private String addressName;
	private String city;
	private String postalCode;
	
	public Address() {
		
	}

	public Address(String addressName, String city, String postalCode) {
		super();
		this.addressName = addressName;
		this.city = city;
		this.postalCode = postalCode;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
