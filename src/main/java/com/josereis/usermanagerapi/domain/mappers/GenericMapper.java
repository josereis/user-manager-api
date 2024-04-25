package com.josereis.usermanagerapi.domain.mappers;

import java.util.List;

public interface GenericMapper<Entity, Request, Response> {
    Entity toEntity(Request request);
    List<Entity> toEntity(List<Request> requests);

    Response toResponse(Entity entity);
    List<Response> toResponse(List<Entity> entity);
}
