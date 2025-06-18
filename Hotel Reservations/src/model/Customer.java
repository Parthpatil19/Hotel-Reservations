package model;
import java.util.regex.*;
public class Customer {
    final String firstName;
    final String lastName;
    final String email;


    public Customer(String firstName,String lastName, String email) {
        this.firstName=firstName;
        this.lastName=lastName;
        if(this.isValid(email)) {
            this.email=email;
        }
        else {
            throw new IllegalArgumentException("oops! The email seems to be in wrong format. Please enter correct email format.");
        }
    }

    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public final String toString() {
        return "Name: "+firstName+" "+lastName+"  Email: "+email;
    }

    private boolean isValid(String email) {
        final String emailPattern="^(.+)@(.+)[.](.+)$";
        Pattern pattern=Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();

    }

}
