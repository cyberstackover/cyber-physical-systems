<!DOCTYPE html>
<html ng-app="template-app">
    <head>
        <title>Login Cyber</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" href="#" type="image/png" sizes="16x16">

        <link rel="stylesheet" href="css/materialize.min.css">
        <link rel="stylesheet" href="css/login.css">

        <script type="text/javascript" src="js/jquery.min.js"></script>
    </head>
    <body>

        <div class="bg-header"></div>

        <div class="container container-login back-shadow">
            <div class="header-login">
                <div class="row">
                    <div class="col l1 m1 s2 no-padding">
                        <i class="material-icons icons-login">lock</i>
                    </div>
                    <div class="col l11 m11 s10">
                        <div class="">
                            <h4>Authentication </h4>
                            <span class="subheader-login">Login to access application and use more feature.</span>
                        </div>
                    </div>
                </div>
            </div>  
            <div class="row">
                <div class="col l12 m12 s12 content-login">
                    <div class="row" style="margin: 0px;padding: 25px;">
                        <#if RequestParameters['error']??>
                        <div class="alert alert-danger">
                            There was a problem logging in. Please try again.
                        </div>
                        </#if>
                        <form class="login-form" role="form" action="login" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                            <br>
                            <div class="col s12">
                                <p>
                                    <input type="checkbox" class="filled-in" id="filled-in-box" name="remember-me" id="remember-me">
                                    <label for="filled-in-box">Remember Credensial</label>
                                </p>
                            </div>
                            <button type="submit" class="waves-effect waves-light btn light-blue darken-3 right">Login</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="container container-footer">
            <p class="center text-footer">Copyright &COPY; 2015</p> 
        </div>
        <script src="js/jquery.min.js"></script>
        <script src="js/materialize.min.js"></script>
        <script src="js/init.js"></script>
    </body>
</html>
