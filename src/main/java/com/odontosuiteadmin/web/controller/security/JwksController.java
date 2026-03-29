package com.odontosuiteadmin.web.controller.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwksController {

//    private final JWKSet jwkSet;
//
//    public JwksController(RSAKey rsaKey) {
//        this.jwkSet = new JWKSet(rsaKey.toPublicJWK());
//    }
//
//    @GetMapping("/.well-known/jwks.json")
//    public Map<String, Object> keys() {
//        return jwkSet.toJSONObject();
//    }
}
