package com.example.furniture_factory.model.other;

import com.example.furniture_factory.model.WorkOrder.ProductWorkOrder;
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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int count;
    @Lob
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private byte[] profilePicture;

    @ManyToMany
    @JoinTable(
            name = "product_material",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id"))
    private Set<Material> material;
    @ManyToMany
    @JoinTable(
            name = "product_work",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "work_id"))
    private Set<Work> works;

   @OneToMany(mappedBy="product")
    private Set<ProductWorkOrder> productWorkOrders;

    public void setProfilePicture(MultipartFile file) throws IOException {
            profilePicture=file.getBytes();
    }
    public String getProfilePicture() {
        return Base64.getMimeEncoder().encodeToString(profilePicture);
    }

}
