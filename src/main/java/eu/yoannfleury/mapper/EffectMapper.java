package eu.yoannfleury.mapper;

import eu.yoannfleury.dto.EffectDTO;
import eu.yoannfleury.entity.Effect;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class EffectMapper {
    public EffectDTO entityToDTO(Effect entity) {
        return new EffectDTO(
                entity.getId(),
                entity.getDescription(),
                entity.getLevel(),
                entity.getNotifications().size()
        );
    }

    public Effect DTOToEntity(EffectDTO dto) {
        return new Effect(
                dto.getDescription(),
                dto.getLevel()
        );
    }

    public List<EffectDTO> entityListToDTOList(List<Effect> entities) {
        List<EffectDTO> list = new LinkedList<>();

        for (Effect entity :
                entities) {
            list.add(entityToDTO(entity));
        }

        return list;
    }
}
