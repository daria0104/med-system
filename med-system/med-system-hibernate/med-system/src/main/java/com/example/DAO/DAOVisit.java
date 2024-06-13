package com.example.DAO;

import com.example.entity.DentistryPatient;
import com.example.entity.DentistryVisit;
import com.example.DBUtills.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;


public class DAOVisit implements IDAO<DentistryVisit> {
//    private SessionFactory sessionFactory;
//
//    /**  Configure "Hibernate"  */
//    public void init() {
//        sessionFactory = new Configuration()
//                .configure()
//                .buildSessionFactory();
//    }

    public static void main(String[] args) {
        DentistryVisit dentistryVisit = new DentistryVisit(
                new DentistryPatient("Sidorenko", "Vadim", "Alexandrovich", "17.07.1997", "male", "Tooth without nerve hurt"),
                "1425", "Consultation", "23.09.2023 12:30", 500, 500);
        DAOVisit daoVisit = new DAOVisit();
    }

    //  Get all visits from database (select * from employees)
    @Override
    public List<DentistryVisit> showAll() {
        List<DentistryVisit> list = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            list = session.createQuery("from DentistryVisit", DentistryVisit.class).getResultList();
//            Query<DentistryVisit> query = session.createQuery("from DentistryVisit", DentistryVisit.class);
//            return query.list();
        }
        return list;
    }

    // Get visit by ID
    @Override
    public DentistryVisit findById(Long id) {
        DentistryVisit visit = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {// sessionFactory.openSession()){
            visit = session.get(DentistryVisit.class, id);
        }
        return visit;
    }

    //  Find visit by Key (visitCode)
    @Override
    public DentistryVisit findByKey(DentistryVisit template) {
        List<DentistryVisit> dentistryVisit;// = null;
        DentistryVisit visitInDB = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            dentistryVisit = session.createQuery("from DentistryVisit", DentistryVisit.class).getResultList();
//            visitInDB = dentistryVisit.stream().filter(visit -> visit.getDoctorCode().equals(template.getDoctorCode())).findFirst().get();
            Optional<DentistryVisit> filteredList = dentistryVisit.stream().filter(visit -> visit.getVisitCode().equals(template.getVisitCode())).findFirst();

            if (filteredList.isPresent()) {
                visitInDB = filteredList.get();
            }
        }
        return visitInDB;
    }

    //  Find visit by Patient (patient_id)
    public List<DentistryVisit> findByPatient(long patient_id) {
        List<DentistryVisit> dentistryVisit;// = null;
        DentistryVisit visitInDB = null;
        List<DentistryVisit> result;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            dentistryVisit = session.createQuery("from DentistryVisit", DentistryVisit.class).getResultList();
            result = dentistryVisit.stream().
                    filter(visit -> visit.getPatient().getPatient_id().equals(patient_id)).
                    collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public boolean insert(DentistryVisit entityToSave) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entityToSave);
//            session.persist(entityToSave);
            transaction.commit();
            System.out.println(transaction);
            return true;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.err.println("Error! Insert data: " + getClass());
            return false;
        }
    }

    @Override
    public boolean update(Long id, DentistryVisit entityToUpdate) {
        DentistryVisit visit = findById(id);
        if(visit == null){
            System.err.println("Error. Object to update was not found");
            return false;
        }
        else {
            Transaction transaction = null;
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                entityToUpdate.setVisit_id(id);
                long patient_id = visit.getPatient().getPatient_id();
                entityToUpdate.getPatient().setPatient_id(patient_id);
                entityToUpdate.setVisitCode(visit.getVisitCode());
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
        DentistryVisit visit = findById(id);
        if(visit == null){
            System.err.println("Error. Object to delete was not found");
            return false;
        }
        else {
            Transaction transaction = null;
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
//            session.delete(visit);
                session.remove(visit);
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

    public boolean availableTime(DentistryVisit visit, List<DentistryVisit> visitsList){
        return visitsList.stream().noneMatch(v -> Objects.equals(visit.getDoctorCode(), v.getDoctorCode()) && visit.getTimeVisit().equals(v.getTimeVisit()));
    }
}
