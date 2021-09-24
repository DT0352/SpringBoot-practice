package com.example.resourceserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ResourceServerApplication {
    @Value("${security.oauth2.access-token-uri}")
    private String accessTokenUri;
    @Value("${security.oauth2.client.client-id}")
    private String clientID;
    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Autowired
    private OAuth2ClientProperties oauth2ClientProperties;
//    @Autowired
//    private ConsumerTokenServices consumerTokenServices;

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/login")
    public OAuth2AccessToken login(@RequestParam("username") String username, @RequestParam("password") String password) {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();

        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setClientId(clientID);
        resourceDetails.setUsername(username);
        resourceDetails.setPassword(password);
        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails);
        template.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());
        return template.getAccessToken();
    }

//    @GetMapping("/callback")
//    public OAuth2AccessToken login1(@RequestParam("code") String code) {
//        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
//        resourceDetails.setAccessTokenUri(accessTokenUri);
//        resourceDetails.setClientId(clientID);
//        resourceDetails.setClientSecret(secret);
//
//        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails);
//        template.getOAuth2ClientContext().getAccessTokenRequest().setAuthorizationCode(code);
//        template.getOAuth2ClientContext().getAccessTokenRequest().setPreservedState("http://127.0.0.1:9090/callback");
//        template.setAccessTokenProvider(new AuthorizationCodeAccessTokenProvider());
//        return template.getAccessToken();
//        // 创建 AuthorizationCodeResourceDetails 对象
////        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
////        resourceDetails.setAccessTokenUri(accessTokenUri);
////        resourceDetails.setClientId(oauth2ClientProperties.getClientId());
////        resourceDetails.setClientSecret(oauth2ClientProperties.getClientSecret());
////        // 创建 OAuth2RestTemplate 对象
////        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails);
////        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setAuthorizationCode(code); // <1> 设置 code
////        restTemplate.getOAuth2ClientContext().getAccessTokenRequest().setPreservedState("http://127.0.0.1:9090/callback"); // <2> 通过这个方式，设置 redirect_uri 参数
////        restTemplate.setAccessTokenProvider(new AuthorizationCodeAccessTokenProvider());
////        // 获取访问令牌
////        return restTemplate.getAccessToken();
//    }

    @PostMapping("/client-login")
    public OAuth2AccessToken login2() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(accessTokenUri);
        resourceDetails.setClientId(clientID);
        resourceDetails.setClientSecret(secret);

        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails);
        template.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());
        return template.getAccessToken();
    }

//    @PostMapping("/token/revoke")
//    public boolean revokeToken(@RequestParam("token") String token) {
//        return consumerTokenServices.revokeToken(token);
//    }
}
