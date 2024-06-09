package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Request.ChangeRequest;
import com.Jetcheck.Aplication.DTo.Request.UpdateUserRequest;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Services.ProfileServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/PrincipalContent/profile")
public class ProfileController {
    private final ProfileServices profileServices;
    @PutMapping(value = "/ChangePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangeRequest changeRequest,HttpServletRequest request){
        try{
            return profileServices.changePassword(changeRequest,request);
        }catch (PersonExceptions e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping(value = "/UpdateUser")
    public ResponseEntity<String> updateuser(@RequestBody UpdateUserRequest UpdateRequest, HttpServletRequest request){
        try{
            return profileServices.updateUser(UpdateRequest,request);
        }catch (PersonExceptions e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/DeleteAccount/{Password}")
    public ResponseEntity<String> DeleteAccont(@PathVariable String Password, HttpServletRequest request){
        return profileServices.deleteUser(Password,request);
    }
}
