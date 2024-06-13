package com.example.DAO;

import com.example.entity.DentistryPatient;
import com.example.DBUtills.HibernateUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DAOPatient implements IDAO<DentistryPatient>{

    public static void main(String[] args) {
        DAOPatient patient = new DAOPatient();
        System.out.println(patient.showAll());
        DentistryPatient patient1 = new DentistryPatient("Borisenko", "Борис", "Borisovich", "17.07.2000", "male", "Consultation");
        patient.sortByBirthday();
        System.out.println(patient.sortByBirthday());
//        patient.insert(new DentistryPatient("Borisenko", "Борис", "Borisovich", "17.07.2000", "male", "Consultation"));
//        System.out.println(patient.findByInitials("Sidorchuk Borisovich"));
    }

//  Get all visits from database (select * from employees)
@Override
public List<DentistryPatient> showAll() {
    List<DentistryPatient> list = null;
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        list = session.createQuery("from DentistryPatient", DentistryPatient.class).getResultList();
        saveToXML(list);
//            Query<DentistryVisit> query = session.createQuery("from DentistryVisit", DentistryVisit.class);
//            return query.list();
    }
    return list;
}

    // Get visit by ID
    @Override
    public DentistryPatient findById(Long id) {
        DentistryPatient patient;// = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {// sessionFactory.openSession()){
            patient = session.get(DentistryPatient.class, id);
        }
        return patient;
    }

    //  Find visit by Key (Surname)
    @Override
    public DentistryPatient findByKey(DentistryPatient template) {
        List<DentistryPatient> dentistryPatients;// = null;
        DentistryPatient patientInDB = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            dentistryPatients = session.createQuery("from DentistryPatient", DentistryPatient.class).getResultList();
//            patientInDB = dentistryPatients.stream().filter(patient -> patient.getSurname().equals(template.getSurname())).findFirst().get();
            Optional<DentistryPatient> filteredList = dentistryPatients.stream().filter(patient -> patient.getSurname().equals(template.getSurname())).findFirst();
            if (filteredList.isPresent()) {
                patientInDB = filteredList.get();
            }
        }
        return patientInDB;
    }

    public boolean existInDB(DentistryPatient patient, List<DentistryPatient> patients){
        return patients.stream().anyMatch(p -> p.equals(patient));
    }

    // check if such patient exist in DB
    public DentistryPatient findID(DentistryPatient template){
        List<DentistryPatient> dentistryPatients;// = null;
        DentistryPatient patientInDB = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            dentistryPatients = session.createQuery("from DentistryPatient", DentistryPatient.class).getResultList();
            Optional<DentistryPatient> filteredList = dentistryPatients.stream().filter(patient -> patient.equals(template)).findFirst();
            if (filteredList.isPresent()) {
                patientInDB = filteredList.get();
            }
        }
        return patientInDB;
    }

    public List<DentistryPatient> sortByBirthday(){
        List<DentistryPatient> patients = showAll();
        patients.sort(Comparator.comparing(DentistryPatient::getBirthday));
        return patients;
    }

    public List<DentistryPatient> findByInitials(String searchBar)//String surname, String name)
    {
        List<DentistryPatient> dentistryPatients;// = null;
        List<DentistryPatient> result = null;
        String[] initials = searchBar.toLowerCase().split("\\s+");
        List<String> initialsList = Arrays.stream(initials).collect(Collectors.toList());
        String first = "";  //surname
        String second = ""; //name
        String third = "";  //mid name

        List<String> surnames = showAll().stream().map(p -> p.getSurname().toLowerCase()).distinct().collect(Collectors.toList());
        List<String> names = showAll().stream().map(p -> p.getName().toLowerCase()).distinct().collect(Collectors.toList());
        List<String> midNames = showAll().stream().map(p -> p.getMidName().toLowerCase()).distinct().collect(Collectors.toList());
        for(String s : initialsList){
            if(surnames.contains(s))
                first = s;
            else if(names.contains(s))
                second = s;
            else if(midNames.contains(s))
                third = s;
        }
        System.out.println("surname: " + first + " name: " + second + " midname: " + third);
        searchBar = searchBar.toLowerCase();

        /**-----------------------------------------------------*/
        for(DentistryPatient patient : showAll()){
            String regex = "(.*(" + patient.getSurname().toLowerCase() + "))?(.*(" + patient.getName().toLowerCase() + "))?(.*(" + patient.getMidName().toLowerCase() + "))?";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(searchBar);
            if(matcher.find()){
                System.out.println(matcher.group(0));
//                for (int i = 1; i <= matcher.groupCount(); i++) {
//                    System.out.println("Group " + i + ": " + matcher.group(i));
//                }
            }
        }

        /**-----------------------------------------------------*/

        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            dentistryPatients = session.createQuery("from DentistryPatient", DentistryPatient.class).getResultList();

//            for (String surname : initialsList) {
//                // list with surnames
//                result = dentistryPatients.stream().filter(p -> p.getSurname().equalsIgnoreCase(surname)).collect(Collectors.toList());
//            }
//            // then find name in found surnameList
//            if (initials.length > 1) {
//                first = result.get(0).getSurname(); //surname
//                initialsList.remove(first);
//                for (String name : initialsList)
//                    result = result.stream().filter(p -> p.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
//            }
//            if (initials.length > 2) {
//                second = result.get(0).getName();
//                initialsList.remove(second);
//                for (String midName : initialsList)
//                    result = result.stream().filter(p -> p.getMidName().equalsIgnoreCase(midName)).collect(Collectors.toList());
//            }
        }
        return result;
    }

    @Override
    public boolean insert(DentistryPatient entityToSave) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entityToSave);
//            session.persist(entityToSave);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.err.println("Error! Insert data: " + getClass());
            return false;
        }
    }

    @Override
    public boolean update(Long id, DentistryPatient entityToUpdate) {
        DentistryPatient patient = findById(id);
        if(patient == null){
            System.err.println("Error. Object to update not found");
            return false;
        }
        else {
            Transaction transaction = null;
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                entityToUpdate.setPatient_id(id);
                session.update(entityToUpdate);
//                session.merge(patient);
                transaction.commit();
                System.out.println(transaction);
                return true;
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
                System.err.println("Error! Delete data: " + getClass());
                return false;
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        DentistryPatient patient = findById(id);
        if(patient == null){
            System.err.println("Error. Object to delete was not found");
            return false;
        }
        else {
            Transaction transaction = null;
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
//            session.delete(visit);
                session.remove(patient);
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction != null)
                    transaction.rollback();
                System.err.println("Error! Delete data: " + getClass());
                return false;
            }
        }
    }

    private static final String XML_FILE_PATH = "/D:/Универ/3_курс/Web Java/Lab_2/WebAppMaierLab2/patients.xml";
    public void saveToXML(List <DentistryPatient> patients){
        XStream xStream = new XStream();
        xStream.addPermission(AnyTypePermission.ANY);
        xStream.alias("patient", DentistryPatient.class);
        xStream.alias("patients", List.class);
        String content = xStream.toXML(patients);
        try(FileWriter fileWriter = new FileWriter(XML_FILE_PATH); PrintWriter printWriter = new PrintWriter(fileWriter)) {
            System.out.println("Enter to writer");
            printWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
