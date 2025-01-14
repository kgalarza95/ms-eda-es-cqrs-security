package ec.com.sofka.mapper;

import ec.com.sofka.document.UserSystemEntity;
import ec.com.sofka.gateway.dto.UserAdminDTO;
import ec.com.sofka.model.util.RoleSystemEnum;

public class UserSystemRepoMapper {
    public static UserAdminDTO toDTO(UserSystemEntity entity) {
        if (entity == null) {
            return null;
        }
        UserAdminDTO dto = new UserAdminDTO();
        dto.setId(entity.getId());
        dto.setFirstname(entity.getFirstName());
        dto.setLastname(entity.getLastName());
        dto.setEmail(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole().name());
        return dto;
    }

    public static UserSystemEntity toEntity(UserAdminDTO dto) {
        if (dto == null) {
            return null;
        }
        UserSystemEntity entity = new UserSystemEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstname());
        entity.setLastName(dto.getLastname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setRole(RoleSystemEnum.valueOf(dto.getRole()));
        return entity;
    }
}
