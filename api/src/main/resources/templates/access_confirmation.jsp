<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Sparklr</title>
        <link type="text/css" rel="stylesheet" href="../webjars/bootstrap/3.2.0/css/bootstrap.min.css" />
        <script type="text/javascript" src="../webjars/jquery/2.1.1/jquery.min.js"></script>
        <script type="text/javascript" src="../webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    </head>

    <body>

        <div class="container">
            <h1>Sparklr</h1>
            <h2>Please Confirm</h2>
            <p>
                You hereby authorize ${client.clientId} to access your protected resources.
            </p>
            <#list scopes?keys as key>
                ${key} <hr>
            </#list>
            <form id="confirmationForm" name="confirmationForm" action="/account/oauth/authorize" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input name="user_oauth_approval" value="true" type="hidden" />
                <#list scopes?keys as key>
                <input name="${key}" value="true" type="hidden" />
                </#list>
                <button class="btn btn-primary" type="submit">Approve</button>
            </form>
            <form id="confirmationForm" name="confirmationForm" action="/account/oauth/authorize" method="post">
                <input name="user_oauth_approval" value="true" type="hidden" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <#list scopes?keys as key>
                <input name="${key}" value="false" type="hidden" />
                </#list>
                <button class="btn btn-primary" type="submit">Declined</button>
            </form>        


            <div class="footer">
                Sample application for <a
                    href="http://github.com/spring-projects/spring-security-oauth"
                    target="_blank">Spring Security OAuth</a>
            </div>

        </div>

    </body>
</html>
