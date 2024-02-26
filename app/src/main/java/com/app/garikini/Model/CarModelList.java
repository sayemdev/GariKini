package com.app.garikini.Model;

import java.util.HashMap;
import java.util.Map;

public class CarModelList {
    public static Map<String, String[]> getCarModels() {
        Map<String, String[]> carModels = new HashMap<>();

        carModels.put("Toyota", new String[]{"Corolla", "Prius", "Hilux", "Fortuner"});
        carModels.put("Honda", new String[]{"Civic", "Accord", "CR-V"});
        carModels.put("Suzuki", new String[]{"Swift", "Baleno", "Ciaz", "Vitara"});
        carModels.put("Nissan", new String[]{"X-Trail", "Navara"});
        carModels.put("Mitsubishi", new String[]{"Pajero", "Outlander"});
        carModels.put("Ford", new String[]{"Ranger", "Everest"});
        carModels.put("Hyundai", new String[]{"Tucson", "Creta"});
        carModels.put("BMW", new String[]{"3 Series", "5 Series", "X5"});
        carModels.put("Mercedes-Benz", new String[]{"C-Class", "E-Class", "GLC"});
        carModels.put("Chevrolet", new String[]{"Camaro", "Equinox", "Malibu"});
        carModels.put("Volkswagen", new String[]{"Golf", "Passat", "Tiguan"});
        carModels.put("Kia", new String[]{"Seltos", "Sportage", "Telluride"});
        carModels.put("Audi", new String[]{"A3", "A4", "Q5"});
        carModels.put("Volvo", new String[]{"S60", "XC40", "XC90"});
        carModels.put("Lexus", new String[]{"RX", "ES", "UX"});
        carModels.put("Subaru", new String[]{"Forester", "Outback", "Crosstrek"});
        carModels.put("Peugeot", new String[]{"308", "3008", "5008"});
        carModels.put("Jaguar", new String[]{"XE", "F-PACE", "I-PACE"});
        carModels.put("Land Rover", new String[]{"Discovery", "Range Rover Evoque", "Defender"});
        carModels.put("Fiat", new String[]{"500", "Panda", "Tipo"});
        carModels.put("Porsche", new String[]{"911", "Cayenne", "Panamera"});
        carModels.put("Tesla", new String[]{"Model 3", "Model S", "Model X"});
        carModels.put("Maserati", new String[]{"Ghibli", "Levante", "Quattroporte"});
        carModels.put("Ferrari", new String[]{"488 GTB", "F8 Tributo", "Portofino"});
        carModels.put("Lamborghini", new String[]{"Huracan", "Aventador", "Urus"});
        carModels.put("TaTa", new String[]{"Naxon", "Punch", "Harrier", "Altroz", "Tiago", "Safari"});
        carModels.put("Mahindra", new String[]{"Scorpio N", "Thar", "Scorpio", "Bolero", "XUV700", "XUV300", "BE.05"});
        carModels.put("JAC", new String[]{"JS8", "J7 Plus", "J7", "JS6", "N-series", "Heavy truck", "X-series", "T8 PRO", "T6", "Sunray"});
        carModels.put("Proton", new String[]{"Proton Saga", "Proton Persona", "Proton Exora", "Proton GEN-2", "Proton X70", "Proton Wira", "Proton X50", "Proton Prevé", "Proton Iriz", "Proton Waja", "Proton Perdana", "Proton Satria", "Proton Suprima S", "Proton Inspira", "Proton Ertiga", "Proton Putra", "Proton Satria Neo", "Proton Tiara", "Proton Satria R3"});
        carModels.put("ISUZU",new String[]{"NLR Truck","NMR Truck","NQR Truck","NMR Dump Truck","FSR Truck","NQR Truck"});

        return carModels;
    }
}
