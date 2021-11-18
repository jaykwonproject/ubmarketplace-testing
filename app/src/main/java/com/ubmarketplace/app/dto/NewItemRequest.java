package com.ubmarketplace.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class NewItemRequest {
    @NotNull(message = "Item name cannot be empty")
    private String name;

    @NotNull(message = "userId cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_]+@(buffalo.edu|test.com)$", message = "Format incorrect: userId should be a buffalo.edu email")
    private String userId;

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

    // contactPhoneNumber should be 10 digits number in string or empty string
    @Pattern(regexp = "(^\\d{10}$|^$)",
            message = "Format incorrect: Contact phone number should be 10 digits number or empty")
    private String contactPhoneNumber;
}