package PictureService;

/**
 * Created by MJPS on 23/05/2017.
 */
public class Place {
	
	private String reference;
	private String name;
	private String formattedAddress;
	
	public Place() {
	}
	
	
	public String getFormattedAddress() {
		return formattedAddress;
	}
	
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Place{" +
				"reference='" + reference + '\'' +
				", formattedAddress='" + formattedAddress + '\'' +
				'}';
	}
}
