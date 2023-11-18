package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Services.RoutesServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/PrincipalContent")
public class RoutesController {
    private final RoutesServices routesServices;
    @PostMapping(value = "/CreateRoute")
    public ResponseEntity<String> CreateRuta(@RequestBody RoutesRequest routesRequest, HttpServletRequest request){
        return routesServices.UpdateAddRuta(routesRequest,request);
    }
    @DeleteMapping(value = "/DeleteRote")
    public ResponseEntity<String> DeleteRute(String id){
        return routesServices.DeleteRuta(id);
    }
    @PutMapping(value = "UpdateRoute")
    public ResponseEntity<String> UpdateRuta(@RequestBody RoutesRequest routesRequest, HttpServletRequest request){
        return routesServices.UpdateAddRuta(routesRequest,request);
    }
    @PutMapping(value = "AcceptProject")
    public ResponseEntity<String> Accept(String id_Route){
        return routesServices.AcceptProject(id_Route);
    }
    @PutMapping(value = "FinishProject")
    public ResponseEntity<String>Finish(String id_Route){
        return routesServices.FinishProject(id_Route);
    }
    @GetMapping(value = "FilterProject")
    public ResponseEntity<List<Rutas>> FilterRoutes(int id_state){
        return routesServices.filterRoutes(id_state);
    }
}
