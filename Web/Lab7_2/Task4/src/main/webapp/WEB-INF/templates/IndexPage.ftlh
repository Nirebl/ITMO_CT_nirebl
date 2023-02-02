<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <div class="status">
            No data
        </div>

        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body"></div>

                <ul class="attachment">
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
                    <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
                </ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        2 days ago
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
    </div>

    <script>
        $(function () {
            const $articles = $(".articles");
            $articles.find(".status").text("Loading...");

            lab7_ajax({
                    action: "findAllVisible"
                },
                function (response) {
                    $articles.find(".status").remove();
                    response["articles"].forEach(function (article) {
                        const $post = $($articles.find("template").prop("content")).clone();

                        $post.find(".title").text(article["title"]);
                        $post.find(".body").text(article["text"]);
                        $articles.append($post);
                    });
                }
            );
        });
    </script>


</@c.page>
