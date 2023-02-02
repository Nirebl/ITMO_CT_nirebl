<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#list posts as post>
        <#if post.user_id==user_id>
            <@c.post_presentation post=post short=false/>
        </#if>
    </#list>
</@c.page>
