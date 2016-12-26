package eu.yoannfleury.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.dto.RegionDTO;
import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.exception.NotificationNotFoundException;
import eu.yoannfleury.exception.RegionNotFoundException;
import eu.yoannfleury.mapper.NotificationMapper;
import eu.yoannfleury.repository.EffectRepository;
import eu.yoannfleury.repository.NotificationRepository;
import eu.yoannfleury.repository.ProductRepository;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

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

    public List<NotificationDTO> getByPagination(int page, int limit,
                                            Sort.Direction direction,
                                            String property) {
        Pageable pageable = new PageRequest(page, limit, direction, property);

        Page<Notification> notificationPage = this.notificationRepository.findAll(pageable);

        return this.notificationMapper.entityListToDTOList(notificationPage.getContent());
    }

    /**
     * @param notification The notification to create.
     * @return The notification newly created
     */
    public NotificationDTO create(NotificationDTO notification) {
        ConnectionSpec spec = new ConnectionSpec.Builder(
                ConnectionSpec.MODERN_TLS
        ).build();

        OkHttpClient client = new OkHttpClient.Builder().connectionSpecs(
                Collections.singletonList(spec)
        ).build();

        List<RegionDTO> regions;

        Request request = new Request.Builder()
                .url("https://geo.api.gouv.fr/regions")
                .build();

        try {
            Response response = client.newCall(request).execute();

            ObjectMapper mapper = new ObjectMapper();

            regions = Arrays.asList(mapper.readValue(response.body().string(), RegionDTO[].class));
        } catch (IOException e) {
            throw new RuntimeException();
        }

        boolean inArray = false;
        for (RegionDTO region : regions) {
            if (region.getCode().equals(notification.getCode())) {
                inArray = true;
                break;
            }
        }

        if (inArray) {
            Notification n = this.notificationRepository.save(
                    this.notificationMapper.DTOToEntity(notification)
            );

            return this.notificationMapper.entityToDTO(n);
        } else {
            throw new RegionNotFoundException(notification.getCode());
        }
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
