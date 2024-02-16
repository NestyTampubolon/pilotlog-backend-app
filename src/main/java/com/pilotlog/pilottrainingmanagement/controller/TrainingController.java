package com.pilotlog.pilottrainingmanagement.controller;


import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingClassService trainingClassService;

    // build create Users
    @PostMapping("admin/addTraining")
    public ResponseEntity<TrainingClass> addTrainingClass(@RequestBody TrainingClass trainingClass){
        return new ResponseEntity<>(trainingClassService.addTraining(trainingClass), HttpStatus.CREATED);
    }

    @GetMapping("admin/training")
    public List<TrainingClass> getAllTrainingClass(){
        return trainingClassService.getAllTrainingClass();
    }

    @GetMapping("admin/training/{id}")
    public ResponseEntity<TrainingClass> getTrainingById(@PathVariable("id") String idtraining){
        return new ResponseEntity<TrainingClass>(trainingClassService.getTrainingClassById(idtraining), HttpStatus.OK);
    }

    @PutMapping("admin/training/update/{id}")
    public ResponseEntity<TrainingClass> updateTraining(@PathVariable("id") String id,
                                             @RequestBody TrainingClass trainingClass){
        return new ResponseEntity<TrainingClass>(trainingClassService.updateTrainingClass(trainingClass,id), HttpStatus.OK);
    }

    @PutMapping("admin/training/delete/{id}")
    public ResponseEntity<TrainingClass> deleteTraining(@PathVariable("id") String id,
                                                        @RequestBody TrainingClass trainingClass){
        return new ResponseEntity<TrainingClass>(trainingClassService.deleteTrainingClass(trainingClass,id), HttpStatus.OK);
    }



}
