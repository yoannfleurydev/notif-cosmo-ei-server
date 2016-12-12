package eu.yoannfleury.controller;

import eu.yoannfleury.dto.NotificationDTO;
import eu.yoannfleury.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping
    public List<NotificationDTO> notifications() {
        return this.notificationService.getAll();
    }

    @RequestMapping("/{id}")
    public NotificationDTO read(@PathVariable long id) {
        return this.notificationService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public NotificationDTO create(@Validated @RequestBody NotificationDTO notification) {
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
}
