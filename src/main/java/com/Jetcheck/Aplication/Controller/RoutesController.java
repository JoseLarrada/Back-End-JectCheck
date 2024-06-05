package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.RoutesRequest;
import com.Jetcheck.Aplication.DTo.RoutesResponse;
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
    @PostMapping(value = "/CreateRoute")
    public ResponseEntity<String> createRuta(@RequestBody RoutesRequest routesRequest, HttpServletRequest request){
        return routesServices.addRoute(routesRequest,request);
    }
    @DeleteMapping(value = "/DeleteRoute/{name}/{id}")
    public ResponseEntity<String> deleteRute(@PathVariable String name,@PathVariable String id){
        return routesServices.disableRoute(name,id);
    }
    @PutMapping(value = "/UpdateRoute")
    public ResponseEntity<String> updateRuta(@RequestBody RoutesRequest routesRequest, HttpServletRequest request){
        return routesServices.updateRoute(routesRequest,request);
    }
    @PutMapping(value = "/AcceptProject/{idRoute}")
    public ResponseEntity<String> accept(@PathVariable String idRoute){
        return routesServices.acceptProject(idRoute);
    }
    @PutMapping(value = "/FinishProject/{idRoute}")
    public ResponseEntity<String> finish(@PathVariable String idRoute){
        return routesServices.finishProject(idRoute);
    }
    @PutMapping(value = "/RejectProject/{idRoute}")
    public ResponseEntity<String> reject(@PathVariable String idRoute){
        return routesServices.rejectProject(idRoute);
    }
    @GetMapping(value = "/getProject/{id}")
    public ResponseEntity<RoutesResponse> getRoute(@PathVariable String id){
        return routesServices.findRoutesById(id);
    }

}
