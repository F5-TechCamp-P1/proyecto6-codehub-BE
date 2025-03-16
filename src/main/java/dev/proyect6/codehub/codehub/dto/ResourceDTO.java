package dev.proyect6.codehub.codehub.dto;

public class ResourceDTO {
    private String title;
    private String fileUrl;
    private Long categoryId;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFileUrl() {
        return fileUrl;
    }
    public void setUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
