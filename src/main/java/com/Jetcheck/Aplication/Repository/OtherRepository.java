package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OtherRepository {
    private final JdbcTemplate jdbcTemplate2;
    public int Getstatebyadvance(String id_advance){
        String sql = "SELECT id_estado FROM avances WHERE id_avance= ?";
        try {
            return jdbcTemplate2.queryForObject(sql, new Object[]{id_advance}, int.class);
        } catch (EmptyResultDataAccessException e) {
            throw new PersonExceptions("El avance no existe");
        }
    }
    public void rateprogress(double rate,String id_assignment){
        String sql="UPDATE entregas SET calificacion= ? WHERE id_entrega= ?";
        jdbcTemplate2.update(sql,rate,id_assignment);
    }
    public List<Avances> getAvancesByRoutes(String id_ruta){
        String sql="SELECT * FROM avances WHERE id_ruta= ?";
        List<Avances> advances = jdbcTemplate2.query(sql, new Object[] { id_ruta } ,(resultSet, rowNum) -> {
            Avances advance = new Avances();
            advance.setId_estado(resultSet.getInt("id_estado"));
            advance.setFecha_creacion(resultSet.getDate("fecha_creacion"));
            advance.setId_ruta(resultSet.getString("id_ruta"));
            advance.setId_avance(resultSet.getString("id_avance"));
            advance.setDescripcion(resultSet.getString("descripcion"));
            advance.setRubrica(resultSet.getString("rubrica"));
            advance.setTitulo(resultSet.getString("titulo"));
            return advance;
        });
        return advances;
    }
    public List<Entregas> getAssigmentByAdvance(String id_Advance){
        String sql="SELECT * FROM entregas WHERE id_avance= ?";
        List<Entregas> Assignments = jdbcTemplate2.query(sql, new Object[] { id_Advance } ,(resultSet, rowNum) -> {
            Entregas Assignment = new Entregas();
            Assignment.setCalificacion(resultSet.getDouble("calificacion"));
            Assignment.setId_avance(resultSet.getString("id_avance"));
            Assignment.setAnexos(resultSet.getString("anexos"));
            Assignment.setArchivo_entrega(resultSet.getString("archivo_entrega"));
            Assignment.setComentario(resultSet.getString("comentario"));
            Assignment.setId_entrega(resultSet.getString("id_entrega"));
            return Assignment;
        });
        return Assignments;
    }
}
