package eu.yoannfleury.controller;

import eu.yoannfleury.dto.EffectDTO;
import eu.yoannfleury.service.EffectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Effect controller. All the routes for the effects.
 * @author Yoann Fleury
 */
@RestController
@RequestMapping("/effects")
public class EffectController {

    /**
     * The effect service is used to do a CRUD on database.
     */
    private final EffectService effectService;

    /**
     * EffectController constructor
     * @param effectService The {@link eu.yoannfleury.entity.Effect} service
     *                      automatically injected by dependency injection
     *                      manager.
     */
    @Autowired
    public EffectController(EffectService effectService) {
        this.effectService = effectService;
    }

    /**
     * The method matching route <code>/effects</code>. This method
     * will return all the effects, using a pagination system.
     * @param page The page you want to get.
     * @param limit The number of item per page.
     * @param direction The direction, ascending or descending.
     * @param property The property on witch to apply the previous filters.
     * @param httpServletResponse The response object used to set a header to
     *                            indicate the number of pages.
     * @return A list of {@link EffectDTO}.
     */
    @RequestMapping
    public List<EffectDTO> effects(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "direction", defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(value = "property", defaultValue = "id") String property,
            HttpServletResponse httpServletResponse
    ) {
        Integer pages = (int) Math.ceil((double)this.effectService.getAll().size() / (double)limit);
        httpServletResponse.setHeader("X-Pages", pages.toString());
        return this.effectService.getByPagination(page, limit, direction, property);
    }

    /**
     * The method matching route <code>/effects/{id}</code>. This
     * method will return the effect that match the id path variable.
     * @param id The index of the effect you want to get.
     * @return The {@link EffectDTO} that match the path variable.
     */
    @RequestMapping("/{id}")
    public EffectDTO read(@PathVariable long id) {
        return this.effectService.get(id);
    }

    /**
     * The method matching route <code>/effects/search?value=example</code>.
     * This method will return all the effects that match the string pass through
     * the request parameter in their description.
     * @param value The string to match in the description.
     * @return A list of {@link EffectDTO} that match the value.
     */
    @RequestMapping("/search")
    public List<EffectDTO> search(
            @RequestParam(value = "value", defaultValue = "") String value) {
        return this.effectService.search(value);
    }

    /**
     * The method matching route <code>/effects</code> on <strong>POST</strong>
     * Http method. This method will create a new {@link eu.yoannfleury.entity.Effect}
     * with the data given as a json body.
     * @param effect The body matching an {@link EffectDTO} architecture.
     * @return The newly created {@link EffectDTO}.
     */
    @RequestMapping(method = RequestMethod.POST)
    public EffectDTO create(@Validated @RequestBody EffectDTO effect) {
        return this.effectService.create(effect);
    }

    /**
     * The method matching route <code>/effects/{id}</code> on <strong>PUT</strong>
     * Http method. This method will update the {@link eu.yoannfleury.entity.Effect}
     * matching the id path variable.
     * @param id The index of the {@link eu.yoannfleury.entity.Effect} you want to update.
     * @param effect The new data to insert.
     * @return The {@link EffectDTO} just being update.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public EffectDTO update(@PathVariable long id, @Validated @RequestBody EffectDTO effect) {
        return this.effectService.update(id, effect);
    }

    /**
     * The method matching route <code>/effect/{id}</code> on <strong>DELETE</strong>
     * Http method. This method will delete the {@link eu.yoannfleury.entity.Effect}
     * that match the path variable.
     * @param id The index of the {@link eu.yoannfleury.entity.Effect} to delete.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        this.effectService.delete(id);
    }

    /**
     * The method matching route <code>/effects/most_reported</code>. This
     * method will return the most reported {@link eu.yoannfleury.entity.Effect}
     * first.
     * @return A list of {@link EffectDTO} sorted with most reported first.
     */
    @RequestMapping(value = "/most_reported")
    public List<EffectDTO> mostReported() {
        return this.effectService.mostReported();
    }

    /**
     * The method matching route <code>/effects/heaviest</code>. This method
     * will return the heaviest {@link eu.yoannfleury.entity.Effect} first.
     * @return A list of {@link EffectDTO} sorted with heaviest first.
     */
    @RequestMapping(value = "/heaviest")
    public List<EffectDTO> heaviest() {
        return this.effectService.heaviest();
    }
}
