package co.zip.candidate.userapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "user-api")
public class AppConfig {
    private BigDecimal minExpendableIncome;
    private int maxNumberOfAccountAssociation;

    public BigDecimal getMinExpendableIncome() {
        return minExpendableIncome;
    }

    public void setMinExpendableIncome(BigDecimal minExpendableIncome) {
        this.minExpendableIncome = minExpendableIncome;
    }

    public int getMaxNumberOfAccountAssociation() {
        return maxNumberOfAccountAssociation;
    }

    public void setMaxNumberOfAccountAssociation(int maxNumberOfAccountAssociation) {
        this.maxNumberOfAccountAssociation = maxNumberOfAccountAssociation;
    }
}
