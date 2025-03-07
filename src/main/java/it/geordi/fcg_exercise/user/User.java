package it.geordi.fcg_exercise.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "USERS")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    @NotEmpty
    @Size(max = 100)
    private String name;

    @Column(name = "SURNAME")
    @NotEmpty
    @Size(max = 100)
    private String surname;

    @Column(name = "EMAIL")
    @NotEmpty
    @Size(max = 100)
    private String email;

    @Column(name = "ADDRESS")
    @Size(max = 255)
    private String address;
}
