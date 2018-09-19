package ca.ulaval.glo4003.ws.api;

import ca.ulaval.glo4003.ws.api.dto.PingDto;

import javax.ws.rs.BadRequestException;

public class PingResourceImpl implements PingResource {
    @Override
    public PingDto ping(String echo) {
        if(echo == null || echo.isEmpty()) {
            throw new BadRequestException("Missing echo query parameter");
        }
        return new PingDto(echo);
    }
}
