package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    public PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
    public Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        Pet savedPet = petService.savePet(pet, petDTO.getOwnerId());
        return convertPetToPetDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> allPets = petService.getAllPets();
        return allPets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> allPetsByOwner = petService.getAllPetsByOwner(ownerId);
        return  allPetsByOwner.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }
}
