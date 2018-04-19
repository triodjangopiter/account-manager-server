package ru.pravvich.web.vds;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pravvich.config.api.RestApi;
import ru.pravvich.domain.Vds;
import ru.pravvich.service.VdsService;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@RestApi
@RestController
@RequestMapping("/vds")
public class VdsController {

    @Autowired
    private VdsService vdsService;

    @GetMapping("/list")
    public Collection<VdsRest> list() {
        return collectionToRest(vdsService.list());
    }

    @GetMapping("/get")
    public VdsRest get(@RequestParam(name = "id") int id) {
        return toRest(vdsService.get(id));
    }

    @PostMapping("/save")
    public VdsRest save(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds saved = vdsService.saveOrUpdate(entity);
        return toRest(saved);
    }

    @PostMapping("/update")
    public VdsRest update(@RequestBody() VdsRest vds) {
        Vds entity = toEntity(vds);
        Vds update = vdsService.saveOrUpdate(entity);
        return toRest(update);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody() VdsRest vds) {
        vdsService.delete(toEntity(vds));
    }

    private Collection<VdsRest> collectionToRest(@NonNull Collection<Vds> vds) {
        return vds.stream().map(this::toRest).collect(Collectors.toList());
    }

    private VdsRest toRest(@NonNull Vds entity) {
        VdsRest rest = new VdsRest();
        rest.setId(entity.getId());
        rest.setIp(entity.getIp());
        rest.setNote(entity.getNote());
        rest.setLogin(entity.getLogin());
        rest.setPassword(entity.getPassword());
        rest.setActivatedDate(nonNull(entity.getActivatedDate()) ? entity.getActivatedDate().getTime() : 0);
        rest.setDeactivatedDate(nonNull(entity.getDeactivatedDate()) ? entity.getDeactivatedDate().getTime() : 0);
        return rest;
    }

    private Vds toEntity(@NonNull VdsRest rest) {
        Vds entity = new Vds();
        entity.setId(rest.getId());
        entity.setIp(rest.getIp());
        entity.setNote(rest.getNote());
        entity.setLogin(rest.getLogin());
        entity.setPassword(rest.getPassword());
        entity.setActivatedDate(rest.getActivatedDate() != 0 ? new Timestamp(rest.getActivatedDate()) : null);
        entity.setDeactivatedDate(rest.getDeactivatedDate() != 0 ? new Timestamp(rest.getDeactivatedDate()) : null);
        return entity;
    }
}
