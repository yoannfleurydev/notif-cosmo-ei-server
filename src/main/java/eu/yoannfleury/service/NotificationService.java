package eu.yoannfleury.service;

import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.entity.Product;
import eu.yoannfleury.exception.NotificationNotFoundException;
import eu.yoannfleury.mapper.NotificationMapper;
import eu.yoannfleury.repository.EffectRepository;
import eu.yoannfleury.repository.NotificationRepository;
import eu.yoannfleury.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private EffectRepository effectRepository;
    private ProductRepository productRepository;

    private NotificationMapper notificationMapper;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               NotificationMapper notificationMapper,
                               EffectRepository effectRepository,
                               ProductRepository productRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.effectRepository = effectRepository;
        this.productRepository = productRepository;
    }

    /**
     * Get one notification based on the index parameter.
     *
     * @param id The index of the {@link Notification} to fetch.
     * @return The notification that matches with the index parameter.
     */
    public NotificationDTO get(long id) {
        Notification p = this.notificationRepository.findOne(id);
        if (p == null) {
            throw new NotificationNotFoundException(Long.toString(id));
        }

        return this.notificationMapper.entityToDTO(p);
    }

    /**
     * @return The list of all the ingredients
     */
    public List<NotificationDTO> getAll() {
        return this.notificationMapper.entityListToDTOList(
                this.notificationRepository.findAll()
        );
    }

    /**
     * @param notification The notification to create.
     * @return The notification newly created
     */
    public NotificationDTO create(NotificationDTO notification) {
        Notification n = this.notificationRepository.save(
                this.notificationMapper.DTOToEntity(notification)
        );

        return this.notificationMapper.entityToDTO(n);
    }

    /**
     * @param id The index of the notification you want to create.
     * @param notification The model with the new data.
     * @return The notification that matches with the parameter index, with the new values.
     */
    public NotificationDTO update(long id, NotificationDTO notification) {
        Notification entity = this.notificationRepository.findOne(id);

        if (entity == null) {
            throw new NotificationNotFoundException(id);
        }

        entity.setCode(notification.getCode());

        if (notification.getEffects() != null) {
            for (Long effectId :
                    notification.getEffects()) {
                entity.addEffect(
                        this.effectRepository.findOne(effectId)
                );
            }
        }

        if (notification.getProducts() != null) {
            for (Long productId :
                    notification.getProducts()) {
                entity.addProduct(
                        this.productRepository.findOne(productId)
                );
            }
        }

        return this.notificationMapper.entityToDTO(
                this.notificationRepository.saveAndFlush(entity)
        );
    }

    public void delete(long id) {
        if (this.notificationRepository.findOne(id) == null) {
            throw new NotificationNotFoundException(id);
        }
        this.notificationRepository.delete(id);
    }
}
