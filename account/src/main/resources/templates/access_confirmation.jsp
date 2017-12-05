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
        <link href="../css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
        <link href="../css/login.css"  type="text/css" rel="stylesheet" media="screen,projection"/>
    </head>
    <body>

        <div class="bg-header"></div>

        <div class="container container-login back-shadow">
            <div class="header-login">
                <div class="row">
                    <div class="col l1 m1 s2 no-padding">
                        <i class="material-icons icons-login">lock_open</i>
                    </div>
                    <div class="col l11 m11 s10">
                        <div class="">
                            <h4>Auth Confirmation </h4>
                            <span class="subheader-login">Confirm your scope privilleges in application</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col l12 m12 s12 content-login">
                    <div class="row" style="margin: 0px;padding: 25px;">
                        <form id="confirmationForm" name="confirmationForm" action="/account/oauth/authorize" method="post">
                            <div class="col s12">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input name="user_oauth_approval" value="true" type="hidden" />
                                <#list scopes?keys as key>
                                <input name="${key}" value="true" type="checkbox" ${scopes[key]} class="filled-in" id="filled-in-${key}" />
                                <label for="filled-in-${key}">${key}</label>
                                <br>
                                </#list>
                            </div>
                            <br><br><hr>
                            <button class="waves-effect waves-light btn light-blue darken-3 right" name="user_oauth_approval" value="true" type="submit">APPROVE</button>
                            <button class="waves-effect waves-light btn light-blue white z-depth-0 right" style="color: #0277bd;" name="user_oauth_approval" value="false" type="submit">DECLINE</button>
                        </form>

                    </div>
                </div>
            </div>
        </div>

        <div class="container container-footer">
            <p class="center text-footer">Copyright &COPY; 2015</p>
        </div>
        <!--  Scripts-->
        <script src="../js/jquery.min.js"></script>
        <script src="../js/materialize.min.js"></script>
        <script src="../js/init.js"></script>
    </body>
</html>