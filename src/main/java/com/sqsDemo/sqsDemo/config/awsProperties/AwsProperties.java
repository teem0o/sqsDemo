package com.sqsDemo.sqsDemo.config.awsProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties("cloud.aws")
@Configuration
public class AwsProperties {
    private String region;
    private Credentials credentials;
    private String endPointUri;

    public static class Credentials{
        String queuename;
        String accesskey;
        String secretkey;

        public String getQueuename() {
            return queuename;
        }

        public void setQueuename(String queuename) {
            this.queuename = queuename;
        }

        public String getAccesskey() {
            return accesskey;
        }

        public void setAccesskey(String accesskey) {
            this.accesskey = accesskey;
        }

        public String getSecretkey() {
            return secretkey;
        }

        public void setSecretkey(String secretkey) {
            this.secretkey = secretkey;
        }
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getEndPointUri() {
        return endPointUri;
    }

    public void setEndPointUri(String endPointUri) {
        this.endPointUri = endPointUri;
    }
}
