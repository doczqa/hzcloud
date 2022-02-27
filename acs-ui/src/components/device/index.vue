<template>
  <div>
    <svg-icon icon-class="edit" style="width: 40px; height: 40px" v-if="status == 1"/>
    <svg-icon icon-class="select" style="width: 40px; height: 40px" v-if="status == 2"/>
  </div>
</template>

<script>
export default {
  name: "Device",
  props: {
    name: undefined,
    status: undefined,
  }
}
</script>

<style scoped>

</style>
