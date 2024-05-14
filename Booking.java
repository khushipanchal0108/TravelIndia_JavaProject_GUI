package com.example.javafxx;

import java.time.LocalDate;

public class Booking {
    private int bookingID;
    private int userID;
    private int accommodationID;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private int numberOfPeople;
    private int numberOfRooms;

    public Booking(int bookingID, int userID, int accommodationID, LocalDate checkInDate, LocalDate checkOutDate, String status, int numberOfRooms) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.numberOfRooms = numberOfRooms;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAccommodationID() {
        return accommodationID;
    }

    public void setAccommodationID(int accommodationID) {
        this.accommodationID = accommodationID;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}