package com.example.animal.dto;

public class VillagerImageDto {
    private String name;
    private String imageUrl; // 대표 이미지
    private String iconUrl;  // 얼굴 아이콘 ⭐⭐⭐

    public VillagerImageDto() {}

    public VillagerImageDto(String name, String imageUrl, String iconUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
