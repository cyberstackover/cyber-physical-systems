
package com.cyber.authentication;

import com.cyber.authentication.RefreshTokenGrant;
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


public class RefreshTokenTest {
    
    public RefreshTokenTest() {
    }
    
    /**
     * Test of getAccessToken method, of class ClientCredentials.
     */
    //@Test
    public void testGetAccessToken(){
        try {
            System.out.println("refresh token grant");
            String[] scopes = {"penduduk.baca", "penduduk.tulis", "penduduk.ubah"};
            PasswordGrant instance = new PasswordGrant("developer", "developer-secret", scopes, new MatagarudaHttpClient());
            AccessToken result = instance.setCredentials("admin", "admin").getAccessToken();
            
            System.out.println("access token : " + result.toJson());
            
            RefreshTokenGrant refresh = new RefreshTokenGrant("developer", "developer-secret", scopes, new MatagarudaHttpClient());
            AccessToken refreshToken = refresh.setAccessToken(result).getAccessToken();
            
            System.out.println("refresh token");
            System.out.println(refreshToken.toJson());
            assertNotNull(result);
        } catch (MatagarudaException ex) {
            Logger.getLogger(RefreshTokenTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MatagarudaOAuthException ex) {
            Logger.getLogger(RefreshTokenTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(RefreshTokenTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
