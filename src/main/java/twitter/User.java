package twitter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

/**
 * Created on 30.10.2015.
 */
public class User {
    private Long id;

    @NotNull
    @Size(min=5, max = 16, message = "{username.size}")
    private String username;

    @NotNull
    @Size(min=5, max = 25, message = "{password.size}")
    private String password;

    @NotNull
    @Size(min=2, max = 30, message = "{firstName.size}")
    private String firstName;

    @NotNull
    @Size(min=2, max = 30, message = "{lastName.size}")
    private String lastName;

    @NotNull
    @Email(message="Please provide a valid email address")
    private String email;
    //TODO Email Hibernate validator - how to get results? payload=BlahBlah.class ? how to catch errors with @Email

    public User() {}

    public User(String username, String password, String firstName, String lastName, String email){
        this(null,username,password,firstName,lastName,email);
    }

    public User(Long id, String username, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, new String[]{"firstName", "lastName", "username", "password", "email"});
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[]{"firstName", "lastName", "username", "password", "email"});
    }
}
