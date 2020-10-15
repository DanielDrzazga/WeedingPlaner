package weddingplanner.application.models;


import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Create by Daniel Drzazga on 15.10.2020
 **/

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity<Long>{

    @Column(name = "role_name", length = 30)
    private String roleName;

    private String description;

    @Column(nullable = false)
    private boolean active;

    @ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();

}
