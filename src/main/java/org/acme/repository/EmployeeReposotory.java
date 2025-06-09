package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.UpdateEmployeeDTO;
import org.acme.model.Employee;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.acme.repository.EmployeeEnt.*;


@ApplicationScoped
public class EmployeeReposotory {

    @Inject
    Jdbi jdbi;

    public List<String> findAllNameEmployees() {
        var query = String.format("SELECT full_name FROM %s", TABLE_NAME);
        return jdbi.withHandle( handle ->
                handle.createQuery(query)
                        .mapTo(String.class)
                        .list()
        );
    }

    public List<EmployeeEnt> findAllEmployees() {
        var query = String.format("SELECT %s FROM %s", FIELDS, TABLE_NAME);
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .registerRowMapper(ConstructorMapper.factory(EmployeeEnt.class))
                        .mapTo(EmployeeEnt.class)
                        .list()
        );
    }
    public Optional<EmployeeEnt> findById(Long id) {
        var query = String.format("SELECT %s FROM %s WHERE %s = :id", FIELDS, TABLE_NAME, EMPLOYEE_ID);

        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .registerRowMapper(ConstructorMapper.factory(EmployeeEnt.class))
                        .mapTo(EmployeeEnt.class)
                        .findOne()
        );
    }
public void updateEmployee(Long id, UpdateEmployeeDTO request) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE hr_employee SET ");
    Map<String, Object> fieldsToUpdate = new HashMap<>();

    if (request.fullName != null) {
        queryBuilder.append("full_name = :fullName, ");
        fieldsToUpdate.put("full_name", request.fullName);
    }

    if (request.birth_place != null) {
        queryBuilder.append("birth_place = :birth_place, ");
        fieldsToUpdate.put("birth_place", request.birth_place);
    }

    if (request.handphone != null) {
        queryBuilder.append("handphone = :handphone, ");
        fieldsToUpdate.put("handphone", request.handphone);
    }

    if (request.blood_type != null) {
        queryBuilder.append("blood_type = :bloodType, ");
        fieldsToUpdate.put("blood_type", request.blood_type);
    }

    if(fieldsToUpdate.isEmpty()){
        return;
    }

    int lastComma = queryBuilder.lastIndexOf(",");
    queryBuilder.deleteCharAt(lastComma);
    queryBuilder.append(" WHERE employee_id = :id");

    jdbi.useHandle(handle -> {
        var update = handle.createUpdate(queryBuilder.toString());
        fieldsToUpdate.forEach(update::bind);
        update.bind("id", id);
        update.execute();
    });



}


//    public EmployeeEnt createEmployee(String position_level, String fullName, String address, String phone, Integer race) {
//        var employeeId = jdbi.withHandle(handle ->
//                handle.createQuery("SELECT COALESCE(MAX(EMPLOYEE_ID) + 1, 2001110) FROM " + TABLE_NAME)
//                        .mapTo(Long.class)
//                        .one());
//        var ent = new EmployeeEnt(employeeId,position_level,fullName,address,phone,race);
//        var query = String.format("INSERT INTO %s (%s) VALUES (%s)", EmployeeEnt.TABLE_NAME, FIELDS, BINDER);
//
//        jdbi.withHandle(handle ->
//                handle.createUpdate(query)
//                        .bind("employeeId", ent.employeeId())
//                        .bind("position_level", ent.position_level())
//                        .bind("fullName", ent.fullname())
//                        .bind("address", ent.address())
//                        .bind("phone", ent.phone())
//                        .bind("race", ent.race())
//                        .execute()
//        );
//        return ent.withemployeeId(employeeId);
//    }
}
