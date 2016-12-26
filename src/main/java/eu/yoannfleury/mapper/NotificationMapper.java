package eu.yoannfleury.mapper;

import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.entity.Effect;
import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.repository.EffectRepository;
import eu.yoannfleury.repository.ProductRepository;
import eu.yoannfleury.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class NotificationMapper {
    private EffectRepository effectRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public NotificationMapper(EffectRepository effectRepository,
                              ProductRepository productRepository,
                              UserRepository userRepository) {
        this.effectRepository = effectRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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
                entity.getUser().getId(),
                entity.getCode(),
                entity.getDate(),
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
                this.userRepository.findOne(dto.getUser()),
                dto.getDate(),
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
