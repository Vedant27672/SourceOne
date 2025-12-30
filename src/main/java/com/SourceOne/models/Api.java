package com.SourceOne.models;

import com.SourceOne.enums.APIStatus;
import com.SourceOne.enums.HttpMethod;
import jakarta.persistence.*;

@Entity
@Table(name = "so_api")
public class Api extends CommonDataModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system_id", nullable = false)
    private SystemEntity system;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String endpoint;

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", nullable = false)
    private HttpMethod httpMethod;

    @Column(name = "api_version")
    private Long apiVersion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private APIStatus apiStatus;

    public SystemEntity getSystem() {
        return system;
    }

    public void setSystem(SystemEntity system) {
        this.system = system;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Long getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(Long apiVersion) {
        this.apiVersion = apiVersion;
    }

    public APIStatus getStatus() {
        return apiStatus;
    }

    public void setStatus(APIStatus apiStatus) {
        this.apiStatus = apiStatus;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
