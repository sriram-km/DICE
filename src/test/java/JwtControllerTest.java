import com.dice.weatherApp.controller.JwtController;
import com.dice.weatherApp.model.Jwt_response;
import com.dice.weatherApp.service.JwtService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtControllerTest {

    @InjectMocks
    private JwtController jwtController;

    private JwtService jwtService = new JwtService();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateCredentials() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) jwtController.generateJwt();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Jwt_response response = (Jwt_response) responseEntity.getBody();
        String jwt = response.getJwt();
        JSONObject jsonObject = jwtService.getData(jwt);
        JSONObject claims = jsonObject.getJSONObject("payload");

        assert response != null;
        assertTrue( jwtService.verifyJwt(jwt), "The JWT is valid");
        assertTrue(claims.has("allowed"), "The JWT has the allowed claim");
        assertEquals("true", claims.getString("allowed"), "The allowed claim is true");

        String jwt2 = jwt+"a";
        assertTrue( !jwtService.verifyJwt(jwt2), "The JWT is invalid");
    }
}
