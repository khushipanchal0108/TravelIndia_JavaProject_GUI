package com.example.javafxx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItineraryService {
    private Map<Integer, Itinerary> itineraries = new HashMap<>();
    private int nextItineraryId = 1;

    public Itinerary createItinerary(int userID, LocalDate startDate, LocalDate endDate, String destinationName, int numPeople, String transport, String accommodation, List<String> sightseeingOptions) {
        Itinerary itinerary = new Itinerary(nextItineraryId++, userID, startDate, endDate, destinationName, numPeople, transport, accommodation, sightseeingOptions);
        itineraries.put(itinerary.getItineraryID(), itinerary);
        return itinerary;
    }

    public static class Itinerary implements Myinterface{
        private int itineraryID;
        private int userID;
        private LocalDate startDate;
        private LocalDate endDate;
        private String destinationName;
        private int numPeople;
        private String transport;
        private String accommodation;
        private List<String> sightseeingOptions;

        public int getItineraryID() {
            return itineraryID;
        }

        public void setItineraryID(int itineraryID) {
            this.itineraryID = itineraryID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getDestinationName() {
            return destinationName;
        }

        public void setDestinationName(String destinationName) {
            this.destinationName = destinationName;
        }

        public int getNumPeople() {
            return numPeople;
        }

        public void setNumPeople(int numPeople) {
            this.numPeople = numPeople;
        }

        public String getTransport() {
            return transport;
        }

        public void setTransport(String transport) {
            this.transport = transport;
        }

        public String getAccommodation() {
            return accommodation;
        }

        public void setAccommodation(String accommodation) {
            this.accommodation = accommodation;
        }

        public List<String> getSightseeingOptions() {
            return sightseeingOptions;
        }

        public void setSightseeingOptions(List<String> sightseeingOptions) {
            this.sightseeingOptions = sightseeingOptions;
        }

        public Itinerary(int itineraryID, int userID, LocalDate startDate, LocalDate endDate, String destinationName, int numPeople, String transport, String accommodation, List<String> sightseeingOptions) {
            this.itineraryID = itineraryID;
            this.userID = userID;
            this.startDate = startDate;
            this.endDate = endDate;
            this.destinationName = destinationName;
            this.numPeople = numPeople;
            this.transport = transport;
            this.accommodation = accommodation;
            this.sightseeingOptions = new ArrayList<>(sightseeingOptions.subList(0,(int)startDate.until(endDate).getDays()));
        }

        @Override
        public void display() {
            System.out.println("\nComplete Itinerary:");
            System.out.println("Destination: " + destinationName);
            System.out.println("Check-in Date: " + startDate);
            System.out.println("Check-out Date: " + endDate);
            System.out.println("Number of People: " + numPeople);
            System.out.println("Preferred Mode of Transport within the Destination: " + transport);
            System.out.println("Accommodation: " + accommodation);
            System.out.println("Sightseeing Options:");
            for (int i = 0; i < sightseeingOptions.size(); i++) {
                System.out.println("[Day " + (i + 1) + "]: " + sightseeingOptions.get(i));
            }
        }
    }
}
