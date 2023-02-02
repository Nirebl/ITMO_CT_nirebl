<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <form method="post" action="">
        <div class="talks">
            <div class="caption">Send message from ${user.login}</div>

            <input type="hidden" name="action" value="add_message"/>

            <table>
                <thead>
                <tr>
                    <th>To whom</th>
                    <th>Message</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tr>
                    <td>
                        <div class="field">
                            <select name="select">
                                <#list users as usr>
                                    <#if user.login != usr.login>
                                        <option>${usr.login}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                    </td>
                    <td>
                        <textarea name="text" rows="2" cols="70"></textarea>
                    </td>
                    <td>
                        <input type="submit" value="Send">
                    </td>
                </tr>
            </table>
        </div>
    </form>
    <p/>
    <div class="talks">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Message</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
            <tr>
                <td>${talk.id}</td>

                <#assign  fromUser = c.findBy(users, "id", talk.sourceUserId)!/>
                <#if fromUser??>
                <td>${fromUser.login}</td>
                <#else>
                <td>Unknown user with id: ${talk.sourceUserId}</td>
                </#if>

                <#assign  targetUser = c.findBy(users, "id", talk.targetUserId)!/>
                <#if targetUser??>
                    <td>${targetUser.login}</td>
                <#else>
                    <td>Unknown user with id: ${talk.targetUserId}</td>
                </#if>

                <td class="talk">${talk.text}</td>
                <td>${talk.creationTime}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
