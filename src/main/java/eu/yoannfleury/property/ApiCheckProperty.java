package eu.yoannfleury.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("notif-cosmo-ei.api-check")
public class ApiCheckProperty {
    private boolean checkRegionCode;
    private String checkRegionCodeUrl;

    public boolean isCheckRegionCode() {
        return checkRegionCode;
    }

    public void setCheckRegionCode(boolean checkRegionCode) {
        this.checkRegionCode = checkRegionCode;
    }

    public String getCheckRegionCodeUrl() {
        return checkRegionCodeUrl;
    }

    public void setCheckRegionCodeUrl(String checkRegionCodeUrl) {
        this.checkRegionCodeUrl = checkRegionCodeUrl;
    }
}
