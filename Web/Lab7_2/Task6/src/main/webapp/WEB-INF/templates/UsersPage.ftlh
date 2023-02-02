<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th id="admin-header" hidden>Admin</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr class="user-detail">
                    <td class="user-id"></td>
                    <td class="user_login"></td>
                    <td class="user_rule" hidden>
                        <input class="admin-check" type="checkbox" hidden>
                    </td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            reload = function () {

                lab7_ajax({
                        action: "findAll"
                    },
                    function (response) {
                        $(".users .user-detail").remove();
                        let isAdmin = false;
                        if (response["user"] && response["user"]["admin"] === true) {
                            isAdmin = true;
                            $(".datatable #admin-header").attr("hidden", false);
                        } else
                            $(".datatable #admin-header").attr("hidden", true);

                        response["users"].forEach(function (user) {
                            $noData.hide();
                            const $tr = $($(".users template").prop("content")).clone();
                            $tr.find(".user-id").text(user["id"]);
                            $tr.find(".user_login").text(user["login"]);
                            $tr.find(".user_rule .admin-check").attr("data-userId", user["id"]);

                            if (isAdmin) {
                                $tr.find(".user_rule").attr("hidden", false);
                                $tr.find(".user_rule .admin-check").attr("hidden", false);

                                const adminRuleCheckBox = $tr.find(".user_rule .admin-check");
                                adminRuleCheckBox.prop("disabled", false);

                                if (user["admin"] === true)
                                    adminRuleCheckBox.attr("checked", true);
                                else
                                    adminRuleCheckBox.attr("checked", false);
                            }

                            //$tr.find("tr").attr("data-userId", user["id"]);
                            $(".users tbody").append($tr);
                        });

                        $(".user_rule .admin-check").click(function () {

                            const adminRuleCheckBox = $(this);
                            adminRuleCheckBox.prop("disabled", true);

                            let isAdminRule = false;
                            if (adminRuleCheckBox.is(':checked'))
                                isAdminRule = true;

                            const updatedUserid = $(this).attr("data-userId");

                            lab7_ajax({
                                    action: "updateAdminUser",
                                    updatedUserid: updatedUserid,
                                    rule: isAdminRule
                                },
                                function (response) {
                                    adminRuleCheckBox.prop("disabled", false);
                                    if (response["error"]) {
                                        adminRuleCheckBox.prop('checked', !isAdminRule);
                                        notify(response["error"]);

                                        return;
                                    }

                                    if (isAdmin && response["user"] && response["user"]["id"].toString() === updatedUserid.toString()) {
                                        //alert("reload");
                                        //reload();
                                        $(".datatable #admin-header").attr("hidden", true);
                                        $(".users .user_rule").remove();

                                        isAdmin = false;
                                    }
                                }
                            );
                        });
                    }
                );

            };  // end reload

            reload();
        })
    </script>
</@c.page>
