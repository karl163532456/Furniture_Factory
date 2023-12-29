package com.example.furniture_factory.model.other;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Setter
@Getter
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int count;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    private byte[] profilePicture;
    @ManyToMany(mappedBy = "material" )
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<Product> products;

    public String getProfilePicture() {
            return Base64.getMimeEncoder().encodeToString(profilePicture);
    }

    public void setProfilePicture(MultipartFile file) throws IOException {
        profilePicture=file.getBytes();
    }

}