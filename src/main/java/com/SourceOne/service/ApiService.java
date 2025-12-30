package com.SourceOne.service;

import com.SourceOne.models.Api;
import com.SourceOne.models.SystemEntity;
import com.SourceOne.repository.ApiRepository;
import com.SourceOne.repository.SystemEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ApiService extends AbstractCDMService<Api> {

    private final ApiRepository apiRepository;
    private final SystemEntityRepository systemEntityRepository;

    public ApiService(ApiRepository apiRepository, SystemEntityRepository systemEntityRepository) {
        this.apiRepository = apiRepository;
        this.systemEntityRepository = systemEntityRepository;
    }

    /* ================= CREATE ================= */

    public Api create(Api api, String createdByUsername) {
        if (api.getSystem() == null || api.getSystem().getId() == null) {
            throw new RuntimeException("System is required");
        }
        SystemEntity system = systemEntityRepository.findById(api.getSystem().getId()).orElseThrow(() -> new RuntimeException("System not found"));
        api.setSystem(system);
        api.setCreatedBy(createdByUsername);
        api.setUpdatedBy(createdByUsername);
        if (api.getApiVersion() == null) {
            api.setApiVersion(1L);
        }
        return apiRepository.save(api);
    }

    /* ================= READ ================= */

    public Api getById(String apiId) {
        return apiRepository.findById(apiId).orElseThrow(() -> new RuntimeException("API not found with id: " + apiId));
    }

    public List<Api> getAll() {
        return apiRepository.findAll();
    }

    public List<Api> getBySystem(SystemEntity system) {
        return apiRepository.findBySystem(system);
    }

    /* ================= UPDATE ================= */

    public Api update(String apiId, Api updatedApi, String updatedByUsername) {

        Api existingApi = apiRepository.findById(apiId).orElseThrow(() -> new RuntimeException("API not found with id: " + apiId));

        // update allowed fields only
        existingApi.setName(updatedApi.getName());
        existingApi.setEndpoint(updatedApi.getEndpoint());
        existingApi.setHttpMethod(updatedApi.getHttpMethod());
        existingApi.setStatus(updatedApi.getStatus());

        if (updatedApi.getSystem() != null && updatedApi.getSystem().getId() != null) {
            SystemEntity system = systemEntityRepository.findById(updatedApi.getSystem().getId()).orElseThrow(() -> new RuntimeException("System not found"));
            existingApi.setSystem(system);
        }

        existingApi.setUpdatedBy(updatedByUsername);
        existingApi.setApiVersion(existingApi.getApiVersion() + 1);

        return apiRepository.save(existingApi);
    }

    /* ================= DELETE ================= */

    public Api delete(String apiId) {
        Api api = apiRepository.findById(apiId).orElseThrow(() -> new RuntimeException("API not found with id: " + apiId));

        apiRepository.delete(api);
        return api;
    }
}
