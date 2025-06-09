package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.util.List;

import static org.acme.repository.ScheduleEnt.FIELDS;
import static org.acme.repository.ScheduleEnt.TABLE_NAME;

@ApplicationScoped
public class ScheduleReposotory {
    @Inject
    Jdbi jdbi;

    public List<ScheduleEnt> findAllSchedules() {
        var query = String.format("SELECT %s FROM %s", FIELDS, TABLE_NAME);
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .registerRowMapper(ConstructorMapper.factory(ScheduleEnt.class))
                        .mapTo(ScheduleEnt.class)
                        .list()
        );
    }
}
