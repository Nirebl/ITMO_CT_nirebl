<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle??>
        <#assign selectedUser = c.findBy(users, "handle", handle)!>
    <#elseif user_id??>
        <#assign  selectedUser = c.findBy(users, "id", user_id)!/>
    </#if>

    <div class="selected-user">
        <#if selectedUser?has_content>
            <div class="user-info">
                <div class="user-detail">
                    <@c.userlink selectedUser false/>
                    <ul>
                        <li>id: ${selectedUser.id}</li>
                        <li>handle: ${selectedUser.handle}</li>
                        <li>name: ${selectedUser.name}</li>
                        <li>posts count: <a href="/posts?user_id=${selectedUser.id}">${c.userPostNumber(selectedUser.id)}</a></li>
                    </ul>
                </div>
                <div class="user-photo">
                    <img src="/img/no-title.jpg" alt="There was profile picture">
                </div>
            </div>

            <div class="user-scrolling">
                <@make_scroll selectedUser/>
            </div>
        <#else>
            No such user
        </#if>
    </div>
</@c.page>

<#macro make_scroll user>
    <#assign index = users?seq_index_of(user)>
    <#if index != 0>
        <a href="/user?handle=${users[index - 1].handle}">←</a>
    <#else>
        ←
    </#if>

    <#if index != (users?size - 1)>
        <a href="/user?handle=${users[index + 1].handle}">→</a>
    <#else>
        →
    </#if>
</#macro>