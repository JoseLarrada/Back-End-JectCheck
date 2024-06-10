package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.Request.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Mapper.AdvanceMapper;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AdvanceServiceTest {

    @InjectMocks
    private AdvanceService advanceService;
    @Mock
    private  RoutesRepository routesRepository;
    @Mock
    private  IdGeneratorConfig Generate;
    @Mock
    private  AdvanceRepository advanceRepository;
    @Mock
    private  OtherRepository advance;
    @Mock
    private  AdvanceMapper advanceMapper;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createAdvance() {
        AdvanceRequest request = new AdvanceRequest("Proyecto1","proyecto de pruebas unitarias",
                null,"12345","56789");
        Avances avance = new Avances(5,new Date(),"12345","56789",
                "proyecto de pruebas unitarias","Pruebas",5.0);
        Avances avanceCreado = new Avances(5,new Date(),"12345","56789",
                "proyecto de pruebas unitarias","Pruebas",0.0);

        when(routesRepository.existsById(request.getRouteId())).thenReturn(true);
        when(advanceMapper.mapperAdvance(request)).thenReturn(avance);
        when(advanceRepository.save(avanceCreado)).thenReturn(avanceCreado);

        ResponseEntity<String> response = advanceService.createAdvance(request);
        String spectedMessage="Creado Correctamente";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains(spectedMessage));
    }

    @Test
    void createAdvanceWhithNullTittle() {
        AdvanceRequest request = new AdvanceRequest(null,"proyecto de pruebas unitarias",
                null,"12345","56789");

        ResponseEntity<String> response = advanceService.createAdvance(request);

        String spectedMessage="Añada un titulo al avance";

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains(spectedMessage));
    }

    @Test
    void createAdvanceWhithNullDescripcion() {
        AdvanceRequest request = new AdvanceRequest("Proyecto1",null,
                null,"12345","56789");

        ResponseEntity<String> response = advanceService.createAdvance(request);

        String spectedMessage="Añada una descripcion al avance";

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains(spectedMessage));
    }

    @Test
    void createAdvanceNonExistentRoute(){
        AdvanceRequest request = new AdvanceRequest("Proyecto1","proyecto de pruebas unitarias",
                null,"12345","56789");

        when(routesRepository.existsById(request.getRouteId())).thenReturn(false);

        Exception response = assertThrows(PersonExceptions.class, ()->{
            advanceService.createAdvance(request);
        });

        String spectedMessage="Ruta inexistente";

        assertTrue(response.getMessage().contains(spectedMessage));
    }

    @Test
    void deleteAvance() {
        String identification="12345";

        when(advanceRepository.existsById(identification)).thenReturn(true);

        ResponseEntity<String> response=advanceService.deleteAvance("Advance1",identification);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.getBody().contains("Eliminado Correctamente"));
    }

    @Test
    void deleteAvanceWithNonExistentId() {
        String identification="123456";

        when(advanceRepository.existsById(identification)).thenReturn(false);

        ResponseEntity<String> response=advanceService.deleteAvance("Advance2",identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("No existe un avance asociado"));
    }

    @Test
    void deleteAvanceWithNullId() {
        String identification=null;

        ResponseEntity<String> response=advanceService.deleteAvance("Advance",identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("Añada un id valido"));
    }
}