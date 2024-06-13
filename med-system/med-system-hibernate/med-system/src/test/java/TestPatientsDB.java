import com.example.DAO.DAOPatient;
import com.example.entity.DentistryPatient;
import com.example.DBUtills.HibernateUtils;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestPatientsDB {

    private static Session session;
    DAOPatient daoPatient = new DAOPatient();

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
        System.out.println("Patients");
        List<DentistryPatient> patientList = daoPatient.showAll();
        patientList.forEach(System.out::println);
    }

    @Test
    void testInsertPatient() {
        System.out.println("Inserting patient to DB.");
        DentistryPatient patient = new DentistryPatient("Ivanov", "Stepan", "Stepanovich", "22.02.1992", "male", "Consultation.");
        if(daoPatient.findByKey(patient) == null) {
            if (daoPatient.insert(patient)) {
                System.out.println("Patient successfully inserted to DB.");
                List<DentistryPatient> patientList = daoPatient.showAll();
                patientList.forEach(System.out::println);
            } else {
                System.err.println("Error. Patient not inserted to DB.");
            }
        }else{
            System.err.println("Patient to insert already exist in DB.");
        }
    }

    @Test
    void testUpdatePatient() {
        System.out.println("Updating patient in DB.");
        DentistryPatient toUpdate = new DentistryPatient("Kravets");
        DentistryPatient patient = daoPatient.findByKey(toUpdate);
        if (patient != null) {
            long id = patient.getPatient_id();
            DentistryPatient updated = new DentistryPatient("Borisenko", "Ivan", "Ivanovich", "12.02.1980", "male", "Consultation");
            if (daoPatient.findByKey(updated) == null) {
                if (daoPatient.update(id, updated)) {
                    System.out.println("Patient successfully updated in DB.");
                    List<DentistryPatient> patientList = daoPatient.showAll();
                    patientList.forEach(System.out::println);
                } else {
                    System.err.println("Error. Patient not updated to DB.");
                }
            } else {
                System.err.println("Patient already exist in DB.");
            }
        } else {
            System.err.println("Error. Patient to update not found.");
        }
    }

    @Test
    void testDeletePatient() {
        System.out.println("Deleting patient to DB.");
        DentistryPatient toDelete = new DentistryPatient("Sidorchuk");
        DentistryPatient patient = daoPatient.findByKey(toDelete);
        if (patient != null) {
            long id = patient.getPatient_id();
            if (daoPatient.delete(id)) {
                System.out.println("Patient successfully deleted from DB.");
                List<DentistryPatient> patientList = daoPatient.showAll();
                patientList.forEach(System.out::println);
            } else {
                System.err.println("Error. Patient not deleted to DB.");
            }
        } else {
            System.err.println("Patient to delete not found in DB.");
        }
    }

    @Test
    void testCRUD() {
        testCreateDB();
        testInsertPatient();
        testUpdatePatient();
        testDeletePatient();
    }
}
