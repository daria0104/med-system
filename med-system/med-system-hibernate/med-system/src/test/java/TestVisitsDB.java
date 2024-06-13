import com.example.DAO.DAOVisit;
import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import com.example.DBUtills.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestVisitsDB {
    private static Session session;
    DAOVisit daoVisit = new DAOVisit();

    @BeforeAll
    static void beforeAll() {
        session = HibernateUtils.getSessionFactory().openSession();
        System.out.println("Hibernate connected to DB.");
    }

    @Test
    void testCreateDB() {
        System.out.println("DB must be created.");
    }

    @Test
    void testGetAllPatients() {
        System.out.println("Visits");
        List<DentistryVisit> patientList = daoVisit.showAll();
        patientList.forEach(System.out::println);
    }

    @Test
    void testInsertVisit() {
        System.out.println("Inserting visit to DB.");
        DentistryPatient patient = new DentistryPatient("Sidorchuk", "Maria", "Cergiivna", "17.11.1999", "female",
                "Patient has wisdom teeth pain in the left lower jaw. After dental x-rays doctor will able to make a decision.");
        DentistryVisit visit = new DentistryVisit(patient, "0349", "Dental x-rays","26.10.2023 12:45",  400.0, 400.0);
        if(daoVisit.availableTime(visit, daoVisit.showAll())) {//if(daoVisit.findByKey(visit) == null) {
            if (daoVisit.insert(visit)) {
                System.out.println("Visit successfully inserted to DB.");
                List<DentistryVisit> patientList = daoVisit.showAll();
                patientList.forEach(System.out::println);
            } else {
                System.err.println("Error. Visit not inserted to DB.");
            }
        }else{
            System.err.println("Visit to insert already exist in DB.");
        }
    }

    @Test
    void testUpdateVisit() {
        System.out.println("Updating visit in DB.");
        DentistryVisit toUpdate = new DentistryVisit("1698313");
        DentistryVisit visit = daoVisit.findByKey(toUpdate);
        if (visit != null) {
            DentistryPatient patient = new DentistryPatient("Sidorchuk", "Maria", "Cergiivna", "17.11.1999", "female",
                    "Patient has wisdom teeth pain in the left lower jaw. After dental x-rays doctor will able to make a decision.");
            DentistryVisit updated = new DentistryVisit(patient, "0349", "Dental x-rays", "30.10.2023 14:00", 400.0, 400.0);
            List<DentistryVisit> visits = daoVisit.showAll();
            System.out.println("available: " + daoVisit.availableTime(updated, visits));
            if (daoVisit.availableTime(updated, visits)) {
                long id = visit.getVisit_id();
                if (daoVisit.update(id, updated)) {
                    System.out.println("Visit successfully updated in DB.");
                    List<DentistryVisit> visitList = daoVisit.showAll();
                    visitList.forEach(System.out::println);
                } else {
                    System.err.println("Error. Visit not updated to DB.");
                }
            } else {
                System.err.println("Patient already exist in DB.");
            }
            } else {
                System.err.println("Error. Visit to update not found.");
            }
    }


    @Test
    void testDeleteVisit() {
        System.out.println("Deleting visit to DB.");
        DentistryVisit toDelete = new DentistryVisit("1698313");
        DentistryVisit visit = daoVisit.findByKey(toDelete);
        if (visit != null) {
            long id = visit.getVisit_id();
            if (daoVisit.delete(id)) {
                System.out.println("Visit successfully deleted from DB.");
                List<DentistryVisit> patientList = daoVisit.showAll();
                patientList.forEach(System.out::println);
            } else {
                System.err.println("Error. Visit not deleted from DB.");
            }
        } else {
            System.err.println("Visit to delete not found in DB.");
        }
    }

    @Test
    void testCRUD() {
        testCreateDB();
        testInsertVisit();
//        testUpdateVisit();
//        testDeleteVisit();
    }
}
