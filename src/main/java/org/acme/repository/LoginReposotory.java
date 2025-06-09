package org.acme.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.hash.PasswordUtil;
import org.acme.model.Auth;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.sun.tools.attach.VirtualMachine.list;
import static org.acme.repository.LoginEnt.FIELDS;
import static org.acme.repository.LoginEnt.TABLE_NAME;
import static org.acme.repository.LoginEnt.*;

@ApplicationScoped
public class LoginReposotory {

    @Inject
    Jdbi jdbi;

    public List<Auth> getAuths() {
        var query = String.format("SELECT email,password FROM %s", TABLE_NAME);
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .map((rs,ctx) -> new Auth(
                                rs.getString("email"),
                                rs.getString("password")
                        ))
        .list()
        );

    }


    public Optional<LoginEnt> getAuthByEmail(String email) {
        var query = String.format("SELECT %s FROM %s WHERE email = :email", LoginEnt.FIELDS, LoginEnt.TABLE_NAME);
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("email", email)
                        .registerRowMapper(ConstructorMapper.factory(LoginEnt.class))
                        .mapTo(LoginEnt.class)
                        .findFirst()
        );
    }




    public LoginEnt createLoginEnt(String password,  String email,String fullName,Long employeeId,  Integer adminStatus) {
        var userId = jdbi.withHandle(handle ->
                handle.createQuery("SELECT COALESCE(MAX(USER_ID) + 1, 2001110) FROM " + TABLE_NAME)
                        .mapTo(Long.class)
                        .one());

        String regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String hashedPassword = PasswordUtil.hash(password);

        var ent = new LoginEnt(userId, hashedPassword, fullName, email, regDate, employeeId, adminStatus);

        var query = String.format("INSERT INTO %s (%s) VALUES (%s)", TABLE_NAME, FIELDS, BINDER);

        jdbi.withHandle(handle ->
                handle.createUpdate(query)
                        .bind("userId", ent.userId())
                        .bind("password", ent.password())
                        .bind("fullName", ent.fullName())
                        .bind("email", ent.email())
                        .bind("regDate", ent.regDate())
                        .bind("employeeId", ent.employeeId())
                        .bind("adminStatus", ent.adminStatus())
                        .execute()
        );

        return ent.withuserId(userId);
    }
}
