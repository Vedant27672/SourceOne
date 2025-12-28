package com.SourceOne.service;

import com.SourceOne.models.Api;
import com.SourceOne.models.SystemEntity;
import com.SourceOne.models.User;
import com.SourceOne.repository.ApiRepository;
import com.SourceOne.repository.SystemEntityRepository;
import com.SourceOne.repository.UserRepository;
import com.SourceOne.enums.APIStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiService extends AbstractCDMService<Api> {

    private final ApiRepository apiRepository;
    private final SystemEntityRepository systemEntityRepository;
    private final UserRepository userRepository;

    public ApiService(ApiRepository apiRepository, SystemEntityRepository systemEntityRepository, UserRepository userRepository) {
        this.apiRepository = apiRepository;
        this.systemEntityRepository = systemEntityRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Api createApi(Api api) {

        if (api.getSystem() == null || api.getSystem().getId() == null) {
            throw new RuntimeException("System is required");
        }

        SystemEntity system = systemEntityRepository.findById(api.getSystem().getId()).orElseThrow(() -> new RuntimeException("System not found"));
        api.setSystem(system);

        if (api.getCreatedBy() != null) {
            User createdBy = userRepository.findById(api.getCreatedBy().getId()).orElseThrow(() -> new RuntimeException("User not found"));
            api.setCreatedBy(createdBy);
        }

        api.setApiVersion(0L);
        return apiRepository.save(api);
    }


    @Transactional
    public Api updateApiStatus(String apiId, APIStatus status, String updatedById) {
        Api api = apiRepository.findById(apiId).orElseThrow(() -> new RuntimeException("API not found with ID: " + apiId));
        User updatedBy = userRepository.findById(updatedById).orElseThrow(() -> new RuntimeException("Updated by user not found with ID: " + updatedById));
        api.setStatus(status);
        api.setUpdatedBy(updatedBy);
        return apiRepository.save(api);
    }

    public List<Api> getApisBySystemId(SystemEntity system) {
        return apiRepository.findBySystem(system);
    }

}
