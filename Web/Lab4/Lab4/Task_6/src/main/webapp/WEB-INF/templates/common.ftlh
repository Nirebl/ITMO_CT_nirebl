<#-- @ftlvariable name="uri" type="java.lang.String" -->
<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="stylesheet" href="/css/lab4style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/users", "name": "Users"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/invalid404", "name": "Invalid404"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a href="${item.href}"
                                <#if uri?starts_with(item.href)>
                                    class="marked-tab-name"
                                </#if>
                        >${item.name}</a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <section>
                <div class="header">Post ${post.getId()}</div>
                <div class="body">
                    <@post_content_process post=post short=true/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a href="/user?handle=${user.handle}" style="font-weight: bold; color:${user.color}; text-decoration: none">${user.name}</a>
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#macro post_content_process post short>
    <#if short && post.text?length gt 350>
        ${post.text[0..346]?replace("\n", "<br>")?no_esc}...
    <#else>
        ${post.text?replace("\n", "<br>")?no_esc}
    </#if>
</#macro>

<#macro post_presentation post short >
    <article>
        <div class="title">${post.title}</div>
        <#assign  postUser = c.findBy(users, "id", post.user_id)!/>
        <div class="information">By ${postUser.name}</div>
        <div class="body">
            <@post_content_process post=post short=short/>
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
</#macro>

<#function userPostNumber user_id>
    <#assign answer=0/>
    <#list posts as post>
        <#if post.user_id==user_id>
            <#assign answer++/>
        </#if>
    </#list>
    <#return answer/>
</#function>
