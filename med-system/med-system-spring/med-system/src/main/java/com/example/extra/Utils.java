package com.example.extra;

import com.example.entity.DentistryVisit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utils {

    public static Map<Double, String> servicesMap(){
        Map<Double, String> services = new HashMap<>();
        services.put( 350.0, "Consultation");
        services.put(1490.0, "Teeth whitening");
        services.put(1200.0, "Treatment of caries");
        services.put(2400.0, "Periodontal therapy");
        services.put(850.0, "Restoration of tooth crown");
        services.put(180.0, "Dental x-rays");
        services.put(750.0, "Tooth removing");
        return services;
    }

    public static void main(String[] args) {
        System.out.println(servicesMap().get(350.0));
    }
}
