package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Certificate;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.CertificateRepository;
import com.pilotlog.pilottrainingmanagement.repository.RoomRepository;
import com.pilotlog.pilottrainingmanagement.repository.UsersRepository;
import com.pilotlog.pilottrainingmanagement.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final UsersRepository usersRepository;

    // menambah data certificate
    @Override
    public Certificate addCertificate(Certificate certificate) {
        Certificate certificateC = new Certificate();
        certificateC.setNameColor(certificate.getNameColor());
        certificateC.setNameFontSize(certificate.getNameFontSize());
        certificateC.setNamePositionX(certificate.getNamePositionX());
        certificateC.setNamePositionY(certificate.getNamePositionY());
        certificateC.setNameWidth(certificate.getNameWidth());
        certificateC.setNameHeight(certificate.getNameHeight());
        certificateC.setNameTextAlign(certificate.getNameTextAlign());
        certificateC.setDateColor(certificate.getDateColor());
        certificateC.setDateFontSize(certificate.getDateFontSize());
        certificateC.setDatePositionX(certificate.getDatePositionX());
        certificateC.setDatePositionY(certificate.getDatePositionY());
        certificateC.setDateWidth(certificate.getDateWidth());
        certificateC.setDateHeight(certificate.getDateHeight());
        certificateC.setDateTextAlign(certificate.getDateTextAlign());
        certificateC.setTrainingColor(certificate.getTrainingColor());
        certificateC.setTrainingFontSize(certificate.getTrainingFontSize());
        certificateC.setTrainingPositionX(certificate.getTrainingPositionX());
        certificateC.setTrainingPositionY(certificate.getTrainingPositionY());
        certificateC.setTrainingWidth(certificate.getTrainingWidth());
        certificateC.setTrainingHeight(certificate.getTrainingHeight());
        certificateC.setTrainingTextAlign(certificate.getTrainingTextAlign());
        certificateC.setCompanyColor(certificate.getCompanyColor());
        certificateC.setCompanyFontSize(certificate.getCompanyFontSize());
        certificateC.setCompanyPositionX(certificate.getCompanyPositionX());
        certificateC.setCompanyPositionY(certificate.getCompanyPositionY());
        certificateC.setCompanyWidth(certificate.getCompanyWidth());
        certificateC.setCompanyHeight(certificate.getCompanyHeight());
        certificateC.setCompanyTextAlign(certificate.getCompanyTextAlign());
        certificateC.setLogoWidth(certificate.getLogoWidth());
        certificateC.setLogoHeight(certificate.getLogoHeight());
        certificateC.setLogoPositionX(certificate.getLogoPositionX());
        certificateC.setLogoPositionY(certificate.getLogoPositionY());
        certificateC.setSignatureWidth(certificate.getSignatureWidth());
        certificateC.setSignatureHeight(certificate.getSignatureHeight());
        certificateC.setSignaturePositionX(certificate.getSignaturePositionX());
        certificateC.setSignaturePositionY(certificate.getSignaturePositionY());
        certificateC.setCptsColor(certificate.getCptsColor());
        certificateC.setCptsFontSize(certificate.getCptsFontSize());
        certificateC.setCptsPositionX(certificate.getCptsPositionX());
        certificateC.setCptsPositionY(certificate.getCptsPositionY());
        certificateC.setCptsWidth(certificate.getCptsWidth());
        certificateC.setCptsHeight(certificate.getCptsHeight());
        certificateC.setCptsTextAlign(certificate.getCptsTextAlign());
        certificateC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        certificateC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        certificateC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        certificateC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        certificateC.setCompanyId(AuthenticationServiceImpl.getCompanyInfo());
        Users existingUsers = usersRepository.findById(certificate.getIdcpts().getId_users()).orElseThrow(
                () -> new ResourceNotFoundException("Users", "Id", certificate.getIdcpts().getId_users())
        );
        certificateC.setIdcpts(existingUsers);

        return certificateRepository.save(certificateC);
    }

    // mendapatkan data certificate berdasarkan id
    @Override
    public Certificate getCertificateById() {
        return certificateRepository.findCertificateByCompanyId(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }


    // mengubah data certificate
    @Override
    public Certificate updateCertificate(Certificate certificate, String id) {
        Certificate existingCertificate = certificateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Certificate", "Id", id)
        );

        existingCertificate.setNameColor(certificate.getNameColor());
        existingCertificate.setNameFontSize(certificate.getNameFontSize());
        existingCertificate.setNamePositionX(certificate.getNamePositionX());
        existingCertificate.setNamePositionY(certificate.getNamePositionY());
        existingCertificate.setNameWidth(certificate.getNameWidth());
        existingCertificate.setNameHeight(certificate.getNameHeight());
        existingCertificate.setNameTextAlign(certificate.getNameTextAlign());
        existingCertificate.setDateColor(certificate.getDateColor());
        existingCertificate.setDateFontSize(certificate.getDateFontSize());
        existingCertificate.setDatePositionX(certificate.getDatePositionX());
        existingCertificate.setDatePositionY(certificate.getDatePositionY());
        existingCertificate.setDateWidth(certificate.getDateWidth());
        existingCertificate.setDateHeight(certificate.getDateHeight());
        existingCertificate.setDateTextAlign(certificate.getDateTextAlign());
        existingCertificate.setTrainingColor(certificate.getTrainingColor());
        existingCertificate.setTrainingFontSize(certificate.getTrainingFontSize());
        existingCertificate.setTrainingPositionX(certificate.getTrainingPositionX());
        existingCertificate.setTrainingPositionY(certificate.getTrainingPositionY());
        existingCertificate.setTrainingWidth(certificate.getTrainingWidth());
        existingCertificate.setTrainingHeight(certificate.getTrainingHeight());
        existingCertificate.setTrainingTextAlign(certificate.getTrainingTextAlign());
        existingCertificate.setCompanyColor(certificate.getCompanyColor());
        existingCertificate.setCompanyFontSize(certificate.getCompanyFontSize());
        existingCertificate.setCompanyPositionX(certificate.getCompanyPositionX());
        existingCertificate.setCompanyPositionY(certificate.getCompanyPositionY());
        existingCertificate.setCompanyWidth(certificate.getCompanyWidth());
        existingCertificate.setCompanyHeight(certificate.getCompanyHeight());
        existingCertificate.setCompanyTextAlign(certificate.getCompanyTextAlign());
        existingCertificate.setLogoWidth(certificate.getLogoWidth());
        existingCertificate.setLogoHeight(certificate.getLogoHeight());
        existingCertificate.setLogoPositionX(certificate.getLogoPositionX());
        existingCertificate.setLogoPositionY(certificate.getLogoPositionY());
        existingCertificate.setSignatureWidth(certificate.getSignatureWidth());
        existingCertificate.setSignatureHeight(certificate.getSignatureHeight());
        existingCertificate.setSignaturePositionX(certificate.getSignaturePositionX());
        existingCertificate.setSignaturePositionY(certificate.getSignaturePositionY());
        existingCertificate.setCptsColor(certificate.getCptsColor());
        existingCertificate.setCptsFontSize(certificate.getCptsFontSize());
        existingCertificate.setCptsPositionX(certificate.getCptsPositionX());
        existingCertificate.setCptsPositionY(certificate.getCptsPositionY());
        existingCertificate.setCptsWidth(certificate.getCptsWidth());
        existingCertificate.setCptsHeight(certificate.getCptsHeight());
        existingCertificate.setCptsTextAlign(certificate.getCptsTextAlign());
        existingCertificate.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingCertificate.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        Users existingUsers = usersRepository.findById(certificate.getIdcpts().getId_users()).orElseThrow(
                () -> new ResourceNotFoundException("Users", "Id", certificate.getIdcpts().getId_users())
        );
        existingCertificate.setIdcpts(existingUsers);

        return certificateRepository.save(existingCertificate);
    }

    @Value("${certificate.directory}")
    private String certificateDirectory;

    // mengubah data gambar background certificate
    @Override
    public Certificate  updateBackgroundCertificate(MultipartFile backgroundImage, String id) {
        Certificate existingCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate", "Id", id));

        if (backgroundImage != null && !backgroundImage.isEmpty()) {
            try {
                String certificateFilename = saveCertificate(backgroundImage);
                System.out.println(certificateFilename);
                existingCertificate.setBackgroundImage(certificateFilename);
                return certificateRepository.save(existingCertificate);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save certificate background file.", e);
            }
        } else {
            throw new IllegalArgumentException("Certificate background is null or empty.");
        }
    }

    @Value("${cptssignature.directory}")
    private String cptssignatureDirectory;

    // mengubah data signature cpts oleh certificate
    @Override
    public Certificate updateSignatureCertificate(MultipartFile signature, String id) {
        Certificate existingCertificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate", "Id", id));

        if (signature != null && !signature.isEmpty()) {
            try {
                String certificateFilename = saveSignature(signature);
                existingCertificate.setSignature(certificateFilename);
                return certificateRepository.save(existingCertificate);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save signature file.", e);
            }
        } else {
            throw new IllegalArgumentException("Signature is null or empty.");
        }
    }

    // melihat data certificate
    @Override
    public boolean existsByCompany() {
        int count = certificateRepository.existsByCompanyId(AuthenticationServiceImpl.getCompanyInfo().getId_company());
        return count > 0;
    }


    // menyimpan data certificate
    private String saveCertificate(MultipartFile file) throws IOException {
        // Generate unique filename
        String filename = UUID.randomUUID().toString() + ".png";
        // Simpan file ke folder assets
        Files.copy(file.getInputStream(), Paths.get(certificateDirectory + filename), StandardCopyOption.REPLACE_EXISTING);
        // Kembalikan alamat file gambar
        return filename;
    }


    private String saveSignature(MultipartFile file) throws IOException {
        // Generate unique filename
        String filename = UUID.randomUUID().toString() + ".png";
        // Simpan file ke folder assets
        Files.copy(file.getInputStream(), Paths.get(cptssignatureDirectory + filename), StandardCopyOption.REPLACE_EXISTING);
        // Kembalikan alamat file gambar
        return filename;
    }


}
