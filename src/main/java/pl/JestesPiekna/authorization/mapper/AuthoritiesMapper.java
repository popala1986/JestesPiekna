package pl.JestesPiekna.authorization.mapper;

import pl.JestesPiekna.authorization.dto.AuthoritiesDto;
import pl.JestesPiekna.model.Authorities;

public class AuthoritiesMapper {

    public static AuthoritiesDto mapToAuthoritiesDto(Authorities authorities) {
        AuthoritiesDto authoritiesDto = new AuthoritiesDto();
        authoritiesDto.setUsername(authorities.getUsername());
        authoritiesDto.setAuthority(authorities.getAuthority());
        authoritiesDto.setUser(authorities.getUser());
        return authoritiesDto;
    }

    public static Authorities mapToAuthorities(AuthoritiesDto authoritiesDto) {
        Authorities authorities = new Authorities();
        authorities.setUsername(authoritiesDto.getUsername());
        authorities.setAuthority(authoritiesDto.getAuthority());
        authorities.setUser(authoritiesDto.getUser());
        return authorities;
    }
}