package eu.yoannfleury.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.dto.RegionDTO;
import eu.yoannfleury.entity.Notification;
import eu.yoannfleury.exception.NotificationNotFoundException;
import eu.yoannfleury.exception.RegionNotFoundException;
import eu.yoannfleury.mapper.NotificationMapper;
import eu.yoannfleury.property.ApiCheckProperty;
import eu.yoannfleury.repository.EffectRepository;
import eu.yoannfleury.repository.NotificationRepository;
import eu.yoannfleury.repository.ProductRepository;
import eu.yoannfleury.repository.UserRepository;
import eu.yoannfleury.security.IJwtUser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final EffectRepository effectRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final NotificationMapper notificationMapper;

    private final ApiCheckProperty apiCheckProperty;
    private final IJwtUser iJwtUser;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               NotificationMapper notificationMapper,
                               EffectRepository effectRepository,
                               ProductRepository productRepository,
                               UserRepository userRepository,
                               ApiCheckProperty apiCheckProperty,
                               IJwtUser iJwtUser) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
        this.effectRepository = effectRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.apiCheckProperty = apiCheckProperty;
        this.iJwtUser = iJwtUser;
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

    public List<NotificationDTO> getByUser(HttpServletRequest request) {
        return this.notificationMapper.entityListToDTOList(
                this.notificationRepository.findByUser(
                        this.userRepository.findOne(
                                this.iJwtUser.getUser(request).getId()
                        )
                )
        );
    }

    /**
     * @param notification The notification to create.
     * @return The notification newly created
     */
    public NotificationDTO create(NotificationDTO notification) {
        if (apiCheckProperty.isCheckRegionCode() && checkForCode(notification)) {
            Notification n = this.notificationRepository.save(
                    this.notificationMapper.DTOToEntity(notification)
            );

            return this.notificationMapper.entityToDTO(n);
        } else {
            Notification n = this.notificationRepository.save(
                    this.notificationMapper.DTOToEntity(notification)
            );

            return this.notificationMapper.entityToDTO(n);
        }
    }


    private boolean checkForCode(NotificationDTO notification) {
        OkHttpClient client = new OkHttpClient();

        List<RegionDTO> regions;

        Request request = new Request.Builder()
                .url(this.apiCheckProperty.getCheckRegionCodeUrl())
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
                return true;
            }
        }

        throw new RegionNotFoundException(notification.getCode());
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
