package co.ke.personal.shopfilestorage.config.oauth;

import io.quantics.multitenant.tenantdetails.TenantDetails;

public class MyTenantDetail implements TenantDetails {
    private String id;
    private String issuer;
    private String jwkSetUrl;


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getIssuer() {
        return this.issuer;
    }

    @Override
    public String getJwkSetUrl() {
        return this.jwkSetUrl;
    }
}
