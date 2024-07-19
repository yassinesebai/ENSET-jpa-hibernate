package hadini.net.jpahibernatespringdata.repository;

import hadini.net.jpahibernatespringdata.entites.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    List<Patient> findByNomContainingIgnoreCase(String nom);
}
