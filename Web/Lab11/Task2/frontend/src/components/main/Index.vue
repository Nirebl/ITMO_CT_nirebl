<template>
  <div>
    <PostDetail v-for="post in posts"
                :post="post"
                :key="post.id"/>
  </div>
</template>

<script>
import PostDetail from "./details/PostDetail";
import axios from "axios";

export default {
  name: "Index",
  components: {
    PostDetail
  },
  data: function () {
    return {
      posts: []
    }
  },
  beforeCreate() {
    axios.get('/api/1/posts').then(response => {
      this.posts = response.data
    })
  }

}
</script>

<style scoped>

</style>