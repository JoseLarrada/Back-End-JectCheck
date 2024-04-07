package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Mapper.RoutesMapper;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RoutesServicesTest {
    @InjectMocks
    private RoutesServices routesServices;

    @Mock
    private  RoutesRepository routesRepository;
    @Mock
    private  IdGeneratorConfig generate;
    @Mock
    private  UploadContentService uploadContentService;
    @Mock
    private  RepositoryJDBC repositoryJDBC;
    @Mock
    private  RoutesMapper routesMapper;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testAddRoute() {
        // Configurar datos de prueba
        RoutesRequest request = new RoutesRequest("asd2w1", "Proyecto de prueba Ing",
                "Maribel", "JectCheck", "jose", "laura");
        Rutas ruta = new Rutas(5, "asd2w1", "Proyecto de prueba Ing",
                "Maribel", "124324", "JectCheck", "21433", "32647");
        HttpServletRequest http = null;

        // Configurar comportamiento simulado
        when(generate.IdGenerator()).thenReturn("asd2w1");
        when(routesRepository.existsById(request.getId())).thenReturn(false);

        // Llamar al método bajo prueba
        ResponseEntity<String> response = routesServices.addRoute(request, http);

        // Verificar el resultado esperado
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Proyecto Guardado Correctamente"));
    }


    @Test
    void finishProject() {
        String identification="12345";

        when(routesRepository.existsById(identification)).thenReturn(true);
        when(repositoryJDBC.getstatebyId(identification)).thenReturn(1);

        ResponseEntity<String> response=routesServices.finishProject(identification);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertTrue(response.getBody().contains("Proyecto finalizado Correctamente"));
    }

    @Test
    void finishProjectWithNonExistId(){
        String identification="12345";

        when(routesRepository.existsById(identification)).thenReturn(false);

        ResponseEntity<String> response=routesServices.finishProject(identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("El proyecto no existe"));
    }

    @Test
    void finishProjectWithProjectFinis(){
        String identification="12345";

        when(repositoryJDBC.getstatebyId(identification)).thenReturn(2);
        when(routesRepository.existsById(identification)).thenReturn(true);

        ResponseEntity<String> response=routesServices.finishProject(identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(response.getBody(),"El proyecto ya ha sido finalizado");
    }

    @Test
    void finishProjectWithNullId(){
        String identification=null;

        when(routesRepository.existsById(identification)).thenReturn(false);

        ResponseEntity<String> response=routesServices.finishProject(identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertTrue(response.getBody().contains("Ingrese un Valor valido"));
    }

    @Test
    void acceptProject() {
        String identification="12345";

        when(routesRepository.existsById(identification)).thenReturn(true);
        when(repositoryJDBC.getstatebyId(identification)).thenReturn(5);

        ResponseEntity<String> response=routesServices.acceptProject(identification);
        assertEquals(response.getBody(),"Proyecto aceptado");
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void acceptProjectWithNonExistId(){
        String identification="12345";

        when(routesRepository.existsById(identification)).thenReturn(false);

        ResponseEntity<String> response=routesServices.acceptProject(identification);

        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }

    @Test
    void acceptProjectWithProjectFinis(){
        String identification="12345";

        when(repositoryJDBC.getstatebyId(identification)).thenReturn(2);
        when(routesRepository.existsById(identification)).thenReturn(true);

        ResponseEntity<String> response=routesServices.acceptProject(identification);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        assertEquals(response.getBody(),"El proyecto ya ha sido aceptado");
    }
}