package com.school.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private BigDecimal fraisInscriptions;

    @ManyToOne()
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "etudiant_id")
    private Etudiant etudiant;

    @ManyToOne()
    @JoinColumn(name = "annee_id")
    private Annee anneescolaire;

//    @JsonIgnore
    @OneToMany(mappedBy = "inscription" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<Payment> payments;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getFraisInscriptions() {
        return fraisInscriptions;
    }

    public void setFraisInscriptions(BigDecimal fraisInscriptions) {
        this.fraisInscriptions = fraisInscriptions;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Annee getAnneescolaire() {
        return anneescolaire;
    }

    public void setAnneescolaire(Annee anneescolaire) {
        this.anneescolaire = anneescolaire;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
