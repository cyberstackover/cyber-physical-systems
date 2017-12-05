
package com.cyber.authentication;

import com.cyber.authentication.ClientCredentials;
import com.cyber.authentication.AccessToken;
import static com.cyber.authentication.AuthorizationCodeTest.instance;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpClient;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ClientCredentialsTest {
    
    public ClientCredentialsTest() {
    }
    
    /**
     * Test of getAccessToken method, of class ClientCredentials.
     */
    //@Test
    public void testGetAccessToken(){
        try {
            System.out.println("getAccessToken");
            String[] scopes = {"penduduk.baca", "penduduk.tulis", "penduduk.ubah"};
            ClientCredentials instance = new ClientCredentials("developer", "developer-secret", scopes, new MatagarudaHttpClient());
            
            AccessToken result = instance.getAccessToken();
            System.out.println(result.getAccessToken());
            assertNotNull(result);
        } catch (MatagarudaException ex) {
            Logger.getLogger(ClientCredentialsTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MatagarudaOAuthException ex) {
            Logger.getLogger(ClientCredentialsTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ClientCredentialsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
