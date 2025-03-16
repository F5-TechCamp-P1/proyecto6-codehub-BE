package dev.proyect6.codehub.codehub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;


@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @NotBlank(message = "El título no puede estar vacío")
    private String title;

    @NotBlank(message = "La URL del archivo no puede estar vacía")
    @Column(name = "file_url")
    private String fileUrl;
    
    @Column(name = "upload_date")
    private LocalDate uploadDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    protected void onCreate() {
        this.uploadDate = LocalDate.now();
    }

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

    public void setCategory(Category category) {
        this.category = category;
    }
    
}