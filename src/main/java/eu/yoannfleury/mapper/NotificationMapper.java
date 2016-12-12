package eu.yoannfleury.mapper;

import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.entity.Effect;
import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.repository.EffectRepository;
import eu.yoannfleury.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class NotificationMapper {
    private EffectRepository effectRepository;

    private ProductRepository productRepository;

    public NotificationMapper(EffectRepository effectRepository,
                              ProductRepository productRepository) {
        this.effectRepository = effectRepository;
        this.productRepository = productRepository;
    }

    public NotificationDTO entityToDTO(Notification entity) {
        List<Long> effects = new LinkedList<>();
        List<Long> products = new LinkedList<>();

        for (Effect effect :
                entity.getEffects()) {
            effects.add(effect.getId());
        }

        for (Product product :
                entity.getProducts()) {
            products.add(product.getId());
        }

        return new NotificationDTO(
                entity.getId(),
                entity.getCode(),
                effects,
                products
        );
    }

    public Notification DTOToEntity(NotificationDTO dto) {
        List<Effect> effects = new LinkedList<>();
        List<Product> products = new LinkedList<>();

        for (Long id :
                dto.getEffects()) {
            effects.add(this.effectRepository.findOne(id));
        }

        for (Long id :
                dto.getProducts()) {
            products.add(this.productRepository.findOne(id));
        }

        return new Notification(
                dto.getCode(),
                effects,
                products
        );
    }

    public List<NotificationDTO> entityListToDTOList(List<Notification> entities) {
        List<NotificationDTO> list = new LinkedList<>();

        for (Notification entity :
                entities) {
            list.add(entityToDTO(entity));
        }

        return list;
    }
}
