package eu.yoannfleury.controller;

import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.security.IJwtUser;
import eu.yoannfleury.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    private IJwtUser iJwtUser;

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  IJwtUser iJwtUser) {
        this.notificationService = notificationService;
        this.iJwtUser = iJwtUser;
    }

    @RequestMapping
    public List<NotificationDTO> notifications(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "property", defaultValue = "id") String property,
            HttpServletResponse httpServletResponse
    ) {
        Integer pages = (int) Math.ceil((double)this.notificationService.getAll().size() / (double)limit);
        httpServletResponse.setHeader("X-Pages", pages.toString());
        return this.notificationService.getByPagination(page, limit, direction, property);
    }

    @RequestMapping("/{id}")
    public NotificationDTO read(@PathVariable long id) {
        return this.notificationService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public NotificationDTO create(@Validated @RequestBody NotificationDTO notification, HttpServletRequest request) {
        notification.setDate(new Date());
        notification.setUser(this.iJwtUser.getUser(request).getId());
        return this.notificationService.create(notification);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public NotificationDTO update(@PathVariable long id, @Validated @RequestBody NotificationDTO notification) {
        return this.notificationService.update(id, notification);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.notificationService.delete(id);
    }

    @RequestMapping(value = "/myself")
    public List<NotificationDTO> myNotification(HttpServletRequest request) {
        return this.notificationService.getByUser(request);
    }
}
