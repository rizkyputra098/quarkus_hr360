package org.acme.until;

import org.eclipse.microprofile.jwt.JsonWebToken;

public class JwtUntil {
    public static Long getEmployeeIdFromJwt(JsonWebToken jwt) {
        return Long.valueOf(jwt.getClaim("employeeId").toString());
    }
}
