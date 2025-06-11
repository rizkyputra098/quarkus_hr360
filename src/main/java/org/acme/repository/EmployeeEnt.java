package org.acme.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;


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
        @JsonIgnore @ColumnName(MARITAL_ID) Long marital_id,
        @JsonIgnore @ColumnName(NATIONALITY_ID) Long nationality_Id,
        @Nullable @ColumnName("MARITAL_STATUS") String marital_status,
        @Nullable @ColumnName("NATIONALITY_NAME") String nationality_name,
        @ColumnName(EMAIL_ADDRESS) String email_address,
        @ColumnName(RACE) Long race,
        @ColumnName(EMERGENCY_NAME) String emergency_name
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
    public static final String MARITAL_ID = "marital_id";
    public static final String NATIONALITY_ID = "nationality_Id";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String RACE = "race";
    public static final String EMERGENCY_NAME = "emergency_name";

    public static final String FIELDS = String.join(",", EMPLOYEE_ID, POSITION_LEVEL, EMPLOYEE_NUM,FULL_NAME,ADDRESS,ADDRESS_PERMANENT,PHONE,PHONE_EMERGENCY,HANDPHONE,SEX,BIRTH_PLACE,BIRTH_DATE,BLOOD_TYPE, EMAIL_ADDRESS,MARITAL_ID,NATIONALITY_ID ,RACE);
    public static final String BINDER = ":employeeId, :position_level, :employee_num, :fullname, :address, :address_permanent, :phone, :phone_emergency, :handphone, :sex, :birth_place, :birth_date, :blood_type,:marital_id,:nationalityId,:email_address, :race";
    public EmployeeEnt withemployeeId(Long employeeId) {
        return new EmployeeEnt(employeeId, position_level, employee_num, fullname,
                address, address_permanent, phone, phone_emergency, handphone,
                sex, birth_place, birth_date, blood_type,marital_id,nationality_Id,marital_status,nationality_name, email_address, race,emergency_name);
    }

}
