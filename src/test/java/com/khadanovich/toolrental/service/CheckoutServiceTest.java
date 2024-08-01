package com.khadanovich.toolrental.service;

import com.khadanovich.toolrental.model.RentalAgreement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CheckoutServiceTest {
    private CheckoutService checkoutServiceTestObj;

    @BeforeEach
    void setUp() {
        checkoutServiceTestObj = new CheckoutService();
    }

    @Test
    public void testCheckoutShouldThrowExceptionIfDiscountPercentageOver100Test1() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            checkoutServiceTestObj.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        });
        assertEquals("Discount percentage must be between 0 and 100", thrown.getMessage());
    }

    @Test
    public void testCheckoutShouldApplyDiscountOnlyTest2() {

        RentalAgreement rentalAgreement = checkoutServiceTestObj.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        rentalAgreement.printRentalAgreement();
        assertEquals(0.60, rentalAgreement.getDiscountAmount());
        assertEquals(5.37, rentalAgreement.getFinalCharge());
        assertEquals(5.97, rentalAgreement.getPreDiscountCharge());

    }

    @Test
    public void testCheckoutShouldNotChargeOnWeekendsAndApplyDiscountTest3() {

        RentalAgreement rentalAgreement = checkoutServiceTestObj.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        rentalAgreement.printRentalAgreement();
        assertEquals(1.12, rentalAgreement.getDiscountAmount());
        assertEquals(3.35, rentalAgreement.getFinalCharge());
        assertEquals(4.47, rentalAgreement.getPreDiscountCharge());
    }

    @Test
    public void testCheckoutShouldNotChargeOnWeekendsAndHolidaysAndApplyDiscountTest4() {

        RentalAgreement rentalAgreement = checkoutServiceTestObj.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        rentalAgreement.printRentalAgreement();
        assertEquals(0.00, rentalAgreement.getDiscountAmount());
        assertEquals(8.97, rentalAgreement.getFinalCharge());
        assertEquals(8.97, rentalAgreement.getPreDiscountCharge());
        assertEquals(3, rentalAgreement.getChargeDays());
    }

    @Test
    public void testCheckoutShouldNotChargeOnWeekendsAndHolidaysAndApplyDiscountTest5() {

        RentalAgreement rentalAgreement = checkoutServiceTestObj.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        rentalAgreement.printRentalAgreement();
        assertEquals(0.00, rentalAgreement.getDiscountAmount());
        assertEquals(14.95, rentalAgreement.getFinalCharge());
        assertEquals(14.95, rentalAgreement.getPreDiscountCharge());
        assertEquals(5, rentalAgreement.getChargeDays());
    }

    @Test
    public void testCheckoutShouldNotChargeOnWeekendsAndHolidaysAndApplyDiscountTest6() {

        RentalAgreement rentalAgreement = checkoutServiceTestObj.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        rentalAgreement.printRentalAgreement();
        assertEquals(1.50, rentalAgreement.getDiscountAmount());
        assertEquals(1.49, rentalAgreement.getFinalCharge());
        assertEquals(2.99, rentalAgreement.getPreDiscountCharge());
        assertEquals(1, rentalAgreement.getChargeDays());
    }
}
