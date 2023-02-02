<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Enter</div>
        <div class="body">
            <form class="login-form" method="post" action="">
                <input type="hidden" name="action" value="enter"/>
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" value="${login!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" type="password" value="${password!}"/>
                    </div>
                </div>
                <div class="error">
                    <#if error??>
                        ${error!}
                    </#if>
                </div>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".login-form").submit(function () {
                    const $form = $(this);
                    const login = $form.find("input[name='login']").val();
                    const pwd = $form.find("input[name='password']").val();
                    const $error = $(this).find(".error");

                    ajax({
                            action: "enter",
                            login: login,
                            password: pwd
                        },
                        function (response) {
                            if (response["error"]) {
                                $error.text(response["error"]);
                            }
                        }
                    );

                    return false;
                }
            );
        })
        ;
    </script>
</@c.page>
