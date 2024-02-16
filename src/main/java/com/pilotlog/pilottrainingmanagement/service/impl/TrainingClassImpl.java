package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Company;
import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.repository.TrainingClassRepository;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingClassImpl implements TrainingClassService {
    private final TrainingClassRepository trainingClassRepository;

    @Override
    public TrainingClass addTraining(TrainingClass trainingClass) {
        TrainingClass trainingC = new TrainingClass();
        trainingC.setId_trainingclass(trainingClass.getId_trainingclass());
        trainingC.setName(trainingClass.getName());
        trainingC.setShort_name(trainingClass.getShort_name());
        trainingC.setRecurrent(trainingClass.getRecurrent());
        trainingC.setDescription(trainingClass.getDescription());
        trainingC.setIs_delete((byte) 0);
        trainingC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        trainingC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        trainingC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        trainingC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        trainingC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return trainingClassRepository.save(trainingC);
    }

    @Override
    public List<TrainingClass> getAllTrainingClass() {
        return trainingClassRepository.findAll();
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

        existingTraining.setIs_delete((byte) 1);
        trainingClassRepository.save(existingTraining);
        return existingTraining;
    }

}
