package de.adorsys.pop.example.controller;

import org.adorsys.jjwk.serverkey.ServerKeyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;

import de.adorsys.pop.example.config.UserResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "/pop", tags={"Proof of Pocession RFC7800"}, description = "Public key distribution endpoint")
@RequestMapping("/pop")
@UserResource
public class PoPController {

    @Autowired
    private ServerKeyManager keyManager;

    @GetMapping(produces={MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Read server public keys", tags={"Read server public keys"}, response=JWKSet.class, notes = "Fetches publick keys of the target server. Keys are used to encrypt data sent to the server and also send a response encrytpion key to the server. See RFC7800")
    @ApiResponses(value = { @ApiResponse (code = 200, message = "Ok")})
    public ResponseEntity<String> getPublicKeys(){
        JWKSet publicKeySet = keyManager.getServerKeysHolder().getPublicKeySet();
        return ResponseEntity.ok(publicKeySet.toPublicJWKSet().toJSONObject().toJSONString());
    }

}

