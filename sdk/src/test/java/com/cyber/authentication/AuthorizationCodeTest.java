package com.cyber.authentication;

import com.cyber.authentication.AuthorizationCode;
import com.cyber.Matagaruda;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.http.MatagarudaHttpClient;
import java.io.IOException;
import static org.junit.Assert.*;

public class AuthorizationCodeTest {

    public static AuthorizationCode instance;

    public AuthorizationCodeTest() throws MatagarudaException {
        String[] scopes = {"penduduk.baca", "penduduk.tulis", "penduduk.ubah"};
        instance = new AuthorizationCode("developer", "developer-secret", "http://iout-er2c.com:8000/developer/redirect", scopes, new MatagarudaHttpClient());

    }

    /**
     * Test of getAuthorizationURI method, of class AuthorizationCode.
     */
    //@Test
    public void testGetAuthorizationURI() throws Exception {
        System.out.println("getAuthorizationURI");
        String expResult = Matagaruda.BASE_URL + "account/oauth/authorize?client_id=developer&response_type=code&redirect_uri=http%3A%2F%2Fiout-er2c.com:8000%2Fdeveloper%2Fredirect&scope=penduduk.baca+penduduk.tulis+penduduk.ubah&state=state";
        String result = instance.getAuthorizationURI();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAccessToken method, of class AuthorizationCode.
     */
    //@Test
    public void testGetAccessToken() throws IOException {
//        try {
//            CookieManager cookie = new CookieManager();
//            System.out.println("getAccessToken");
//            SakaNusantaraHttpClient client = instance.getHttpClient();
//
//            HttpPost httpPost = new HttpPost("https://sapawarga.surabaya.go.id/account/login");
//
//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            nvps.add(new BasicNameValuePair("username", "developer"));
//            nvps.add(new BasicNameValuePair("password", "admin"));
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//
//            SakaNusantaraHttpResponse res = client.execute(httpPost);
//            CookieStore cookieStore = client.getHttpContext().getCookieStore();
//            List<Cookie> cookies = cookieStore.getCookies();
//            for (Cookie cooky : cookies) {
//                System.out.println(cooky.getName() + " = " + cooky.getValue());
//            }
//
//            System.out.println("----------------------------------------get authorization code-----------");
//
//            String authorizationUrl = instance.getAuthorizationURI();
//
//            HttpPost httpCodePost = new HttpPost(authorizationUrl);
//
//            List<NameValuePair> codeparam = new ArrayList<NameValuePair>();
//            codeparam.add(new BasicNameValuePair("client_id", "developer"));
//            codeparam.add(new BasicNameValuePair("client_secret", "developer-secret"));
//            httpCodePost.setEntity(new UrlEncodedFormEntity(codeparam));
//
//            BasicCookieStore cookieStoreReq = new BasicCookieStore();
//            cookieStoreReq.addCookie(cookies.get(0));
//            
//            HttpContext localContext = new BasicHttpContext();
//            localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStoreReq);
//            
//            HttpClient clientReq = client.configHttpClient().setDefaultCookieStore(cookieStoreReq).build();
//            HttpResponse resCode = clientReq.execute(httpCodePost);
//            
//           // System.out.println(EntityUtils.toString(resCode.getEntity()));
//            for (Header header : resCode.getAllHeaders()) {
//                //System.out.println(header.toString());
//            }
//
//            AccessToken expResult = null;
//            AccessToken result;
//            result = instance.getAccessToken();
//            System.out.println(result);
//            //assertEquals(expResult, result);
//        } catch (SakaNusantaraOAuthException ex) {
//            Logger.getLogger(AuthorizationCodeTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(AuthorizationCodeTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SakaNusantaraException ex) {
//            Logger.getLogger(AuthorizationCodeTest.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(AuthorizationCodeTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

}
