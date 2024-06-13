package com.example.DAO;

import com.example.entity.DentistryPatient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class PatientProxy implements IDAO<DentistryPatient>{

    public static void main(String[] args) {
        PatientProxy patientProxy = new PatientProxy();
        System.out.println(patientProxy.showAll());
        System.out.println(patientProxy.isXMLDataSource());
//        DentistryPatient patient1 = new DentistryPatient("Borisenko", "Борис", "Borisovich", "17.07.2000", "male", "Consultation");
//        patient.sortByBirthday();
//        System.out.println(patient.sortByBirthday());
    }

    private IDAO<DentistryPatient> patientDAO;

    public PatientProxy() {}

    private final String URL = "jdbc:mysql://localhost:3306/work";
    private final String name = "root";
    private final String password = "root";

    private static final String XML_FILE_PATH = "/D:/Универ/3_курс/Web Java/Lab_2/WebAppMaierLab2/patients.xml";
    private static final XStream xStream = new XStream(new DomDriver());

    private boolean getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, name, password);
            System.out.print("connection: true - ");
            System.out.println(connection);
            patientDAO = new DAOPatient();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            patientDAO = new PatientProxy();
            System.out.println("false");
            e.printStackTrace();
            return false;
        }
    }

    private void updateImpl(){
        patientDAO = getConnection() ? new DAOPatient() : new PatientProxy();
    }
    @Override
    public List<DentistryPatient> showAll() {
        System.out.println(this.getClass().getName() + " showAll");

        try {
            if (getConnection()) {
                return patientDAO.showAll();
            } else {
                System.out.println("show all without connection");

                try (FileReader reader = new FileReader(XML_FILE_PATH)) {
                    xStream.alias("patient", DentistryPatient.class);
                    xStream.alias("patients", List.class);
                    xStream.addPermission(AnyTypePermission.ANY);
                    return (List<DentistryPatient>) xStream.fromXML(reader);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error reading from XML file", e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error in showAll", e);
        }
    }

    @Override
    public DentistryPatient findById(Long id) {
        updateImpl();
        if(getConnection())
            return patientDAO.findById(id);
        return null;
    }

    @Override
    public DentistryPatient findByKey(DentistryPatient template) {
        updateImpl();
        if(getConnection())
            return patientDAO.findByKey(template);
        return null;
    }

    @Override
    public boolean insert(DentistryPatient entityToSave) {
        updateImpl();
        if(getConnection())
            return patientDAO.insert(entityToSave);
        return false;
    }

    @Override
    public boolean update(Long id, DentistryPatient entityToUpdate) {
        updateImpl();
        if(getConnection())
            return patientDAO.update(id, entityToUpdate);
        return false;
    }

    @Override
    public boolean delete(Long id) {
        updateImpl();
        if(getConnection())
            return  patientDAO.delete(id);
        return false;
    }
    public boolean isXMLDataSource() {
        return patientDAO instanceof PatientProxy;
    }
}
