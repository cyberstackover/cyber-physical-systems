package com.cyber.frontend;

import com.cyber.Matagaruda;
import com.cyber.authentication.AccessToken;
import com.cyber.exceptions.MatagarudaException;
import com.cyber.exceptions.MatagarudaOAuthException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {

    private static final String COOKIE_KEY = "matagaruda_session";
    private static final String USER_KEY = "user_session";

    @Autowired
    private Matagaruda sakaNusantara;

    @RequestMapping("/login")
    public String login() throws URISyntaxException, MatagarudaException, MatagarudaOAuthException {
        String url = sakaNusantara.getAuthorizationUrl();
        return "redirect:" + url;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse logout(HttpSession session) {
        session.removeAttribute("access_token");
        return JsonResponse.render(HttpStatus.OK, "ok", null);
    }

    @RequestMapping("/redirect")
    public String redirect(@RequestParam(value = "code", defaultValue = "") String code,
            @RequestParam(value = "error", defaultValue = "") String error,
            HttpServletResponse response) throws MatagarudaException, MatagarudaOAuthException, URISyntaxException {

        AccessToken token = sakaNusantara.setAuthorizationCode(code).getAccessToken();
        
        response.addCookie(new Cookie(USER_KEY, sakaNusantara.get("/user").getBody()));
        response.addCookie(new Cookie(COOKIE_KEY, token.toJson()));
        return "redirect:/";
    }

    @RequestMapping("/user")
    @ResponseBody
    public Map<String, Object> user(HttpSession session,
            HttpServletRequest request,
            HttpServletResponse response)
            throws MatagarudaException, URISyntaxException, MatagarudaOAuthException {
        Cookie[] cookies = request.getCookies();
        AccessToken token = null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(COOKIE_KEY)) {
                token = AccessToken.fromJson(cookie.getValue());
            }
        }

        if (token == null) {
            return (Map<String, Object>) new HashMap<String, Object>().put("error", "error occured");
        }
        sakaNusantara.setAccessToken(token);
        System.out.println(sakaNusantara.getAccessToken().toJson());
        response.addCookie(new Cookie(COOKIE_KEY, sakaNusantara.getAccessToken().toJson()));
        return (Map<String, Object>) sakaNusantara.get("/user").getJsonObject();

    }

}
