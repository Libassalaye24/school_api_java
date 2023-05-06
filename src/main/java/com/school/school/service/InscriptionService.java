package com.school.school.service;


import com.school.school.exception.InscriptionException;
import com.school.school.model.*;
import com.school.school.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final ClasseRepository classeRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final PeriodeRepository periodeRepository;
    private final PaymentRepository paymentRepository;

    public InscriptionService(InscriptionRepository inscriptionRepository, EtudiantRepository etudiantRepository, ClasseRepository classeRepository, AnneeScolaireRepository anneeScolaireRepository, PeriodeRepository periodeRepository, PaymentRepository paymentRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.etudiantRepository = etudiantRepository;
        this.classeRepository = classeRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.periodeRepository = periodeRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Inscription> findAll(){ return this.inscriptionRepository.findAll(); }
    public List<Inscription> findAllByClasse(Classe classe){
        return this.inscriptionRepository.findAllByClasse(classe);
    }
    public Inscription store(Inscription inscription){
        return processIncription(inscription);
    }

    public Inscription processIncription(Inscription inscription){
        checkEtudiant(inscription);
        checkAnnee(inscription);
        checkAmountInscription(inscription);
        checkEtudiantExistInAnnee(inscription);
        validateAge(inscription);
        checkIfPaymentIsOk(inscription);
        checkIntervalInscription(inscription);
        return this.inscriptionRepository.save(inscription);
    }

    public void checkEtudiant(Inscription inscription){

        if (inscription.getEtudiant().getId() != null){
            Optional<Etudiant> etudiant = this.etudiantRepository.findById(inscription.getEtudiant().getId());
            if (etudiant.isEmpty()){
                throw new InscriptionException("Aucun n'etudiant trouve avec cet ID : "+inscription.getEtudiant().getId());
            }
            etudiant.ifPresent(inscription::setEtudiant);
        }
    }

    public void validateAge(Inscription inscription){
        // Calculer l'âge de la personne en fonction de sa date de naissance
        LocalDate birthDate = inscription.getEtudiant().getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int age = Period.between(birthDate, LocalDate.now()).getYears();
        // Vérifier si la personne a plus de 18 ans
        if (age < 18) {
            throw new InscriptionException("L'etudiant doit etre age au moins de 18 ans");
        }
    }

    public void checkEtudiantExistInAnnee(Inscription inscription){

//        throw new InscriptionException("L'etudiant existe deja dans cette annee scolaire !");
//        Optional <Inscription> inscri = this.inscriptionRepository.findByEtudiantAndAnneescolaire(etudiant , scolaire);

        Annee annee = this.anneeScolaireRepository.findById(inscription.getAnneescolaire().getId()).orElse(null);
        if (annee != null){
            int bool = 0;
            List<Inscription> inscriptions = this.inscriptionRepository.findByAnneescolaire(annee);
            for (Inscription inscription1: inscriptions) {
                if (inscription1.getEtudiant().getId() == inscription.getEtudiant().getId() || inscription1.getEtudiant().getEmail().equals(inscription.getEtudiant().getEmail())){
                    System.out.println("======== L'etudiant existe deja dans cette annee scolaire ! ======== ");
                    throw new InscriptionException("L'etudiant existe deja dans la classe "+inscription1.getClasse().getLibelle()+" annee scolaire " + inscription1.getAnneescolaire().getLibelle());
                }
            }
//            inscriptions.stream().map(inscription1 -> {
//                if (inscription1.getEtudiant().getId() == inscription.getEtudiant().getId()){
//                    throw new InscriptionException("L'etudiant existe deja dans cette annee scolaire !");
//                }
//                return inscription1;
//            });
        }

//        if (inscri.isPresent()){
//            throw new InscriptionException("L'etudiant existe deja dans cette annee scolaire !");
//        }
    }

    public void checkAnnee(Inscription inscription){
        if (inscription.getAnneescolaire().getId() == null){
            Annee annee = this.anneeScolaireRepository.getAnneeByEtat(true).orElse(null);
            if (annee != null){
                inscription.setAnneescolaire(annee);
            }
        }
    }

    public Annee checkAnneeAndGet(Inscription inscription){
        Annee annee;
        if (inscription.getAnneescolaire().getId() == null){
            annee = this.anneeScolaireRepository.getAnneeByEtat(true).orElse(null);
        }else{
            annee = this.anneeScolaireRepository.findById(inscription.getAnneescolaire().getId()).orElse(null);
        }
        if (annee != null){
            inscription.setAnneescolaire(annee);
            return annee;
        }else{
            throw new InscriptionException("Annnee invalide");
        }

    }

    public void checkAmountInscription(Inscription inscription){
        BigDecimal prix = inscription.getFraisInscriptions();
        BigDecimal frais = BigDecimal.valueOf(0);
        BigDecimal montantRestant = BigDecimal.valueOf(0);
        BigDecimal minimumDepot = BigDecimal.valueOf(0);
        BigDecimal initialDepot = BigDecimal.valueOf(0);
        BigDecimal nbrMonthPay = BigDecimal.valueOf(0);

        Classe classe = classeRepository.findById(inscription.getClasse().getId()).orElse(null);
        if (classe != null){
            minimumDepot = classe.getFraisInscription().add(classe.getAutesFrais()).add(classe.getMensualite());
            System.out.println("===== minimumDepot "+minimumDepot+"=======");
            if (prix != null && prix.compareTo(minimumDepot) < 0){
                System.out.println("initial "+inscription.getFraisInscriptions());
                throw new InscriptionException("Le montant est inferieur a la somme demandee !");
            }else{
                // ?! recuperer le montant a deposer
                initialDepot = inscription.getFraisInscriptions();
                System.out.println("===== initial "+initialDepot+"=======");
                // ! enlever le minimum deposit du montant initial a deposer
                montantRestant = initialDepot.subtract(minimumDepot);
                System.out.println("===== montantRestant "+montantRestant+"=======");
                // ! diviser le montantrestant par la mensualite a payer pour obtenir le nombre de mois a payer
                BigDecimal[] resultat = montantRestant.divideAndRemainder(classe.getMensualite());
                nbrMonthPay = resultat[0];
            //    BigDecimal rest = resultat[1].setScale(2 , RoundingMode.HALF_UP);
                // !paiment period 1 : novembre
                List<Payment> paymentList = new ArrayList<>();
                Payment firstPay = createPayment(inscription , classe.getMensualite() , classe.getMensualite() ,1);
                paymentList.add(firstPay);
                //***! parcourir le nbrMonthPay pour ajouter des paiements
                int nbr = nbrMonthPay.intValue();
                if (nbr > 0 ){
                    if (nbr < 10){
                        for (int i = 1; i <= nbr; i++) {
                            Payment p = createPayment(inscription , classe.getMensualite() , classe.getMensualite() ,i+1);
                            paymentList.add(p);
                        }
                    }else{
                        //# recupere le reste
                        throw new InscriptionException("Oups le montant donnee est superieur au montant paiement de l'annee");
                    }

                }
               if (nbr < 8){
                   BigDecimal soldeEtuPay = montantRestant.subtract(classe.getMensualite().multiply(nbrMonthPay));
                   if (soldeEtuPay.intValue() > 0){
                       Payment pWithSolde = createPayment(inscription , classe.getMensualite() , soldeEtuPay,nbr+2);
                       paymentList.add(pWithSolde);
                   }
                   inscription.setPayments(paymentList);
//                BigDecimal soldeEtuPay = montantRestant.subtract(classe.getMensualite().multiply(nbrMonthPay));
                   System.out.println("SOLDE ETUDINAT "+soldeEtuPay);
               }

            }
        }else {
            throw new InscriptionException("Classe invalide");
        }
    }

    public Payment createPayment(Inscription inscription, BigDecimal montant , BigDecimal montantVerser , int period){
        Payment payment = new Payment();
        payment.setMontant(montant);
        payment.setInscription(inscription);
        payment.setMontantVerse(montantVerser);
        payment.setMontanRestant(montant.subtract(montantVerser));
        Optional<Periode> periode = periodeRepository.findByNumero(period);

        if (periode.isPresent()){
            payment.setPeriode(periode.get());
            return payment;
        }else{
            throw new InscriptionException("Periode invalide");
        }

    }

    public void checkIfPaymentIsOk(Inscription inscription){
      if (inscription.getEtudiant().getId() != null){
          Etudiant etudiant = this.etudiantRepository.findById(inscription.getEtudiant().getId()).orElse(null);
          if (etudiant !=null ){
              System.out.println("ENTRE ICI");
              Inscription lastInscriptionEtu = this.inscriptionRepository.findLatestInscriptionByEtudiant(etudiant);
              BigDecimal LastYearAmountPay = lastInscriptionEtu.getPayments().stream().map(
                      Payment::getMontantVerse
              ).reduce(BigDecimal.ZERO,BigDecimal::add);
              BigDecimal lastfrais = lastInscriptionEtu.getClasse().getFraisInscription().add(lastInscriptionEtu.getClasse().getAutesFrais());
              LastYearAmountPay = LastYearAmountPay.add(lastfrais);
              System.out.println("LastYearAmountPay "+LastYearAmountPay);
              Classe classe = this.classeRepository.findById(inscription.getClasse().getId()).orElse(null);
              if (classe != null){
                  //?! multiplier la mensualite au nbr de mois qu'on a
                  BigDecimal restNbrMonth  = BigDecimal.valueOf(9).subtract(BigDecimal.valueOf(lastInscriptionEtu.getPayments().size()));
                  BigDecimal allFraisIns = classe.getFraisInscription().add(classe.getAutesFrais());
                  BigDecimal allAmountToPay = allFraisIns.add(classe.getMensualite().multiply(BigDecimal.valueOf(9)));
                  System.out.println("allAmountToPay "+allAmountToPay);
                  BigDecimal rest = allAmountToPay.subtract(LastYearAmountPay);
                  if (rest.intValue() != 0){
                      throw  new InscriptionException("Vous devez a l'ecole "+restNbrMonth +" mois de l'annee passee qu'est d'un montant de "+rest);
                  }
              }
          }
      }
    }

    public void checkIntervalInscription(Inscription inscription){
        Optional<Classe> classe = this.classeRepository.findById(inscription.getClasse().getId());
        if (classe.isPresent()){
            if (!Utils.isDateInRange(inscription.getDate() , classe.get().getDebutInscription() , classe.get().getFinInscription())){
                throw new InscriptionException("Les inscriptions sont cloturees !");
            }

        }
    }
}
