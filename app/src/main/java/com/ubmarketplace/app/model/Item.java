package com.ubmarketplace.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;

import java.util.List;

import static com.ubmarketplace.app.Utils.getCurrentEpochMilli;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Item {
    @Id @Builder.Default
    private String itemId = RandomStringUtils.randomAlphanumeric(8).toLowerCase();
    private String name;
    private String userId;
    private String category;
    private String description;
    private Double price;
    private List<String> images;
    private String meetingPlace;

    private String contactPhoneNumber;

    @EqualsAndHashCode.Exclude @Builder.Default
    private Long createdTime = getCurrentEpochMilli();
}
