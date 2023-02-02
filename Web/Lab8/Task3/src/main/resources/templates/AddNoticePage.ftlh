<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeForm" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="add-notice form-box">
    <div class="header">Add new notice</div>
    <div class="body">
        <form method="post" action="/add-notice">
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <textarea id="content" name="content">${noticeForm.content!}</textarea>
                </div>
                <@c.error "noticeForm.content"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Add">
            </div>
        </form>
    </div>
    </div>
</@c.page>