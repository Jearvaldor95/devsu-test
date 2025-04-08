package com.devsu.account_service.adapter.controller;

import com.devsu.account_service.adapter.dto.request.MovementRequest;
import com.devsu.account_service.adapter.dto.response.MovementResponse;
import com.devsu.account_service.adapter.mapper.MovementRequestMapper;
import com.devsu.account_service.application.service.MovementUseCase;
import com.devsu.account_service.domain.model.Movement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@Tag(name = "Movement Controller")
public class MovementController {

    private final MovementUseCase movementUseCase;
    private final MovementRequestMapper movementRequestMapper;

    public MovementController(MovementUseCase movementUseCase, MovementRequestMapper movementRequestMapper){
        this.movementUseCase = movementUseCase;
        this.movementRequestMapper = movementRequestMapper;
    }


    @PostMapping
    @Operation(summary = "Save movement")
    public ResponseEntity<MovementResponse> saveMovement(@Valid @RequestBody MovementRequest movementRequest){
        Movement movement = movementRequestMapper.toMovement(movementRequest);
        MovementResponse response = MovementResponse.of(movementUseCase.saveMovement(movement));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all movements")
    public ResponseEntity<List<MovementResponse>> getMovements(){
        List<MovementResponse> movements = MovementResponse.movementResponses(movementUseCase.getMovements());
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }

    @PutMapping("/{movementId}")
    @Operation(summary = "Update movement")
    public ResponseEntity<MovementResponse> saveMovement(@PathVariable Integer movementId, @RequestBody MovementRequest movementRequest){
        Movement movement = movementRequestMapper.toMovement(movementRequest);
        MovementResponse response = MovementResponse.of(movementUseCase.updateMovement(movementId, movement));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Get Movements by accountId")
    public ResponseEntity<List<MovementResponse>> getMovementByAccountId(@PathVariable Integer accountId){
        List<MovementResponse> response = MovementResponse.movementResponses(movementUseCase.findByAccountId(accountId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
