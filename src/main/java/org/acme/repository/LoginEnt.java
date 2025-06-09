package org.acme.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jdbi.v3.core.mapper.reflect.ColumnName;



public record LoginEnt(
        @JsonIgnore @ColumnName(USER_ID) Long userId,
        @ColumnName(PASSWORD) String password,
        @ColumnName(FULL_NAME) String fullName,
        @ColumnName(EMAIL) String email,
        @ColumnName(REG_DATE) String regDate,
        @ColumnName(EMPLOYEE_ID) Long employeeId,
        @ColumnName(ADMIN_STATUS) Integer adminStatus
) {
    public static final String TABLE_NAME = "hr_app_user";

    public static final String USER_ID = "user_id";
    public static final String PASSWORD = "password";
    public static final String FULL_NAME = "full_name";
    public static final String EMAIL = "email";
    public static final String REG_DATE = "reg_date";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String ADMIN_STATUS = "admin_status";

    public static final String FIELDS = String.join(", ",USER_ID,PASSWORD,FULL_NAME,EMAIL,REG_DATE,EMPLOYEE_ID, ADMIN_STATUS);
    public static final String BINDER = " :userId, :password, :fullName, :email, :regDate, :employeeId, :adminStatus";

    public LoginEnt withuserId(Long userId) {
        return new LoginEnt(userId, password, fullName, email, regDate, employeeId, adminStatus);
    }
}
