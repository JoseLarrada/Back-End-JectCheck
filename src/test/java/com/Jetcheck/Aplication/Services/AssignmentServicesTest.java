package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.AssignmentRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Mapper.AssignmentMapper;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AssignmentServicesTest {

    @InjectMocks
    private AssignmentServices assignmentServices;
    @Mock
    private  AssignmentRepository assignmentRepository;
    @Mock
    private  AdvanceRepository advanceRepository;
    @Mock
    private  OtherRepository assignmentJDBC;
    @Mock
    private  IdGeneratorConfig idGeneratorConfig;
    @Mock
    private  AssignmentMapper assignmentMapper;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void addAssignment() {
        AssignmentRequest request =new AssignmentRequest("11111","rutaanexo1",
                "rutaFile1", null,"124s2");
        Entregas entregas = new Entregas(0.0,"11111","rutaanexo1",
                "rutaFile1",null,"124s2");

        when(advanceRepository.existsById(request.getIdAdvance())).thenReturn(true);
        when(assignmentMapper.mapperAssignment(request)).thenReturn(entregas);
        when(assignmentRepository.save(entregas)).thenReturn(entregas);

        ResponseEntity<String> response = assignmentServices.addAssignment(request);

        String spectedMessage= "Entrega correcta";

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.getBody().contains(spectedMessage));
    }

    @Test
    void addAssignmentWithNullFile(){
        AssignmentRequest request =new AssignmentRequest("11111","rutaanexo1",
                null,null,"124s2");

        ResponseEntity<String> response = assignmentServices.addAssignment(request);

        String spectedMessage= "Ingrese un archivo valido";

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains(spectedMessage));
    }

    @Test
    void addAssignmentWithNonExistAdvance(){
        AssignmentRequest request =new AssignmentRequest("11111","rutaanexo1",
                "rutafile1",null,"124s2");

        when(advanceRepository.existsById(request.getIdAdvance())).thenReturn(false);

        ResponseEntity<String> response = assignmentServices.addAssignment(request);

        String spectedMessage= "No existe el Avance";

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains(spectedMessage));
    }

    //Test Unitarios al metodo Eliminar
    @Test
    void deleteAssignment() {
        String identification="12345";

        when(assignmentRepository.existsById(identification)).thenReturn(true);

        ResponseEntity<String> response=assignmentServices.deleteAssignment(identification);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.getBody().contains("Eliminado Correctamente"));
    }

    @Test
    void deleteAssignmentWithNonExistentId() {
        String identification="123456";

        when(assignmentRepository.existsById(identification)).thenReturn(false);

        ResponseEntity<String> response=assignmentServices.deleteAssignment(identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("No se encontro Entrega"));
    }

    @Test
    void deleteAssignmentWithNullId() {
        String identification=null;

        ResponseEntity<String> response=assignmentServices.deleteAssignment(identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("Ingrese un valor"));
    }

    //Test Unitarios al metodo Calificar

    @Test
    void rateAssignment() {
       String idAssignment="12dasd2";
       double rate=3.5;

       when(assignmentRepository.existsById(idAssignment)).thenReturn(true);

        ResponseEntity<String> response=assignmentServices.rateAssignment(rate,idAssignment);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.getBody().contains("Calificado Correctamente"));
    }

    @Test
    void rateAssignmentWithNonExistId() {
        String idAssignment="12dasd2";
        double rate=3.5;

        when(assignmentRepository.existsById(idAssignment)).thenReturn(false);

        ResponseEntity<String> response=assignmentServices.rateAssignment(rate,idAssignment);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("No se Encontro el Id"));
    }
    
    @Test
    void rateAssignmentWithNullId(){
        String idAssignment=null;
        double rate=3.5;

        ResponseEntity<String> response=assignmentServices.rateAssignment(rate,idAssignment);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("Ingrese un valor"));
    }

    @Test
    void rateAssignmentWithRateHigh(){
        String idAssignment="12dasd2";
        double rate=5.4;

        ResponseEntity<String> response=assignmentServices.rateAssignment(rate,idAssignment);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("La nota maxima es de 5.0"));
    }

    @Test
    void rateAssignmentWithRateLow(){
        String idAssignment="12dasd2";
        double rate=-1;

        ResponseEntity<String> response=assignmentServices.rateAssignment(rate,idAssignment);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("La nota minma es de 0"));
    }
}