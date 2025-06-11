package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.UpdateEmployeeDTO;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.acme.repository.EmployeeEnt.*;
import static org.acme.repository.EmployeeEnt.BINDER;
import static org.acme.repository.EmployeeEnt.FIELDS;
import static org.acme.repository.EmployeeEnt.TABLE_NAME;


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
        var query = """
        SELECT e.*, m.marital_status, n.nationality_name
        FROM hr_employee e
        INNER JOIN hr_marital m ON e.marital_id = m.marital_id
        INNER JOIN hr_nationality n ON e.nationality_id = n.nationality_id
        WHERE e.employee_id = :id
    """;

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

//    public EmployeeEnt createEmployeeEnt(
//            String position_level,
//            String fullName,
//            String employee_num,
//            String address,
//            String address_permanent,
//            String phone,
//            String phone_emergency,
//            String handphone,
//            Integer sex,
//            String birth_place,
//            LocalDate birth_date,
//            String blood_type,
//            String email_address,
//            Long race
//    ) {
//
//        if (position_level != null && position_level.trim().isEmpty()) {
//            position_level = null;
//        }
//        // 2. Cek apakah race valid di tabel hr_race
//        Long validRace = jdbi.withHandle(handle ->
//                handle.createQuery("SELECT RACE_ID FROM hr_race WHERE RACE_ID = :race")
//                        .bind("race", race)
//                        .mapTo(Long.class)
//                        .findOne()
//                        .orElse(14015L) // fallback ke "TO BE DEFINED"
//        );
//
//
//        // 3. Generate ID baru
//        Long employeeId = jdbi.withHandle(handle ->
//                handle.createQuery("SELECT COALESCE(MAX(EMPLOYEE_ID) + 1, 2001110) FROM " + EmployeeEnt.TABLE_NAME)
//                        .mapTo(Long.class)
//                        .one()
//        );
//
//        // 4. Buat entitas employee
//        var ent = new EmployeeEnt(
//                employeeId, position_level,
//                fullName, employee_num,
//                address, address_permanent,
//                phone, phone_emergency, handphone,
//                sex, birth_place, birth_date,
//                blood_type,email_address, validRace
//        );
//
//        // 5. Lakukan insert
//        var query = String.format("INSERT INTO %s (%s) VALUES (%s)",
//                EmployeeEnt.TABLE_NAME, EmployeeEnt.FIELDS, EmployeeEnt.BINDER);
//
//        jdbi.withHandle(handle ->
//                handle.createUpdate(query)
//                        .bind("employeeId", ent.employeeId())
//                        .bind("position_level", ent.position_level())
//                        .bind("employee_num", ent.employee_num())
//                        .bind("fullname", ent.fullname())
//                        .bind("address", ent.address())
//                        .bind("address_permanent", ent.address_permanent())
//                        .bind("phone", ent.phone())
//                        .bind("phone_emergency", ent.phone_emergency())
//                        .bind("handphone", ent.handphone())
//                        .bind("sex", ent.sex())
//                        .bind("birth_place", ent.birth_place())
//                        .bind("birth_date", ent.birth_date())
//                        .bind("blood_type", ent.blood_type())
//                        .bind("email_address",ent.email_address())
//                        .bind("race", ent.race()) // ini udah valid
//                        .execute()
//        );
//
//        return ent.withemployeeId(employeeId);
//    }



}
