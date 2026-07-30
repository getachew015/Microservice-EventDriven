package com.app.accounts.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CUSTOMERSTBL")
public class CustomersEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(
            name = "customer_seq",
            sequenceName = "customer_sequence",
            initialValue = 1001, // Starts the ID counter at 1000
            allocationSize = 1   // Prevents gaps in the ID sequence
    )
    private Long customerId;// VARCHAR(20) BY DEFAULT AS IDENTITY,
    @Column(nullable = false)
    private String firstName;//VARCHAR(50) NOT NULL,
    @Column(nullable = false)
    private String lastName;// VARCHAR(50) NOT NULL,
    @Column(nullable = false)
    private String email; //VARCHAR(100) NOT NULL,
    @Column
    private String phoneNumber;// VARCHAR(20),
    @Column(name = "STREET_ADDRESS")
    private String streetAddress;// VARCHAR(20),
    @Column
    private String city;// VARCHAR(20),
    @Column
    private String state;// VARCHAR(20),
    @Column(name = "ZIP_CODE")
    private String zipCode;// VARCHAR(20),
    @JsonProperty()
    @Column
    private String status = "ACTIVE";// VARCHAR(15) DEFAULT 'ACTIVE',
    // Configures the one-to-many relationship
    @OneToMany(mappedBy = "customerId",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountsEntity> accounts;

}
