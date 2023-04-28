package com.busreservationsystem.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The Database class embodies a database system to keep track of all
 * Users, Admins, Buses, and Bookings.
 *
 * @author Ethan Tran
 * @author Nikolaos Polyhronopoulos
 * @author Christopher Soussa
 */
public class Database {

    private final static ArrayList<Client> clients = new ArrayList<>();
    private final static ArrayList<Admin> admins = new ArrayList<>();
    private final static ArrayList<Bus> buses = new ArrayList<>();
    private final static ArrayList<Booking> bookings = new ArrayList<>();

    private static Admin currentAdmin = null;
    private static Client currentClient = null;
    /**
     * Constructor for the Database.
     * Fills all according Lists according to JSON files specified.
     * Parameters specified should contain only the basename of the JSON files
     * stored at the root level of a directory called 'database'.
     *
     * @param clientsJSON File name of customers JSON file.
     * @param adminJSON File name of admins JSON file.
     * @param bookingsJSON File name of bookings JSON file.
     * @param busJSON File name of buses JSON file.
     */
    public Database(String clientsJSON, String adminJSON, String bookingsJSON, String busJSON) {
        loadJson(busJSON, buses, Bus.class);
        loadJson(clientsJSON, clients, Client.class);
        loadJson(bookingsJSON, bookings, Booking.class);
    }

    /**
     * Loads the data contained in the JSON file into a given List of data.
     *
     * @param json The name of the JSON file to write the data into.
     * @param data The List of data to write into the JSON file.
     * @param classType The class type of the objects contained in the List.
     */
    public void loadJson(String json, List<?> data, Class<?> classType) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, classType);

        json = String.format("src/main/resources/com/busreservationsystem/database/%s.json", json);
        File jsonFile = new File(json);

        try {
            data.addAll(objectMapper.readValue(jsonFile,  collectionType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the data from the List into the given JSON file.
     *
     * @param json The name of the JSON file to write the data into.
     * @param data The List of data to write into the JSON file.
     */
    public void writeJson(String json, List<?> data) {
        json = String.format("src/main/resources/com/busreservationsystem/database/%s.json", json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        File jsonFile = new File(json);
        try {
            objectMapper.writeValue(jsonFile, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    public static void setCurrentClient(Client client) {
        currentClient = client;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void addClient(Client client) {
        clients.add(client);
    }

    public static void addBus(Bus bus) {
        buses.add(bus);
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public static ArrayList<Client> getClients() {
        return clients;
    }

    public static ArrayList<Bus> getBuses() {
        return buses;
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }
}