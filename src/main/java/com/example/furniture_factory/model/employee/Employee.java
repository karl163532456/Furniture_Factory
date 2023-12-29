package com.example.furniture_factory.model.employee;

import com.example.furniture_factory.model.WorkOrder.WorkEmployee;
import com.example.furniture_factory.model.other.Brigade;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    private String name;

    private String surname;

    private String patronymic;

    @Column(name = "date_of_birth")

    private  Date dateOfBirth;

    @Column(name = "job_title")
    private String jobTitle;

    private String login;

    private String password;


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private byte[] profilePicture;

    //@OneToOne(mappedBy = "chief")
    @ManyToOne
    @JoinColumn(name="brigade_id")
    private Brigade brigade;

    @OneToMany(mappedBy="employee")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<WorkEmployee> workEmployees;

    @ManyToMany
    @JoinTable(
            name = "employees_schedule",
            joinColumns = @JoinColumn(name = "employees_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private Set<Schedule> schedules;

    public void setProfilePicture(MultipartFile file) throws IOException {
        profilePicture=file.getBytes();
    }
    public String getProfilePicture() {
        return Base64.getMimeEncoder().encodeToString(profilePicture);
    }

}

