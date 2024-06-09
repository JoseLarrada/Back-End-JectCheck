package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.Response.InteractiveSearch;
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
    private final IdGeneratorConfig generator;

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
            ruta.setTitulo(resultSet.getString("titulo"));
            ruta.setId_docente(resultSet.getString("id_docente"));
            ruta.setId_estudiante(resultSet.getString("id_estudiante"));
            ruta.setIdEstado(resultSet.getInt("id_estado"));
            ruta.setDescripcion(resultSet.getString("descripcion"));
            ruta.setId_ruta(resultSet.getString("id_ruta"));
            ruta.setId_integrante(resultSet.getString("id_integrante"));
            ruta.setId_integrante2(resultSet.getString("id_integrante2"));
            ruta.setIdArea(resultSet.getInt("id_area"));
            ruta.setIdFacultad(resultSet.getInt("id_facultad"));
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
            ruta.setTitulo(resultSet.getString("titulo"));
            ruta.setId_docente(resultSet.getString("id_docente"));
            ruta.setId_estudiante(resultSet.getString("id_estudiante"));
            ruta.setIdEstado(resultSet.getInt("id_estado"));
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
    public String getIdUserByFullName(String name, String role, String id){
        try{
            String sql = "SELECT "+ id +" FROM "+ role + " WHERE nombre_completo = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, String.class);
        }catch (EmptyResultDataAccessException e){
            throw new PersonExceptions("El Estudiante no existe");
        }
    }
    public String getFullNameUserById(String identificacion, String role, String idRole){
        try{
            String sql = "SELECT nombre_completo FROM "+ role + " WHERE " +idRole+ "= ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{identificacion}, String.class);
        }catch (EmptyResultDataAccessException e){
            return null;
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
    public List<InteractiveSearch> findUserInteractive(String name,String roleUser){
        try {
            String sql = "SELECT nombre_completo FROM " + roleUser + " WHERE nombre_completo LIKE ?";
            List<InteractiveSearch> listUsers = jdbcTemplate.query(sql, new Object[] { "%" + name + "%" }, (resultSet, rowNum) ->{
                InteractiveSearch result= new InteractiveSearch();
               result.setInitial(generator.getInitialName(resultSet.getString("nombre_completo")));
               result.setFullName(resultSet.getString("nombre_completo"));
               return result;
            });
            return listUsers;
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    public List<InteractiveSearch> getMostAreas(String tableName,String idTableName,String idUser){
        try {
            String sql = String.format("""
                SELECT f.nombre, COUNT(p.id_ruta) AS total_proyectos
                FROM areas f
                JOIN rutas p ON f.id_area = p.id_area
                JOIN %s e ON e.%s = p.%s
                WHERE e.id_usuario = ?
                GROUP BY f.nombre
                ORDER BY total_proyectos DESC
                LIMIT 3;
                """, tableName, idTableName, idTableName);
            List<InteractiveSearch> listProject = jdbcTemplate.query(sql, new Object[] {idUser}, (resultSet, rowNum) ->{
                InteractiveSearch result= new InteractiveSearch();
                result.setInitial(resultSet.getString("nombre"));
                result.setFullName(resultSet.getString("total_proyectos"));
                return result;
            });
            return listProject;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
