package com.school.school.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;


@Builder
public class ClasseDTO {

    private UUID id;
    private String libelle;
    private BigDecimal fraisInscription;
    private BigDecimal mensualite;
    private BigDecimal autesFrais;

    private UUID filiere;
    private String debutInscription;
    private String finInscription;

    public ClasseDTO(){}
    public ClasseDTO(UUID id, String libelle, BigDecimal fraisInscription, BigDecimal mensualite, BigDecimal autesFrais, UUID filiere, String debutInscription, String finInscription) {
        this.id = id;
        this.libelle = libelle;
        this.fraisInscription = fraisInscription;
        this.mensualite = mensualite;
        this.autesFrais = autesFrais;
        this.filiere = filiere;
        this.debutInscription = debutInscription;
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

    public UUID getFiliere() {
        return filiere;
    }

    public void setFiliere(UUID filiere) {
        this.filiere = filiere;
    }

    public String getDebutInscription() {
        return debutInscription;
    }

    public void setDebutInscription(String debutInscription) {
        this.debutInscription = debutInscription;
    }

    public String getFinInscription() {
        return finInscription;
    }

    public void setFinInscription(String finInscription) {
        this.finInscription = finInscription;
    }
}
