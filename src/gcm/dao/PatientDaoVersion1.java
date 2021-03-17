package gcm.dao;

import java.util.ArrayList;
import java.util.List;

import gcm.model.Patient;

public class PatientDaoVersion1 implements IPatientDao {

    static private List<Patient> listPatients = new ArrayList<Patient>();

    public void add(Patient patient) {
        listPatients.add(patient);
    }

    public void delete(int nss) {
        Patient patient = new Patient();
        patient.setNss(nss);
        listPatients.remove(patient);
    }

    public void update(int nss) {
        Patient patient = new Patient();
        patient.setNss(nss);
        int index=1;
        listPatients.add(index,patient);
    }

    public List<Patient> findAll() {
        List<Patient> list =listPatients;
        return list;
    }

	@Override
	public void update(int nss, String ville, String adresse) {
		// TODO Auto-generated method stub
		
	}
}


