<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
        <title>Oauth Cyber</title>

        <!-- ICON --> 
        <link rel="icon" href="images/logo/oauth.png" type="image/png" sizes="42x42">

        <!-- CSS  -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="css/oauth.css"  type="text/css" rel="stylesheet" media="screen,projection"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col s4 offset-s4 z-depth-4 card-panel" style="margin-top: 50px">
                    <#if RequestParameters['error']??>
                    <div class="alert alert-danger">
                        There was a problem logging in. Please try again.
                    </div>
                    </#if>
                    <form class="login-form" role="form" action="login" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="row">
                            <div class="input-field col s12 center">
                                <img class="circle responsive-img valign profile-image-login" alt="" src="images/login-logo.png">
                                <p class="center login-form-text">Cyber System</p>
                            </div>
                        </div>
                        <div class="row margin">
                            <div class="input-field col s12">
                                <i class="mdi-social-person-outline prefix"></i>
                                <input id="username" name="username" type="text" class="validate">
                                <label class="center-align" for="username">Username</label>
                            </div>
                        </div>
                        <div class="row margin">
                            <div class="input-field col s12">
                                <i class="mdi-action-lock-outline prefix"></i>
                                <input id="password" name="password" type="password" class="validate">
                                <label for="password">Password</label>
                            </div>
                        </div>
                        <div class="row">          
                            <div class="input-field col s12 m12 l12  login-text">
                                <input type="checkbox"  name="remember-me" id="remember-me">
                                <label for="remember-me">Remember me</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <a class="btn waves-effect waves-light col s12" href="index-2.html">Login</a>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s6 m6 l6">
                                <p class="margin medium-small"><a href="regsiter">Register Now!</a></p>
                            </div>
                            <div class="input-field col s6 m6 l6">
                                <p class="margin right-align medium-small"><a href="forgot">Forgot password ?</a></p>
                            </div>          
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!--  Scripts-->
        <script src="js/jquery.min.js"></script>
        <script src="js/materialize.min.js"></script>
        <script src="js/init.js"></script>
    </body>
</html>
