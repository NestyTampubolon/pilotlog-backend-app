package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.repository.TrainingClassRepository;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TrainingClassImpl implements TrainingClassService {
    private final TrainingClassRepository trainingClassRepository;

    @Override
    public TrainingClass addTraining(TrainingClass trainingClass) {
        Company idCompany = AuthenticationServiceImpl.getCompanyInfo();
        String lastId = trainingClassRepository.findLastIdByIdCompany(idCompany.getId_company());

        // Jika tidak ada id sebelumnya, gunakan 1 sebagai angka berikutnya, jika tidak gunakan id sebelumnya ditambah 1
        int nextIdNumber = (lastId != null) ? Integer.parseInt(lastId.split(idCompany.getId_company())[1]) + 1 : 1;
        String idTrainingClass = idCompany.getId_company() + nextIdNumber;


        // Membuat objek TrainingClass baru
        TrainingClass trainingC = new TrainingClass();
        trainingC.setId_trainingclass(idTrainingClass);
        trainingC.setName(trainingClass.getName());
        trainingC.setShort_name(trainingClass.getShort_name());
        trainingC.setRecurrent(trainingClass.getRecurrent());
        trainingC.setDescription(trainingClass.getDescription());
        trainingC.setDelete(false);
        trainingC.setId_company(idCompany);
        trainingC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        trainingC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        trainingC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        trainingC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());

        return trainingClassRepository.save(trainingC);
    }

    @Override
    public List<TrainingClass> getAllTrainingClass() {
        return trainingClassRepository.findAllByIdCompany(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    @Override
    public TrainingClass getTrainingClassById(String id) {
        return  trainingClassRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Training", "Id", id));
    }

    @Override
    public TrainingClass updateTrainingClass(TrainingClass trainingClass, String id) {

        TrainingClass existingTraining = trainingClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Training Class", "Id", id)
        );

        existingTraining.setName(trainingClass.getName());
        existingTraining.setShort_name(trainingClass.getShort_name());
        existingTraining.setRecurrent(trainingClass.getRecurrent());
        existingTraining.setDescription(trainingClass.getDescription());
        existingTraining.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingTraining.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        trainingClassRepository.save(existingTraining);

        return existingTraining;
    }

    @Override
    public TrainingClass deleteTrainingClass(TrainingClass trainingClass, String id) {

        TrainingClass existingTraining = trainingClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Training Class", "Id", id)
        );

        existingTraining.setDelete(true);
        trainingClassRepository.save(existingTraining);
        return existingTraining;
    }

    @Override
    public String getValueDateTrainingClass(String idTrainingClass, Date date) {
        String companyId = AuthenticationServiceImpl.getCompanyInfo().getId_company();
        List<TrainingClass> existingTraining = trainingClassRepository.findByIdTrainingClass(idTrainingClass, companyId);
        if (!existingTraining.isEmpty()) {
            TrainingClass firstTraining = existingTraining.get(0);

            TrainingClass trainingClass = existingTraining.get(0);
            String recurrent = trainingClass.getRecurrent();
            Date validTo;

            System.out.println("valid to");
            System.out.println(recurrent);
            if (Objects.equals(recurrent, "Initial")) {
                return null;
            } else if (Objects.equals(recurrent, "6 Month") ||
                    Objects.equals(recurrent, "12 Month") ||
                    Objects.equals(recurrent, "24 Month") ||
                    Objects.equals(recurrent, "36 Month")) {
                System.out.println(recurrent);
                int monthsToAdd;
                switch (recurrent) {
                    case "6 Month":
                        monthsToAdd = 6;
                        break;
                    case "12 Month":
                        monthsToAdd = 12;
                        break;
                    case "24 Month":
                        monthsToAdd = 24;
                        break;
                    case "36 Month":
                        monthsToAdd = 36;
                        break;
                    default:
                        monthsToAdd = 0; // Default to 0 if the recurrent value is unexpected
                        break;
                }

                // Tambahkan bulan ke waktu saat ini
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.MONTH, monthsToAdd);

                // Set tanggal ke tanggal terakhir dari bulan yang baru
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

                // Dapatkan tanggal yang telah diset
                validTo = cal.getTime();

                // Format tanggal ke dalam format yang diinginkan (string)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(validTo);
            } else if (Objects.equals(recurrent, "Last Month of The Next Year on The Past Training")) {
                // Ambil tahun berikutnya
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.YEAR, 1);
                int nextYear = cal.get(Calendar.YEAR);

                // Atur tanggalnya menjadi tanggal terakhir dari bulan Desember di tahun berikutnya
                cal.set(nextYear, Calendar.DECEMBER, 1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

                validTo = cal.getTime();

                // Format tanggal ke dalam format yang diinginkan (string)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(validTo);
            }
        }

        return null;
    }

    @Override
    public List<TrainingClass> getAllTrainingClassByIdCompany() {
        String companyId = AuthenticationServiceImpl.getCompanyInfo().getId_company();
        List<TrainingClass> existingTraining = trainingClassRepository.findTrainingClassByIdCompany(companyId);
        return existingTraining;
    }
}
