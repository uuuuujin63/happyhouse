<template>
  <div>
    <b-container>
      <div class="blank"></div>
      <b-row class="mt-3">
        <b-col cols="4">
          <span class="display-3">QnA </span>
          <span class="h5"> 문의사항</span>
        </b-col>
      </b-row>
    </b-container>
    <router-view />
  </div>
</template>

<script>
export default {};
</script>

<style>
.blank {
  width: 100%;
  height: 100px;
}
</style>
