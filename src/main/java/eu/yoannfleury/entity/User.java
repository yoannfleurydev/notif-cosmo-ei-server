package eu.yoannfleury.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity User
 */
@Entity
public class User {
    /**
     * The index of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The identifier of the user used for connection.
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String userName;

    /**
     * The first name of the user.
     */
    @NotNull
    @Column(nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     */
    @NotNull
    @Column(nullable = false)
    private String lastName;

    /**
     * The email of the user.
     */
    @NotNull
    @Column(nullable = false)
    private String email;

    /**
     * The {@link Role} of the user.
     */
    @NotNull
    @Column(nullable = false)
    private Role role;

    /**
     * The password of the user.
     */
    @Size(min = 5)
    @JsonIgnore
    private String password;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * @param id The index of the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param role The {@link Role} of the user.
     */
    public User(long id, String userName, String firstName, String lastName,
                String email, Role role) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
