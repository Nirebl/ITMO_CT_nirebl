<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="article-box">
        <div class="header">New Article</div>
        <div class="body">
            <form class="article-form" method="post" action="">
                <input type="hidden" name="action" value="submit"/>
                <div class="field">
                    <div class="name">
                        <label for="Title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" value="${title!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="text-value">
                        <textarea name="text" id="text" rows="5" cols="auto"></textarea>
                    </div>
                </div>
                <div class="error">
                    <#if error??>
                        <div>
                            ${error!}
                        </div>
                    </#if>
                </div>
                <div class="button-field">
                    <input name="submit" type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".article-form").submit(function () {
                    const $form = $(this);
                    let titleElement = $form.find("input[name='title']");
                    const title = titleElement.val();
                    let textElement = $form.find("textarea[name='text']");
                    const text = textElement.val();
                    const $error = $(this).find(".error");

                    let submitBtn = $form.find("input[name='submit']");
                    submitBtn.prop("disabled", true);

                    lab7_ajax({
                            action: "submit",
                            title: title,
                            text: text
                        },
                        function (response) {
                            submitBtn.prop("disabled", false);
                            if (response["error"]) {
                                $error.text(response["error"]);
                            } else {
                                if (response["message"])
                                    notify(response["message"]);

                                titleElement.val("");
                                textElement.val("");
                            }
                        }
                    );

                    return false;
                }
            );
        });
    </script>
</@c.page>
