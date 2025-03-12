package dev.proyect6.codehub.codehub.models;

import jakarta.persistence.*; 
import jakarta.validation.constraints.NotBlank;
import java.util.List;

    @Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Resource> resources;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Resource> getProducts() {
        return resources;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Resource> resources) {
        this.resources = resources;
    }

    

    
}

