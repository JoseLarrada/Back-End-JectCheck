package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Repository.OtherRepository;
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
    private final OtherRepository otherRepository;
    @PostMapping(value = "/CreateRoute")
    public ResponseEntity<String> CreateRuta(@RequestBody RoutesRequest routesRequest, HttpServletRequest request){
        return routesServices.UpdateAddRuta(routesRequest,request);
    }
    @DeleteMapping(value = "/DeleteRoute/{name}")
    public ResponseEntity<String> DeleteRute(@PathVariable String name){
        try {
            otherRepository.DeleteFromName(name);
            return ResponseEntity.ok().body("Eliminado Correctamente");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PutMapping(value = "/UpdateRoute")
    public ResponseEntity<String> UpdateRuta(@RequestBody RoutesRequest routesRequest, HttpServletRequest request){
        return routesServices.UpdateAddRuta(routesRequest,request);
    }
    @PutMapping(value = "/AcceptProject/{idRoute}")
    public ResponseEntity<String> Accept(@PathVariable String idRoute){
        return routesServices.AcceptProject(idRoute);
    }
    @PutMapping(value = "/FinishProject/{idRoute}")
    public ResponseEntity<String>Finish(@PathVariable String idRoute){
        return routesServices.FinishProject(idRoute);
    }
    @GetMapping(value = "/FilterProject/{idState}")
    public ResponseEntity<List<Rutas>> FilterRoutes(@PathVariable String idState){
        return routesServices.filterRoutes(Integer.parseInt(idState));
    }
}
