package io.security.springsecurity.service;

import io.security.springsecurity.domain.Resources;

import java.util.List;

public interface ResourcesService {

    Resources selectResources(long id);

    List<Resources> selectResources();

    void insertResources(Resources Resources);

    void deleteResources(long id);
}