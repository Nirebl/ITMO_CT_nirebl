<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hidden</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td>${article.id}</td>
                    <td>${article.title}</td>
                    <td>
                        <#assign hidden = "">
                        <#if article.isHidden()>
                            <#assign hidden = "checked">
                        </#if>
                        <input class="hidden-check" id="${article.id}" type="checkbox" ${hidden}>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".datatable .hidden-check").click(
                function (event) {
                    const $checked = $(this);
                    $checked.prop("disabled", true);
                    let hidden = false;
                    if ($checked.is(':checked'))
                        hidden = true;

                    lab7_ajax({
                            action: "updateHidden",
                            articleId: $checked.attr("id"),
                            hidden: hidden
                        },
                        function (response) {
                            if (response["error"]) {
                                $checked.prop('checked', !hidden);
                                notify(response["error"]);
                            }

                            $checked.prop("disabled", false);
                        }
                    );
                }
            )
        })
    </script>

</@c.page>
