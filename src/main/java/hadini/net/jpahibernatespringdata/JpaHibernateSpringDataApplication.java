package hadini.net.jpahibernatespringdata;

import hadini.net.jpahibernatespringdata.entites.Patient;
import hadini.net.jpahibernatespringdata.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class JpaHibernateSpringDataApplication implements CommandLineRunner {

	@Autowired
	private PatientRepository patientRepository;
	public static void main(String[] args) {

		SpringApplication.run(JpaHibernateSpringDataApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {


		//- Ajouter des patients
		patientRepository.save(new Patient(null,"mohamed",new Date(),true,10));
		patientRepository.save(new Patient(null,"Ahmed",new Date(),false,90));
		patientRepository.save(new Patient(null,"zineb",new Date(),true,9));
		patientRepository.save(new Patient(null,"halima",new Date(),false,10));

		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("=== MENU ===");
			System.out.println("1. Ajouter un patient");
			System.out.println("2. Consulter tous les patients");
			System.out.println("3. Consulter un patient par ID");
			System.out.println("4. Chercher des patients par nom");
			System.out.println("5. Mettre à jour un patient");
			System.out.println("6. Supprimer un patient");
			System.out.println("0. Quitter");
			System.out.print("Votre choix : ");

			int choix = scanner.nextInt();
			scanner.nextLine();

			switch (choix) {
				case 1:
					System.out.println("- Ajouter un patient");
					System.out.print("Nom du patient : ");
					String nom = scanner.nextLine();
					System.out.print("Date de naissance (au format yyyy-MM-dd) : ");
					String dateNaissanceStr = scanner.nextLine();

					Date dateNaissance = new SimpleDateFormat("yyyy-MM-dd").parse(dateNaissanceStr);
					System.out.print("Est malade ? (true/false) : ");
					boolean malade = scanner.nextBoolean();
					System.out.print("Score : ");
					int score = scanner.nextInt();

					patientRepository.save(new Patient(null, nom, dateNaissance, malade, score));
					break;
				case 2:
					System.out.println("- Consulter tous les patients");
					patientRepository.findAll().forEach(System.out::println);
					break;
				case 3:
					System.out.println("- Consulter un patient par ID");
					System.out.print("ID du patient : ");
					Long patientId = scanner.nextLong();
					Patient patient = patientRepository.findById(patientId).orElse(null);
					if (patient != null) {
						System.out.println(patient);
					} else {
						System.out.println("Patient introuvable.");
					}
					break;
				case 4:
					System.out.println("- Chercher des patients par nom");
					System.out.print("Nom (partiel ou complet) : ");
					String nomRecherche = scanner.nextLine();
					patientRepository.findByNomContainingIgnoreCase(nomRecherche).forEach(System.out::println);
					break;
				case 5:
					System.out.println("- Mettre à jour un patient");
					System.out.print("ID du patient à mettre à jour : ");
					Long patientIdToUpdate = scanner.nextLong();

					Patient patientToUpdate = patientRepository.findById(patientIdToUpdate).orElse(null);
					if (patientToUpdate != null) {
						scanner.nextLine();
						System.out.print("Nom de patinet:");
						String newNom=scanner.nextLine();
						System.out.print("Nouveau statut malade (true/false) : ");
						boolean newMalade = scanner.nextBoolean();

						patientToUpdate.setMalade(newMalade);
						patientToUpdate.setNom(newNom);
						patientRepository.save(patientToUpdate);
						System.out.println("Patient mis à jour avec succès.");
					} else {
						System.out.println("Patient introuvable pour la mise à jour.");
					}
					break;
				case 6:
					System.out.println("- Supprimer un patient");

					System.out.print("ID du patient à supprimer : ");
					Long patientIdToDelete = scanner.nextLong();
					Patient patientToDelete = patientRepository.findById(patientIdToDelete).orElse(null);
					if (patientToDelete != null) {
						patientRepository.delete(patientToDelete);
						System.out.println("Patient supprimé avec succès.");
					} else {
						System.out.println("Patient introuvable pour la suppression.");
					}
					break;
				case 0:
					exit = true;
					break;
				default:
					System.out.println("Choix invalide. Veuillez réessayer.");
			}
		}


	}
}
