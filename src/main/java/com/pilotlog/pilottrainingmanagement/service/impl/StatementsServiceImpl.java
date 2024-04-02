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

    @Override
    public Statements addStatements(Statements statements) {
        Statements statementsC = new Statements();
        statementsC.setContent(statements.getContent());
        statementsC.setStatementType(statements.getStatementType());
        statementsC.setIs_delete((byte) 0);
        statementsC.setId_company(AuthenticationServiceImpl.getCompanyInfo());
        statementsC.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        statementsC.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        statementsC.setCreated_by(AuthenticationServiceImpl.getUserInfo());
        statementsC.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        return statementsRepository.save(statementsC);
    }

    @Override
    public List<Statements> getAllStatments() {
        return statementsRepository.findAllByCompanyIdAndIsDeleteIsZero(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    @Override
    public List<Statements> getAllStatmentsForTrainee() {
        return statementsRepository.findAllStatementsForTrainee(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

    @Override
    public List<Statements> getAllStatmentsForInstructor() {
        return statementsRepository.findAllStatementsForInstructor(AuthenticationServiceImpl.getCompanyInfo().getId_company());
    }

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

    @Override
    public Statements activationStatements(Statements statements, Long id) {
        Statements existingStatement = statementsRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Statement", "Id", id)
        );

        if(existingStatement.getIs_active() == 0){
            existingStatement.setIs_active((byte) 1);
        } else if (existingStatement.getIs_active() == 1) {
            existingStatement.setIs_active((byte) 0);
        }
        existingStatement.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        existingStatement.setUpdated_by(AuthenticationServiceImpl.getUserInfo());
        statementsRepository.save(existingStatement);

        return existingStatement;
    }

    @Override
    public Statements deleteStatement(Statements statements, Long id) {
        Statements existingStatement = statementsRepository.findById(String.valueOf(id)).orElseThrow(
                () -> new ResourceNotFoundException("Statement", "Id", id)
        );

        existingStatement.setIs_delete((byte) 1);
        statementsRepository.save(existingStatement);
        return existingStatement;
    }
}
