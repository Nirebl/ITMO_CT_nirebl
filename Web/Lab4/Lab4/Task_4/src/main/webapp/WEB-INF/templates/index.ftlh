<#-- @ftlvariable name="posts" type="ru.itmo.web.hw4.model.Post[]" -->
<#-- @ftlvariable name="postUser" type="ru.itmo.web.hw4.model.User" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <article>
            <div class="title">${post.title}</div>
            <#assign  postUser = c.findBy(users, "id", post.user_id)!/>
            <div class="information">By ${postUser.name}</div>
            <div class="body">
                <#if post.text?length gt 350>
                    ${post.text[0..346]?replace("\n", "<br>")?no_esc}...
                <#else>
                    ${post.text?replace("\n", "<br>")?no_esc}
                </#if>
            </div>
            <div class="footer">
                <div class="left">
                    <img src="/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="/img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    2 days ago
                    <img src="/img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">68</a>
                </div>
            </div>
        </article>
    </#list>
</@c.page>
