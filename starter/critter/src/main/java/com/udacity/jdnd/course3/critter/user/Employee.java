package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table(name = "Employee")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ElementCollection //BECAUSE 'EmployeeSkill' IS NOT AN ENTITY CLASS
    private Set<EmployeeSkill> skills;
    @ElementCollection //BECAUSE 'DayOfWeek' IS NOT AN ENTITY CLASS
    private Set<DayOfWeek> daysAvailable;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
