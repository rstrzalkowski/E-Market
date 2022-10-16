package pl.marketapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.marketapi.domain.dto.request.RegisterRequest;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "user_seq")
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Column
    private boolean enabled;
    @Column
    private String role;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @Column
    @UpdateTimestamp
    private Timestamp updatedAt;

    public User(RegisterRequest registerRequest) {
        this.email = registerRequest.getEmail().toLowerCase();
        this.password = registerRequest.getPassword();
        this.firstName = registerRequest.getFirstName();
        this.lastName = registerRequest.getLastName();
        this.enabled = true;
        this.role = "USER";
    }
}
