package com.khadanovich.toolrental.service;

import com.khadanovich.toolrental.model.RentalAgreement;
import com.khadanovich.toolrental.model.Tool;
import com.khadanovich.toolrental.model.ToolType;
import com.khadanovich.toolrental.util.DateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class CheckoutService {
    private final List<Tool> tools;

    public CheckoutService() {
        ToolType toolTypeLadder = ToolType.builder().toolType("Ladder").dailyCharge(1.99).weekendCharge(true)
                .weekdayCharge(true).holidayCharge(false).build();
        ToolType toolTypeChainsaw = ToolType.builder().toolType("Chainsaw").dailyCharge(1.49).weekendCharge(false)
                .weekdayCharge(true).holidayCharge(true).build();
        ToolType toolTypeJackhammer = ToolType.builder().toolType("Jackhammer").dailyCharge(2.99).weekendCharge(false)
                .weekdayCharge(true).holidayCharge(false).build();
        Tool chainsawStihl = Tool.builder().toolCode("CHNS").toolType(toolTypeChainsaw).brand("Stihl").build();
        Tool ladderWerner = Tool.builder().toolCode("LADW").toolType(toolTypeLadder).brand("Werner").build();
        Tool jackhammerDewalt = Tool.builder().toolCode("JAKD").toolType(toolTypeJackhammer).brand("DeWalt").build();
        Tool jackhammerRidgid = Tool.builder().toolCode("JAKR").toolType(toolTypeJackhammer).brand("Ridgid").build();
        this.tools = List.of(chainsawStihl, ladderWerner, jackhammerDewalt, jackhammerRidgid);

    }

    public RentalAgreement checkout(String toolCode, int rentalDaysCount, int discountPercentage, LocalDate checkoutDate) {
        this.isValidInput(rentalDaysCount, discountPercentage);

        int holidaysCount = DateUtil.getHolidaysCount(checkoutDate, rentalDaysCount);
        int weekendDaysCount = DateUtil.getWeekendDaysCount(checkoutDate, rentalDaysCount);

        Tool tool = tools.stream().filter(t -> t.getToolCode().equals(toolCode)).findFirst().get();
        int chargeRentalDaysCount = tool.getToolType().getHolidayCharge() ? rentalDaysCount : rentalDaysCount - holidaysCount;
        chargeRentalDaysCount = tool.getToolType().getWeekendCharge() ? chargeRentalDaysCount : chargeRentalDaysCount - weekendDaysCount;
        double preDiscountCharge = BigDecimal.valueOf(chargeRentalDaysCount*tool.getToolType().getDailyCharge())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        double discountAmount = BigDecimal.valueOf(( (double) discountPercentage / 100.00 ) * preDiscountCharge)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        double finalCharge = BigDecimal.valueOf(preDiscountCharge - discountAmount)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        return RentalAgreement.builder().dailyRentalCharge(tool.getToolType().getDailyCharge())
                .discountAmount(discountAmount).
                preDiscountCharge(preDiscountCharge)
                .checkoutDate(checkoutDate)
                .chargeDays(chargeRentalDaysCount)
                .dueDate(checkoutDate.plusDays(rentalDaysCount))
                .toolCode(toolCode)
                .finalCharge(finalCharge)
                .rentalDays(rentalDaysCount)
                .toolBrand(tool.getBrand())
                .toolType(tool.getToolType())
                .toolBrand(tool.getBrand())
                .build();
    }

    private void isValidInput(int rentalDaysCount, int discountPercentage) {
        if (rentalDaysCount < 1) {
            throw new IllegalArgumentException("Rental days count must be 1 or greater");
        }
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
    }

}
