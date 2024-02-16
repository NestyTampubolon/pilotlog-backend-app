package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.TrainingClass;

import java.util.List;

public interface TrainingClassService {
    TrainingClass addTraining(TrainingClass trainingClass);
    List<TrainingClass> getAllTrainingClass();
    TrainingClass getTrainingClassById(String id);
    TrainingClass updateTrainingClass(TrainingClass trainingClass, String id);
    TrainingClass deleteTrainingClass(TrainingClass trainingClass,String id);
}
