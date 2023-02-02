<template>
  <div class="users datatable">
    <div class="caption">User</div>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
      <UserDetailRow v-for="user in users" :user="user" :key="user.id"/>
      </tbody>
    </table>
  </div>
</template>

<script>
import UserDetailRow from "./details/UserDetailRow";
import axios from 'axios';

export default {
  name: "Users",
  components: {
    UserDetailRow
  },
  data: function () {
    return {
      users: []
    }
  },
  beforeCreate() {
    axios.get('/api/1/users').then(response => {
      this.users = response.data
    })
  }
}

</script>

<style scoped>

</style>