package org.acme.model;

public record Employee(
        String position_level,
        String employee_num,
        String fullname,
        String address,
        String phone,
        String address_permanent,
        String phone_emergency,
        String handphone,
        Integer sex,
        String birth_place,
        Integer birth_date,
        String blood_type,
        Integer race) {
}
