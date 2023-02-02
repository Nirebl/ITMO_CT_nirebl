<#import "commons.ftlh" as c>
<#import "commonPost.ftlh" as p>

<@c.page>
<#list user.posts as post>
    <@p.post post=post/>
</#list>
</@c.page>
