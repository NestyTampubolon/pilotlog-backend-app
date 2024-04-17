package com.pilotlog.pilottrainingmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "certificate")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_certificate;

    @Column(name = "backgroundImage")
    private String backgroundImage;

    @Column(name = "nameText")
    private String nameText;

    @Column(name = "nameColor")
    private String nameColor;

    @Column(name = "nameFontSize")
    private int nameFontSize;

    @Column(name = "namePositionX")
    private int namePositionX;

    @Column(name = "namePositionY")
    private int namePositionY;

    @Column(name = "nameWidth")
    private int nameWidth;

    @Column(name = "nameHeight")
    private int nameHeight;

    @Column(name = "nameTextAlign")
    private String nameTextAlign;

    @Column(name = "dateText")
    private String dateText;

    @Column(name = "dateColor")
    private String dateColor;

    @Column(name = "dateFontSize")
    private int dateFontSize;

    @Column(name = "datePositionX")
    private int datePositionX;

    @Column(name = "datePositionY")
    private int datePositionY;

    @Column(name = "dateWidth")
    private int dateWidth;

    @Column(name = "dateHeight")
    private int dateHeight;

    @Column(name = "dateTextAlign")
    private String dateTextAlign;

    @Column(name = "trainingText")
    private String trainingText;

    @Column(name = "trainingColor")
    private String trainingColor;

    @Column(name = "trainingFontSize")
    private int trainingFontSize;

    @Column(name = "trainingPositionX")
    private int trainingPositionX;

    @Column(name = "trainingPositionY")
    private int trainingPositionY;

    @Column(name = "trainingWidth")
    private int trainingWidth;

    @Column(name = "trainingHeight")
    private int trainingHeight;

    @Column(name = "trainingTextAlign")
    private String trainingTextAlign;

    @Column(name = "companyText")
    private String companyText;

    @Column(name = "companyColor")
    private String companyColor;

    @Column(name = "companyFontSize")
    private int companyFontSize;

    @Column(name = "companyPositionX")
    private int companyPositionX;

    @Column(name = "companyPositionY")
    private int companyPositionY;

    @Column(name = "companyWidth")
    private int companyWidth;

    @Column(name = "companyHeight")
    private int companyHeight;

    @Column(name = "companyTextAlign")
    private String companyTextAlign;

    @Column(name = "logoWidth")
    private int logoWidth;

    @Column(name = "logoHeight")
    private int logoHeight;

    @Column(name = "logoPositionX")
    private int logoPositionX;

    @Column(name = "logoPositionY")
    private int logoPositionY;

    @Column(name = "signature", nullable = true)
    private String signature;

    @Column(name = "signatureWidth")
    private int signatureWidth;

    @Column(name = "signatureHeight")
    private int signatureHeight;

    @Column(name = "signaturePositionX")
    private int signaturePositionX;

    @Column(name = "signaturePositionY")
    private int signaturePositionY;

    @Column(name = "cptsColor")
    private String cptsColor;

    @Column(name = "cptsFontSize")
    private int cptsFontSize;

    @Column(name = "cptsPositionX")
    private int cptsPositionX;

    @Column(name = "cptsPositionY")
    private int cptsPositionY;

    @Column(name = "cptsWidth")
    private int cptsWidth;

    @Column(name = "cptsHeight")
    private int cptsHeight;

    @Column(name = "cptsTextAlign")
    private String cptsTextAlign;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "created_by", nullable = false)
    private String created_by;

    @Column(name = "updated_by", nullable = false)
    private String updated_by;

    @ManyToOne
    @JoinColumn(name="id_company")
    private Company companyId;

    @ManyToOne
    @JoinColumn(name="id_cpts")
    private Users idcpts;
}
