package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Pet")
public class Pet {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated
    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "customer_id") //a column for customer_id will be created in the pet table
    private Customer customer; //OWNER OF THE PET IS CUSTOMER OF THE CRITTER SERVICE

    public Pet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getPetType() {
        return type;
    }

    public void setPetType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDay() {
        return birthDate;
    }

    public void setBirthDay(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
