package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet, long customerId){
        // 1 - FIND THE CUSTOMER OF THE PET
        Customer customer = customerRepository.getOne(customerId);
        // 2 - ASSIGN THIS CUSTOMER TO THE PET
        pet.setCustomer(customer);
        // 3 - SAVE THE PET
        Pet savedPet = petRepository.save(pet);
        // 4 - ADD THE PET TO THE CUSTOMER LIST OF PETS
        List<Pet> pets = customer.getPets();
        pets.add(savedPet);
        //customer.addPet(pet);
        // 5 - SAVE THE CUSTOMER
        customerRepository.save(customer);
        return savedPet;
    }

    public Pet getPetById(long petId){
        return petRepository.getOne(petId);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByOwner(long customerId){
        return petRepository.findAllByCustomerId(customerId);
    }
}
