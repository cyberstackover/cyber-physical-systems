
package com.cyber.authentication;

import com.cyber.authentication.PasswordGrant;
import com.cyber.authentication.AccessToken;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpClient;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;


public class PasswordGrantTest {
    
    public PasswordGrantTest() {
    }
    
    /**
     * Test of getAccessToken method, of class ClientCredentials.
     */
    //@Test
    public void testGetAccessToken(){
        try {
            System.out.println("getAccessToken");
            String[] scopes = {"penduduk.baca", "penduduk.tulis", "penduduk.ubah"};
            PasswordGrant instance = new PasswordGrant("developer", "developer-secret", scopes, new MatagarudaHttpClient());
            System.out.println("after");
            AccessToken result = instance.setCredentials("admin", "admin").getAccessToken();
            System.out.println("after");
            System.out.println("access token : " + result.toJson());
            assertNotNull(result);
        } catch (MatagarudaException ex) {
            Logger.getLogger(PasswordGrantTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MatagarudaOAuthException ex) {
            Logger.getLogger(PasswordGrantTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(PasswordGrantTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
