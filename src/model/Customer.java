package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    private final String emailRegEx = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegEx);

    public Customer(String firstName, String lastName, String email){
        if(! pattern.matcher(email).matches()){
            throw new IllegalArgumentException("__Error, Invalid email__");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email +
                '}';
    }
}
