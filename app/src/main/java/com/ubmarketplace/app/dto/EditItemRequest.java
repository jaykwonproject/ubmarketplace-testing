package com.ubmarketplace.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class EditItemRequest {
    private String userId;
    private EditItemRequestItem item;

    @Data
    @Builder
    public static class EditItemRequestItem {

        @NotNull(message = "Item id cannot be empty")
        @Pattern(regexp = "(^[0-9A-Za-z]{8}$)",
                 message = "Format incorrect: itemId format incorrect")
        private String itemId;

        @NotNull(message = "Item name cannot be empty")
        private String name;

        @NotNull(message = "Category cannot be empty")
        private String category;

        @NotNull(message = "Description cannot be empty")
        private String description;

        @NotNull(message = "Price cannot be empty")
        @Min(value = 0, message = "Price must be greater than 0")
        @Max(value = 10000, message = "Price must be less than 10000")
        private Double price;

        @NotNull(message = "Please attach at least one image")
        @Size(min = 1)
        private List<String> images;

        @NotNull(message = "Meeting place cannot be empty")
        private String meetingPlace;

        @Pattern(regexp = "(^\\d{10}$|^$)",
                message = "Format incorrect: Contact phone number should be 10 digits number or empty")
        private String contactPhoneNumber;
    }
}
