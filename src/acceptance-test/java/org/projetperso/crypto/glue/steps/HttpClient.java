package org.projetperso.crypto.glue.steps;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.ConfigurableSmartRequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static java.util.Arrays.stream;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.util.List;
import java.util.Map;

@Component
public class HttpClient {

    private final MockMvc mvc;
    private MvcResult currentResult;
    private String currentRoles;
    private List<String> currentProfiles;

    public HttpClient(MockMvc mvc) {
        this.mvc = mvc;
    }

    public void setRoles(String  roles) {
        currentRoles="ROLE_" +roles;
    }

    public void httpGet(final String path){
        final var req= get(path);
        perfomReq(req,currentProfiles,currentRoles);
    }

    public void httpDelete(final String path) {
        final var req = delete(path);
        perfomReq(req, currentProfiles, currentRoles);
    }

    public MockHttpServletResponse getCurrentResponse(){
        requireNonNull(currentResult,"No MockMvc result to return, perform a request before calling this method");
        return currentResult.getResponse();
    }

    public void httpPost(final String path){
        final var req= post(path);
        performPost(req,currentProfiles,currentRoles);
    }

    public void httpPostJson(String path, String json) {
        var req = post(path)
                .contentType("application/json")
                .content(json);
        performPost(req, currentProfiles, currentRoles);
    }

    private void performPost(ConfigurableSmartRequestBuilder<MockHttpServletRequestBuilder> req, List<String> profiles, String roles) {
        setAuth(req,profiles,roles);
        try{
            currentResult=mvc.perform(req).andReturn();
        } catch (final Exception e) {
            fail(e);
        }
    }

    private void perfomReq(ConfigurableSmartRequestBuilder<MockHttpServletRequestBuilder> req, List<String> profiles, String roles) {
        setAuth(req,profiles,roles);
        try{
            currentResult=mvc.perform(req).andReturn();
        } catch (final Exception e) {
            fail(e);
        }
    }

    private void setAuth(ConfigurableSmartRequestBuilder<MockHttpServletRequestBuilder> req, List<String> profiles, String roles) {

        if(roles==null){
            return;
        }
        final var jwt= jwt();
        if(!roles.isEmpty()){
            jwt.authorities(roles(roles));
        }
        jwt.jwt(builder -> builder.claims(claims -> setProfile(claims,profiles)));
        req.with(jwt);
    }

    private void setProfile(Map<String, Object> claims, List<String> profiles) {
        if(nonNull(profiles) && !profiles.isEmpty()){
            claims.put("profile_name", profiles);
        }
    }

    private List<GrantedAuthority> roles(String roles) {
        return stream(roles.split(",")).map(String::trim).map(SimpleGrantedAuthority::new).collect(toList());
    }
}
