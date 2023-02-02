<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.post_presentation post=post short=true/>
    </#list>
</@c.page>
