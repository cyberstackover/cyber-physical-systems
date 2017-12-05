package com.cyber;

import com.cyber.Matagaruda;
import com.cyber.authentication.AccessToken;
import com.cyber.authentication.GrantType;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import com.cyber.http.MatagarudaHttpResponse;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MatagarudaTest {

    public MatagarudaTest() {
    }

    /**
     * Test of getAccessToken method, of class SakaNusantara.
     */
    //@Test
    public void testGetAccessToken() throws MatagarudaException, URISyntaxException {
        try {
            System.out.println("saka nusantara");
            String[] scopes = {"penduduk.baca", "penduduk.tulis", "penduduk.ubah"};
            Matagaruda instance = new Matagaruda()
                    .setClientId("developer")
                    .setClientSecret("developer-secret")
                    .setRedirectUrl("http://iout-er2c.com:8000/developer/redirect").setScopes(scopes)
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .build();
            AccessToken accessToken = instance.getAccessToken();
            System.out.println(accessToken.getAccessToken());
        } catch (MatagarudaOAuthException ex) {
            Logger.getLogger(MatagarudaTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Test of setAccessToken method, of class SakaNusantara.
     */
    @Test
    public void testSetAccessToken() {

    }

    /**
     * Test of get method, of class SakaNusantara.
     */
   // @Test
    public void testGet() {
        try {
            System.out.println("get");
            String url = "/user";

            String[] scopes = {"penduduk.baca", "penduduk.tulis", "penduduk.ubah"};
            Matagaruda instance = new Matagaruda()
                    .setClientId("developer")
                    .setClientSecret("developer-secret")
                    .setRedirectUrl("http://iout-er2c.com:8000/developer/redirect").setScopes(scopes)
                    .setGrantType(GrantType.CLIENT_CREDENTIALS)
                    .build();

            System.out.println(instance.getAccessToken().toJson());

            MatagarudaHttpResponse expResult = null;
            MatagarudaHttpResponse result = instance.get(url);
            System.out.println(result.getBody());
            assertEquals(result.getResponseCode(), 200);
        } catch (MatagarudaException ex) {
            Logger.getLogger(MatagarudaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MatagarudaOAuthException ex) {
            Logger.getLogger(MatagarudaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(MatagarudaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
