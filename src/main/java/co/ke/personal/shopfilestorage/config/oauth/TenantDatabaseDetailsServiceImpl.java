package co.ke.personal.shopfilestorage.config.oauth;

import io.quantics.multitenant.tenantdetails.TenantDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TenantDatabaseDetailsServiceImpl implements TenantDetailsService {
    @Autowired
    private WebClient webClient;
    private final Map<String, MyTenantDetail> tenants= new HashMap<>();

    @Override
    public Iterable<MyTenantDetail> getAll() {
        return webClient.method(HttpMethod.GET)
                //TODO change to service discovery pattern
                .uri("http://localhost:8080/tenant/api/v1/tenants/details")
                .retrieve()
                .bodyToFlux(MyTenantDetail.class)
                .map((tenantDetail)->{
                    this.tenants.put(tenantDetail.getId(),tenantDetail);
                    return tenantDetail;
                })
                .toIterable();
    }

    @Override
    public Optional<MyTenantDetail> getById(String id) {
        return tenants.containsKey(id) ? Optional.ofNullable(tenants.get(id)) : loadById(id);
    }

    @Override
    public Optional<MyTenantDetail> getByIssuer(String issuer) {
        return this.getById(this.retrieveTenantIdFromIssuer(issuer));
    }

    public Optional<MyTenantDetail> loadById(String tenantId){
        return Optional.ofNullable(webClient.method(HttpMethod.GET)
                //TODO change to service discovery pattern
                .uri("http://localhost:8080/tenant/api/v1/tenants/details/"+tenantId)
                .retrieve()
                .bodyToMono(MyTenantDetail.class)
                .map(tenantDetail -> {
                    this.tenants.put(tenantId,tenantDetail);
                    return tenantDetail;
                })
                .block());
    }

    private String retrieveTenantIdFromIssuer(String issuer){
        String[] str =  issuer.split("/");
        return str[str.length - 1];
    }
}
