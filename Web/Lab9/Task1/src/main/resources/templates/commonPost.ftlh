<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "/spring.ftl" as spring/>

<#macro post post>
    <article>
        <div class="title">
<#--            ${post.title}-->
            <a href="<@spring.url '/post/${post.id}/' />" class="post-link">${post.title}</a>
        </div>
        <div class="information">By ${post.user.login}, ${post.creationTime}</div>
        <div class="body">${post.text}</div>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="<@spring.url '/img/voteup.png'/>" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="<@spring.url '/img/votedown.png'/>" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="<@spring.url '/img/date_16x16.png'/>" title="Publish Time" alt="Publish Time"/>
                ${post.creationTime}
                <img src="<@spring.url '/img/comments_16x16.png'/>" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>
