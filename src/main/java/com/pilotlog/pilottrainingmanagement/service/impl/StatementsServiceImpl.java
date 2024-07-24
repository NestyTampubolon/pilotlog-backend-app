package com.pilotlog.pilottrainingmanagement.service.impl;

import com.pilotlog.pilottrainingmanagement.exception.ResourceNotFoundException;
import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Statements;
import com.pilotlog.pilottrainingmanagement.repository.RoomRepository;
import com.pilotlog.pilottrainingmanagement.repository.StatementsRepository;
import com.pilotlog.pilottrainingmanagement.service.StatementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementsServiceImpl implements StatementsService {
    private final StatementsRepository statementsRepository;

    // menambah data statement
    @Override
    public Statements addStatements(Statements statements) {
        Statements statementsC = new Statements();
        statementsC.setContent(statements.getContent());
        statementsC.setStatementType(statements.getStatementType());
        statementsC.set_delete(false);
        statementsC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        statementsC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        statementsC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        statementsC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        statementsC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return statementsRepository.save(statementsC);
    }

    // mendapatkan data semua statement
    @Override
    public List<Statements> getAllStatments() {
        return statementsRepository.findAllByCompanyIdAndIsDeleteIsZero(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    // mendapatkan data semua statemnt yang dilakukan oleh instructor
    @Override
    public List<Statements> getAllStatmentsForTrainee() {
        return statementsRepository.findAllStatementsForTrainee(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    // mendapatkan semua data statement yang dilakukan oleh trainee
    @Override
    public List<Statements> getAllStatmentsForInstructor() {
        return statementsRepository.findAllStatementsForInstructor(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    // mengubah data statement
    @Override
    public Statements updateStatements(Statements statements, Long id) {
        Statements existingStatement = statementsRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Statement", "Id", id)
        );

        existingStatement.setContent(statements.getContent());
        existingStatement.setStatementType(statements.getStatementType());
        existingStatement.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingStatement.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        statementsRepository.save(existingStatement);

        return existingStatement;
    }

    // melakukan aktivasi statement
    @Override
    public Statements activationStatements(Statements statements, Long id) {
        Statements existingStatement = statementsRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Statement", "Id", id)
        );

        if(!existingStatement.is_active()){
            existingStatement.set_active(true);
        } else {
            existingStatement.set_active(false);
        }
        existingStatement.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingStatement.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        statementsRepository.save(existingStatement);

        return existingStatement;
    }

    // menghapus data statement
    @Override
    public Statements deleteStatement(Statements statements, Long id) {
        Statements existingStatement = statementsRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Statement", "Id", id)
        );

        existingStatement.set_delete(true);
        statementsRepository.save(existingStatement);
        return existingStatement;
    }
}
