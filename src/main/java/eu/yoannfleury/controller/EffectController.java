package eu.yoannfleury.controller;

import eu.yoannfleury.dto.EffectDTO;
import eu.yoannfleury.service.EffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/effects")
public class EffectController {
    private final EffectService effectService;

    @Autowired
    public EffectController(EffectService effectService) {
        this.effectService = effectService;
    }

    @RequestMapping
    public List<EffectDTO> effects(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "property", defaultValue = "id") String property
    ) {
        return this.effectService.getByPagination(page, limit, direction, property);
    }

    @RequestMapping("/{id}")
    public EffectDTO read(@PathVariable long id) {
        return this.effectService.get(id);
    }

    @RequestMapping("/search")
    public List<EffectDTO> search(
            @RequestParam(value = "value", defaultValue = "") String value) {
        return this.effectService.search(value);
    }

    @RequestMapping(method = RequestMethod.POST)
    public EffectDTO create(@Validated @RequestBody EffectDTO effect) {
        return this.effectService.create(effect);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public EffectDTO update(@PathVariable long id, @Validated @RequestBody EffectDTO effect) {
        return this.effectService.update(id, effect);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.effectService.delete(id);
    }

    @RequestMapping(value = "/most_reported")
    public List<EffectDTO> mostReported() {
        return this.effectService.mostReported();
    }
}
