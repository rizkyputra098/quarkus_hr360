package org.acme.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;

public record ScheduleEnt(
        @ColumnName(EMP_SCHEDULE_ID) Long emp_schedule_id,
        @ColumnName(PERIOD_ID) Long period_id,
        @ColumnName(EMPLOYEE_ID) Long employee_id,
        @ColumnName(SCHEDULE_TYPE) Integer schedule_type,
        @ColumnName(D1) Long d1

) {
    public static final String TABLE_NAME = "hr_emp_schedule";

    public static final String EMP_SCHEDULE_ID = "emp_schedule_id";
    public static final String PERIOD_ID = "period_id";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String SCHEDULE_TYPE = "schedule_type";
    public static final String D1 = "d1";



    public static final String FIELDS = String.join(",", EMP_SCHEDULE_ID, EMPLOYEE_ID, PERIOD_ID, SCHEDULE_TYPE, D1);
    public static final String BINDER = "  :emp_schedule_id, :employeeId, :periodId, :scheduleType, :d1";

}
