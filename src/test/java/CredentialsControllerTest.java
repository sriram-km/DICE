import com.dice.weatherApp.controller.CredentialsController;
import com.dice.weatherApp.controller.WeatherController;
import com.dice.weatherApp.model.Credential_response;
import com.dice.weatherApp.service.CredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CredentialsControllerTest {

    @InjectMocks
    private CredentialsController credentialsController;

    @InjectMocks
    private WeatherController weatherController;


    @Mock
    private CredentialService credentialService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateCredentials() throws NoSuchAlgorithmException {

        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) credentialsController.generateCredentials();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Credential_response credential_response = (Credential_response) responseEntity.getBody();
        assert credential_response != null;
        assertEquals(16, credential_response.getClientId().length(), "Client id length is not 16");
        assertEquals(32, credential_response.getClientSecret().length(),"Client secret length is not 32");

        CredentialService credentialService = new CredentialService();
        boolean credentialServiceResult = credentialService.validateAuthentication(credential_response.getClientId(),credential_response.getClientSecret());
        assertTrue(credentialServiceResult, "Created crendtials isn't valid");
    }
}
