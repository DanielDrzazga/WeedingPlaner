package weddingplanner.application.models;

import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Create by Daniel Drzazga on 15.10.2020
 **/

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long>{

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean active;
    private String token;
    private Date tokenTime;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn (name = "user_id")}, inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public void generateToken(){
        setToken(UUID.randomUUID().toString());
        setTokenTime(new Date());
    }

}

