package com.example.animal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NookipediaVillagerDto {

    // 영문명 (유니크 키 + 이름)
    private String name;

    // 종족 (Bird, Cat, Dog ...)
    private String species;

    // 성별 (Male / Female)
    private String gender;

    // 데뷔작 (DNM, AC, NH ...)
    private String debut;

    @JsonProperty("birthday_month")
    private String birthdayMonth;

    @JsonProperty("birthday_day")
    private Integer birthdayDay;

    @JsonProperty("nh_details")
    private NhDetails nhDetails;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NhDetails {
        @JsonProperty("image_url")
        private String imageUrl;

        @JsonProperty("icon_url")
        private String iconUrl;
    }
}
