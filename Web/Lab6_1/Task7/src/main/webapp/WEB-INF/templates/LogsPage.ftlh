<#-- @ftlvariable name="events" type="ru.itmo.wp.model.domain.Event[]" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Events</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>UserId</th>
                <th>Type</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <#list events as event>
            <tr>
                <td>${event.id}</td>
                <#assign  eventUser = c.findBy(users, "id", event.userId)!/>
                <#if eventUser?has_content>
                <td>${eventUser.login}</td>
                <#else>
                <td>${event.userId}</td>
                </#if>

                <td>${event.type}</td>
                <td>${event.creationTime}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
