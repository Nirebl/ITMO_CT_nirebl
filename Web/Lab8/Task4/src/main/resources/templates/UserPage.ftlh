<#-- @ftlvariable name="userDetail" type="ru.itmo.wp.domain.User" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div>
        <#if userDetail??>
            <div>
                <p>Id: ${userDetail.id}</p>
                <p>Login: ${userDetail.login}</p>
                <p>Created at: ${userDetail.creationTime}</p>
            </div>
        <#else>
            No such user :(
        </#if>
    </div>
</@c.page>