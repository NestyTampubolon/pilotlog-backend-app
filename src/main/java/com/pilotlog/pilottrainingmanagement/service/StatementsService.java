package com.pilotlog.pilottrainingmanagement.service;

import com.pilotlog.pilottrainingmanagement.model.Room;
import com.pilotlog.pilottrainingmanagement.model.Statements;

import java.util.List;

public interface StatementsService {
    Statements addStatements(Statements statements);
    List<Statements> getAllStatments();
    List<Statements> getAllStatmentsForTrainee();
    List<Statements> getAllStatmentsForInstructor();
    Statements updateStatements(Statements statements, Long id);
    Statements activationStatements(Statements statements, Long id);
    Statements deleteStatement(Statements statements, Long id);
}
