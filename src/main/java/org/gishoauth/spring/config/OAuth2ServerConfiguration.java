package org.gishoauth.spring.config;

import org.gishoauth.spring.services.simple.GishoUserServiceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 *
 * @author Optimus
 */

@Configuration
public class OAuth2ServerConfiguration {

	
    private static final Logger log = LoggerFactory.getLogger(GuishoApplication.class);
        
        
    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    protected static class SecurityConfig extends WebSecurityConfigurerAdapter {
        
        @Autowired
        private GishoUserServiceDetails gishoUserDetails;
        
        @Override
        @Autowired
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(gishoUserDetails);
        }
        
       

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.formLogin()

                    .and()
                    .csrf().disable()
                    .httpBasic().disable()
                    .anonymous().disable()
                    .authorizeRequests().anyRequest().authenticated()
            ;
        }
    }

    @Configuration
    @EnableAuthorizationServer
    @Order(Ordered.LOWEST_PRECEDENCE)
    protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

        @Value("${config.oauth2.privateKey}")
        private String privateKey;

        @Value("${config.oauth2.publicKey}")
        private String publicKey;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Bean
        public JwtAccessTokenConverter tokenEnhancer() {
            log.info("Initializing JWT with public key:\n" + publicKey);
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(privateKey);
            converter.setVerifierKey(publicKey);
            return converter;
        }

        @Bean
        @Qualifier("tokenStore")
        public TokenStore tokenStore() {
            return new JwtTokenStore(tokenEnhancer());
        }
        
        

        /**
         * Defines the security constraints on the token endpoints /oauth/token_key and /oauth/check_token
         * Client credentials are required to access the endpoints
         *
         * @param oauthServer
         * @throws Exception
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer
                    
                    .tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
                    .checkTokenAccess("hasRole('TRUSTED_CLIENT')"); // isAuthenticated()
           
        }

        /**
         * Defines the authorization and token endpoints and the token services
         *
         * @param endpoints
         * @throws Exception
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .authenticationManager(authenticationManager)
                    //.tokenServices(defaultTokenServices())
                    .tokenStore(tokenStore())
                    .accessTokenConverter(tokenEnhancer())
                    ;
            
        }
        
        @Autowired
        private ClientDetailsService clientDetailsService;
        
        //@Bean
        //@Primary
        public DefaultTokenServices defaultTokenServices(){
            final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore());
            defaultTokenServices.setTokenEnhancer(tokenEnhancer());
            defaultTokenServices.setClientDetailsService(clientDetailsService);
            defaultTokenServices.setSupportRefreshToken(true);
            return defaultTokenServices;
        }
         
       
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()

                    // Confidential client where client secret can be kept safe (e.g. server side)
                    .withClient("confidential").secret("secret")
                    .authorizedGrantTypes("password","client_credentials", "authorization_code", "refresh_token")
                    .autoApprove(true)
                    .scopes("read", "write")
                    .redirectUris("http://localhost:8080/client/")

                    .and()

                            // Public client where client secret is vulnerable (e.g. mobile apps, browsers)
                    .withClient("public") // No secret!
                    .authorizedGrantTypes("client_credentials", "implicit")
                    .scopes("read")
                    .redirectUris("http://localhost:8383/AccessTokenRequest/")

                    .and()

                            // Trusted client: similar to confidential client but also allowed to handle user password
                    .withClient("trusted").secret("secret")
                    .authorities("ROLE_TRUSTED_CLIENT")
                    .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
                    .scopes("read", "write")
                    .redirectUris("http://localhost:8080/client/")
                    
            ;
        }
        

    }
    
    
	
            
            
        
}
        
         
        

	


