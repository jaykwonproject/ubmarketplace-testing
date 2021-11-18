package com.ubmarketplace.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;

import static com.ubmarketplace.app.Utils.getCurrentEpochMilli;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Image {
    @Id @Builder.Default
    private String imageId = "IMG" + RandomStringUtils.randomAlphanumeric(8).toLowerCase();
    private String thumb; //URL to the thumb size image
    private String medium; //URL to the medium size image
    private String large; //URL to the original size image

    @JsonIgnore
    private String uploadBy; // UserId
    @EqualsAndHashCode.Exclude @Builder.Default @JsonIgnore
    private Long uploadTime = getCurrentEpochMilli();
}
