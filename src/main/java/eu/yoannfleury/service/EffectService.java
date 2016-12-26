package eu.yoannfleury.service;

import eu.yoannfleury.dto.EffectDTO;
import eu.yoannfleury.entity.Effect;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.exception.EffectNotFoundException;
import eu.yoannfleury.mapper.EffectMapper;
import eu.yoannfleury.repository.EffectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EffectService {
    private final EffectRepository effectRepository;

    private final EffectMapper effectMapper;

    @Autowired
    public EffectService(EffectRepository effectRepository,
                         EffectMapper effectMapper) {
        this.effectMapper = effectMapper;
        this.effectRepository = effectRepository;
    }

    public EffectDTO get(long id) {
        Effect e = this.effectRepository.findOne(id);
        if (e == null){
            throw new EffectNotFoundException(Long.toString(id));
        }

        return this.effectMapper.entityToDTO(e);
    }

    public List<EffectDTO> getAll() {
        return this.effectMapper.entityListToDTOList(
                this.effectRepository.findAll()
        );
    }

    public List<EffectDTO> getByPagination(int page, int limit,
                                            Sort.Direction direction,
                                            String property) {
        Pageable pageable = new PageRequest(page, limit, direction, property);

        Page<Effect> effectPage = this.effectRepository.findAll(pageable);

        return this.effectMapper.entityListToDTOList(effectPage.getContent());
    }

    public List<EffectDTO> search(String pattern) {
        System.out.println(pattern);
        return this.effectMapper.entityListToDTOList(
                this.effectRepository.findByPattern("%" + pattern + "%")
        );
    }

    public EffectDTO create(EffectDTO effect) {
        Effect e = this.effectRepository.save(
                this.effectMapper.DTOToEntity(effect)
        );

        return this.effectMapper.entityToDTO(e);
    }

    public EffectDTO update(long id, EffectDTO effect) {
        Effect entity = this.effectRepository.findOne(id);

        if (entity == null) {
            throw new EffectNotFoundException(id);
        }

        entity.setDescription(effect.getDescription());

        return this.effectMapper.entityToDTO(
                this.effectRepository.saveAndFlush(entity)
        );
    }

    public void delete(long id) {
        if (this.effectRepository.findOne(id) == null) {
            throw new EffectNotFoundException(id);
        }
        this.effectRepository.delete(id);
    }
}
