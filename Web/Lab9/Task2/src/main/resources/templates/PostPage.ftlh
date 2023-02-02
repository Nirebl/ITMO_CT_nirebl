<#-- @ftlvariable name="userId" type="java.lang.Long" -->
<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#import "commons.ftlh" as c>
<#import "commonPost.ftlh" as p>

<@c.page>
    <#if post??>
        <div class="body">
            <@p.post post/>
            <p/>
            <#if user??>
                <form method="post" action="">
                    <div class="new-comment">
                        <div class="caption">Write your comment:</div>
                        <table>
                            <tr>
                                <td>
                                    <div class="value">
                                        <textarea id="comment" name="comment">${commentForm.comment!}</textarea>
                                    </div>
                                    <@c.error "commentForm.comment"/>
                                </td>
                                <td>
                                    <input type="submit" value="Write">
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
                <p/>
            </#if>
            <#list post.comments as cmnt>
                <table class="comment">
                    <tbody>
                    <tr>
                        <th class="login">${cmnt.user.login}</th>
                        <th>${cmnt.text}</th>
                    </tr>
                    </tbody>
                </table>
            </#list>
        </div>
    <#else>
        No such post
    </#if>
</@c.page>