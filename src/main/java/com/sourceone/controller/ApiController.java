//package com.SourceOne.controller;
//
//import com.SourceOne.models.Api;
//import com.SourceOne.models.SystemEntity;
//import com.SourceOne.service.ApiService;
//import com.SourceOne.service.SystemService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/apis")
//public class ApiController {
//
//    private final ApiService apiService;
//    private final SystemService systemService;
//
//    public ApiController(ApiService apiService, SystemService systemService) {
//        this.apiService = apiService;
//        this.systemService = systemService;
//    }
//
//    /* ================= CREATE ================= */
//
//    @PostMapping
//    public ResponseEntity<Api> createApi(@RequestBody Api api, @RequestParam(name = "createdByUsername", required = false) String createdByUsername) {
//        Api createdApi = apiService.create(api, createdByUsername);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdApi);
//    }
//
//    /* ================= READ ================= */
//
//    @GetMapping("/{apiId}")
//    public ResponseEntity<Api> getApiById(@PathVariable("apiId") String apiId) {
//        return ResponseEntity.ok(apiService.getById(apiId));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Api>> getAllApis() {
//        return ResponseEntity.ok(apiService.getAll());
//    }
//
//    @GetMapping("/system/{systemId}")
//    public ResponseEntity<List<Api>> getApisBySystem(@PathVariable("systemId") String systemId) {
//        SystemEntity system = systemService.getById(systemId);
//        return ResponseEntity.ok(apiService.getBySystem(system));
//    }
//
//    /* ================= UPDATE ================= */
//
//    @PutMapping("/{apiId}")
//    public ResponseEntity<Api> updateApi(@PathVariable("apiId") String apiId, @RequestBody Api api, @RequestParam(name = "updatedByUsername", required = false) String updatedByUsername) {
//        Api updatedApi = apiService.update(apiId, api, updatedByUsername);
//        return ResponseEntity.ok(updatedApi);
//    }
//
//    /* ================= DELETE ================= */
//
//    @DeleteMapping("/{apiId}")
//    public ResponseEntity<Api> deleteApi(@PathVariable("apiId") String apiId) {
//        Api deletedApi = apiService.delete(apiId);
//        return ResponseEntity.ok(deletedApi);
//    }
//}
