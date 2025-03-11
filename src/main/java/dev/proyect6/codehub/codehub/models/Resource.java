package dev.proyect6.codehub.codehub.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileUrl;
    private LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFileUrl() {
        return fileUrl;
    }
    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}