package org.acme.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.enterprise.context.ApplicationScoped;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;
import java.util.Date;


public record EmployeeEnt(
        @JsonIgnore @ColumnName(EMPLOYEE_ID) Long employeeId,
        @ColumnName(POSITION_LEVEL) String position_level,
        @ColumnName(EMPLOYEE_NUM) String employee_num,
        @ColumnName(FULL_NAME) String fullname,
        @ColumnName(ADDRESS) String address,
        @ColumnName(ADDRESS_PERMANENT) String address_permanent,
        @ColumnName(PHONE) String phone,
        @ColumnName(PHONE_EMERGENCY) String phone_emergency,
        @ColumnName(HANDPHONE) String handphone,
        @ColumnName(SEX) Integer sex,
        @ColumnName(BIRTH_PLACE) String birth_place,
        @ColumnName(BIRTH_DATE) LocalDate birth_date,
        @ColumnName(BLOOD_TYPE) String blood_type,
        @ColumnName(RACE) Integer race
) {
    public static final String TABLE_NAME = "hr_employee";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String POSITION_LEVEL = "position_level";
    public static final String EMPLOYEE_NUM = "employee_num";
    public static final String FULL_NAME = "full_name";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_PERMANENT = "address_permanent";
    public static final String PHONE = "phone";
    public static final String PHONE_EMERGENCY = "phone_emergency";
    public static final String HANDPHONE = "handphone";
    public static final String SEX ="sex";
    public static final String BIRTH_PLACE = "birth_place";
    public static final String BIRTH_DATE = "birth_date";
    public static final String BLOOD_TYPE = "blood_type";
    public static final String RACE = "race";

    public static final String FIELDS = String.join(",", EMPLOYEE_ID, POSITION_LEVEL, EMPLOYEE_NUM,FULL_NAME,ADDRESS,ADDRESS_PERMANENT,PHONE,PHONE_EMERGENCY,HANDPHONE,SEX,BIRTH_PLACE,BIRTH_DATE,BLOOD_TYPE,RACE);
    public static final String BINDER = " :employeeId, :position_level, :fullname, :address, :phone, :race";
//    public EmployeeEnt withemployeeId(Long employeeId) {
//        return new EmployeeEnt(employeeId, position_level, fullname, address, phone, race);
//    }
}
