<template>
  <tr>
    <td class="id">{{user.id}}</td>
    <td class="login">{{user.login}}</td>
    <td class="Name">{{user.name}} </td>
  </tr>
</template>

<script>
export default {
  name: "UserDetailRow",
  props: ["user"]
}
</script>

<style scoped>

</style>