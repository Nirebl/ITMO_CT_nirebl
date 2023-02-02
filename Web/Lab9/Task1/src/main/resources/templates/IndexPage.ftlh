<#-- @ftlvariable name="posts" type="ru.itmo.wp.domain.Post[]" -->
<#import "commons.ftlh" as c>
<#import "commonPost.ftlh" as p>

<@c.page>
    <#list posts as post>
        <@p.post post=post/>
    </#list>
</@c.page>
