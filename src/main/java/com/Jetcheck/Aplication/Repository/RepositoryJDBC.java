package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class RepositoryJDBC {
    private final JdbcTemplate jdbcTemplate;

    public void updatePassword(Usuarios request){
        String sql="UPDATE usuarios SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getPassword(), request.getId());
    }

    public void ChangePassword(Usuarios request){
        String sql="UPDATE usuarios SET password = ? WHERE username = ?";
        jdbcTemplate.update(sql, request.getPassword(), request.getUsername());
    }

    public List<Rutas> GetRoutesById_Student(String username){
        String sql = "SELECT * FROM rutas WHERE id_estudiante = ? " +
                "UNION SELECT * FROM rutas WHERE id_integrante = ? " +
                "UNION SELECT * FROM rutas WHERE id_integrante2 = ?";
        List<Rutas> routes = jdbcTemplate.query(sql, new Object[] { username, username, username } ,(resultSet, rowNum) -> {
            Rutas ruta = new Rutas();
            ruta.setNombre(resultSet.getString("nombre"));
            ruta.setId_docente(resultSet.getString("id_docente"));
            ruta.setId_estudiante(resultSet.getString("id_estudiante"));
            ruta.setId_estado(resultSet.getInt("id_estado"));
            ruta.setDescripcion(resultSet.getString("descripcion"));
            ruta.setId_ruta(resultSet.getString("id_ruta"));
            ruta.setId_integrante(resultSet.getString("id_integrante"));
            ruta.setId_integrante2(resultSet.getString("id_integrante2"));
            return ruta;
        });
        return routes;
    }

    public List<Rutas>findById_estado(int id_estado){
        String sql="SELECT * FROM rutas WHERE id_estado= ?";
        List<Rutas> routes = jdbcTemplate.query(sql, new Object[] { id_estado } ,(resultSet, rowNum) -> {
            Rutas ruta = new Rutas();
            ruta.setNombre(resultSet.getString("nombre"));
            ruta.setId_docente(resultSet.getString("id_docente"));
            ruta.setId_estudiante(resultSet.getString("id_estudiante"));
            ruta.setId_estado(resultSet.getInt("id_estado"));
            ruta.setDescripcion(resultSet.getString("descripcion"));
            ruta.setId_ruta(resultSet.getString("id_ruta"));
            ruta.setId_integrante(resultSet.getString("id_integrante"));
            ruta.setId_integrante2(resultSet.getString("id_integrante2"));
            return ruta;
        });
        return routes;
    }
    public List<Rutas> GetRoutesByUsername(String username){
        String sql = "SELECT * FROM rutas " +
                "WHERE id_docente =(SELECT id_docente FROM profesores " +
                "WHERE id_usuario=(SELECT id FROM usuarios WHERE username= ?))";
        List<Rutas> routes = jdbcTemplate.query(sql, new Object[] { username } ,(resultSet, rowNum) -> {
            Rutas ruta = new Rutas();
            ruta.setNombre(resultSet.getString("nombre"));
            ruta.setId_docente(resultSet.getString("id_docente"));
            ruta.setId_estudiante(resultSet.getString("id_estudiante"));
            ruta.setId_estado(resultSet.getInt("id_estado"));
            ruta.setDescripcion(resultSet.getString("descripcion"));
            ruta.setId_ruta(resultSet.getString("id_ruta"));
            ruta.setId_integrante(resultSet.getString("id_integrante"));
            ruta.setId_integrante2(resultSet.getString("id_integrante2"));
            return ruta;
        });
        return routes;
    }

    public String getPasswordByUsername(String username){
        String sql = "SELECT password FROM usuarios WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "El usuario no existe";
        }
    }
    public void updateUser(Usuarios request){

        try {
            String sql = "UPDATE usuarios " +
                    "SET nombres = ?, apellidos = ?, ciudad = ?, correo = ? " +
                    "WHERE username = ?";

            jdbcTemplate.update(sql,request.getNombres(),request.getApellidos(),request.getCiudad(),
                    request.getCorreo(),request.getUsername());
        } catch (EmptyResultDataAccessException e) {
            throw new PersonExceptions("No se ha podido completar la sentencia");
        }
    }
    public String getIddByUsername(String username){
        String sql = "SELECT id FROM usuarios WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonExceptions("El usuario no existe");
        }
    }
    public String GetIdTeacherByName(String name){
        try{
            String sql = "select id_docente " +
                    "from profesores " +
                    "where id_usuario=(select id " +
                    "from usuarios " +
                    "where nombres= ?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, String.class);
        }catch (EmptyResultDataAccessException e){
            throw new PersonExceptions("El docente no existe");
        }
    }
    public String GetIdStudentByName(String name){
        try{
            String sql = "select id_estudiante " +
                    "from estudiantes " +
                    "where id_usuario=(select id " +
                    "from usuarios " +
                    "where nombres= ?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, String.class);
        }catch (EmptyResultDataAccessException e){
            throw new PersonExceptions("El Estudiante no existe");
        }
    }
    public String GetIdStudentByUsername(String username){
        try{
            String sql = "select id_estudiante " +
                    "from estudiantes " +
                    "where id_usuario=(select id " +
                    "from usuarios " +
                    "where username= ?)";
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
        }catch (EmptyResultDataAccessException e){
            throw new PersonExceptions("El Estudiante no existe");
        }
    }
    public boolean ExistTeacher(String id){
        String sql = "SELECT COUNT(*) FROM profesores WHERE id_docente = ?";
        int result = jdbcTemplate.queryForObject(sql, new Object[]{id}, int.class);
        return result > 0;
    }
    public boolean ExistMember(String id){
        String sql = "SELECT COUNT(*) FROM estudiantes WHERE id_estudiante = ?";
        int result = jdbcTemplate.queryForObject(sql, new Object[]{id}, int.class);
        return result > 0;
    }
    public int GetPerfilByUsername(String username){
        String sql = "SELECT perfil FROM usuarios WHERE username= ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, int.class);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonExceptions("El usuario no existe");
        }
    }
    public void uptadestate (int id_state,String id_route){
        try {
            String sql = "UPDATE rutas " +
                    "SET id_estado = ? " +
                    "WHERE id_ruta = ?";

            jdbcTemplate.update(sql,id_state,id_route);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonExceptions("No se ha podido completar la sentencia");
        }
    }
    public int getstatebyId(String id_route){
        try{
            String sql= "SELECT id_estado FROM rutas WHERE id_ruta= ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id_route}, int.class);
        }catch (EmptyResultDataAccessException e){
            throw new PersonExceptions("No se ha podido completar la sentencia");
        }
    }

}
