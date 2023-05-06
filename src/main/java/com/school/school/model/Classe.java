package com.school.school.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    private String libelle;
    private BigDecimal fraisInscription;
    private BigDecimal mensualite;
    private BigDecimal autesFrais;

    @ManyToOne()
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @JsonIgnore
    @OneToMany(mappedBy = "classe")
    private List<Inscription> inscriptions;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date debutInscription;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finInscription;

    public Date getDebutInscription() {
        return debutInscription;
    }

    public void setDebutInscription(Date debutInscription) {
        this.debutInscription = debutInscription;
    }

    public Date getFinInscription() {
        return finInscription;
    }

    public void setFinInscription(Date finInscription) {
        this.finInscription = finInscription;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getFraisInscription() {
        return fraisInscription;
    }

    public void setFraisInscription(BigDecimal fraisInscription) {
        this.fraisInscription = fraisInscription;
    }

    public BigDecimal getMensualite() {
        return mensualite;
    }

    public void setMensualite(BigDecimal mensualite) {
        this.mensualite = mensualite;
    }

    public BigDecimal getAutesFrais() {
        return autesFrais;
    }

    public void setAutesFrais(BigDecimal autesFrais) {
        this.autesFrais = autesFrais;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }
}
