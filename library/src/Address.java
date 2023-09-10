public class Address {

    private String postcode;
    private String city;

    // Constructor that accepts the two parameters
    public Address(String postcode, String city) {
        this.postcode = postcode;
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
