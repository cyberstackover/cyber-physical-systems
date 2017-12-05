<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Sparklr</title>
        <link type="text/css" rel="stylesheet" href="webjars/bootstrap/3.2.0/css/bootstrap.min.css" />
        <script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
        <script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h1>Sparklr</h1>
            <#if RequestParameters['error']??>
                <div class="alert alert-danger">
                    There was a problem logging in. Please try again.
                </div>
            </#if>
            <div class="form-horizontal">
                <form role="form" action="login" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <label for="username">Email address</label>
                        <input type="text" class="form-control" name="username" id="username" required autofocus>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" name="password" id="password" required>
                    </div>
                    <div class="form-group">
                        <label for="remember-me">Remember me</label>
                        <input type="checkbox"  name="remember-me" id="remember-me">
                    </div>
                    <button class="btn btn-primary" type="submit">Sign in</button>
                </form>
            </div>

            <div class="footer">
                Sample application for <a
                    href="http://github.com/spring-projects/spring-security-oauth"
                    target="_blank">Spring Security OAuth</a>
            </div>

        </div>


    </body>
</html>
