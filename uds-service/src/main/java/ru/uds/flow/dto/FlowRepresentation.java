package ru.uds.flow.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.postgis.GeometryCollection;
import org.springframework.hateoas.RepresentationModel;
import ru.uds.flow.model.Flow;

@Data
@EqualsAndHashCode(callSuper = true)
public class FlowRepresentation extends RepresentationModel<FlowRepresentation> {
    private Long id;
    private String name;
    private GeometryCollection coordinates;

    public FlowRepresentation(Flow flow) {
        this.id = flow.getId();
        this.name = flow.getName();
        this.coordinates = flow.getCoordinates();
    }
}
