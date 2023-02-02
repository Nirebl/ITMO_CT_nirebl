<#import "./common.ftlh" as c>

<@c.page>

    <div class="datatable">
        <div class="caption">All users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>User</th>
            </tr>
            </thead>
            <tbody>
            <#list users as usr>
                <tr>
                    <td>${usr.id}</td>
                    <td>${usr.handle}</td>
                    <td><@c.userlink usr false/></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

</@c.page>
