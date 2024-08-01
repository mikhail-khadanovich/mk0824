package com.khadanovich.toolrental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalAgreement {
    private String toolCode;
    private ToolType toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private double discountAmount;
    private double finalCharge;

    public void printRentalAgreement() {
        NumberFormat usCurrencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("Tool Code: " + toolCode + '\n' +
                "Tool Type: '" + toolType + '\n' +
                "Tool Brand: '" + toolBrand + '\n' +
                "Rental Days: " + rentalDays + '\n' +
                "Checkout Date: " + checkoutDate.format(formatter) + '\n' +
                "Due Date: " + dueDate.format(formatter) + '\n' +
                "Daily Rental Charge: " + usCurrencyFormatter.format(dailyRentalCharge) + '\n' +
                "Charge Days: " + chargeDays + '\n' +
                "PreDiscount Charge: " + usCurrencyFormatter.format(preDiscountCharge) + '\n' +
                "Discount Amount: " + usCurrencyFormatter.format(discountAmount) + '\n' +
                "Final Charge: " + usCurrencyFormatter.format(finalCharge) );
    }
}
