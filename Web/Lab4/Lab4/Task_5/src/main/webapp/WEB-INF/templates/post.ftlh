<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if post_id ?? && post_id != -1>
        <#assign post = c.findBy(posts, "id", post_id)!>
        <@c.post_presentation post=post short=false/>
    <#else>
        <h1>404</h1>
        <p>No such post :(</p>
    </#if>
</@c.page>
