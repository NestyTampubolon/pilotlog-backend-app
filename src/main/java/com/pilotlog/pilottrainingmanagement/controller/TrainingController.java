package com.pilotlog.pilottrainingmanagement.controller;


import com.pilotlog.pilottrainingmanagement.model.TrainingClass;
import com.pilotlog.pilottrainingmanagement.model.Users;
import com.pilotlog.pilottrainingmanagement.service.TrainingClassService;
import com.pilotlog.pilottrainingmanagement.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingClassService trainingClassService;

    // menambah data training
    @PostMapping("admin/addTraining")
    public ResponseEntity<TrainingClass> addTrainingClass(@RequestBody TrainingClass trainingClass){
        return new ResponseEntity<>(trainingClassService.addTraining(trainingClass), HttpStatus.CREATED);
    }

    // mendapatkan semua data training
    @GetMapping("public/training")
    public List<TrainingClass> getAllTrainingClass(){
        return trainingClassService.getAllTrainingClass();
    }

    // mendapatkan data training berdasarkan id
    @GetMapping("admin/training/{id}")
    public ResponseEntity<TrainingClass> getTrainingById(@PathVariable("id") String idtraining){
        return new ResponseEntity<TrainingClass>(trainingClassService.getTrainingClassById(idtraining), HttpStatus.OK);
    }

    // mengubah data training berdasarkan id
    @PutMapping("admin/training/update/{id}")
    public ResponseEntity<TrainingClass> updateTraining(@PathVariable("id") String id,
                                             @RequestBody TrainingClass trainingClass){
        return new ResponseEntity<TrainingClass>(trainingClassService.updateTrainingClass(trainingClass,id), HttpStatus.OK);
    }

    // menghapus data training berdasarkan id
    @PutMapping("admin/training/delete/{id}")
    public ResponseEntity<TrainingClass> deleteTraining(@PathVariable("id") String id,
                                                        @RequestBody TrainingClass trainingClass){
        return new ResponseEntity<TrainingClass>(trainingClassService.deleteTrainingClass(trainingClass,id), HttpStatus.OK);
    }


    // mendapatkan semua data trainee yang dapat diakses oleh trainee
    @GetMapping("trainee/getAllTraining")
    public List<TrainingClass> getAllTrainingClassByIdCompany(){
        return trainingClassService.getAllTrainingClassByIdCompany();
    }

//    @PostMapping("admin/validto")
//    public ResponseEntity<String> getValidTo(@RequestBody Map<String, String> request) throws ParseException {
//        String shortName = request.get("shortName");
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(request.get("date"));
//
//        return ResponseEntity.ok(trainingClassService.getValueDateTrainingClass(shortName, date));
//    }


}
