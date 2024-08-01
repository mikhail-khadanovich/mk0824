package com.khadanovich.toolrental.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtil {

    public static int getWeekendDaysCount(LocalDate startDate, int daysCount) {
        LocalDate endDate = startDate.plusDays(daysCount);

        return Stream.iterate(startDate, date -> !date.isAfter(endDate), date -> date.plusDays(1))
                .collect(Collectors.toList()).stream()
                .filter(s -> s.getDayOfWeek() == DayOfWeek.SATURDAY || s.getDayOfWeek() == DayOfWeek.SUNDAY)
                .collect(Collectors.toList()).size();
    }

    public static int getHolidaysCount(LocalDate startDate, int daysCount) {
        LocalDate endDate = startDate.plusDays(daysCount);
        LocalDate fourthOfJulyDate = getFourthOfJulyDate(startDate.getYear());
        LocalDate laborDayDate = getLaborDayDate(startDate.getYear());

        return Stream.iterate(startDate, date -> !date.isAfter(endDate), date -> date.plusDays(1))
                .collect(Collectors.toList()).stream()
                .filter(s -> s.equals(fourthOfJulyDate)  || s.equals(laborDayDate) )
                .collect(Collectors.toList()).size();
    }

    private static LocalDate getFourthOfJulyDate(int year) {
        LocalDate fourthOfJuly = LocalDate.of(year, 7, 4);
        if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return fourthOfJuly.plusDays(2);
        } else if (fourthOfJuly.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return fourthOfJuly.plusDays(1);
        }
        return fourthOfJuly;
    }

    private static LocalDate getLaborDayDate(int year) {
        LocalDate date = LocalDate.of(year, 9, 1);
        while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.plusDays(1);
        }
        return date;
    }
}
