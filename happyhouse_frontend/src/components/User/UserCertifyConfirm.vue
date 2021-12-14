<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <div class="blank"></div>
    <b-row class="mb-4 text-center" align-h="center">
      <b-col cols="7">
        <!-- <span class="display-3">동네 인증</span> -->
        <b-card bg-variant="light" header="확인" class="text-center">
          <b-card-text>
            증빙 자료 제출이 완료되었습니다. 빠른 시일 내에 처리하겠습니다.<br />
            감사합니다.
          </b-card-text>
        </b-card>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-button class="button" :to="{ name: 'Main' }"> 홈으로 </b-button>
    </b-row>
  </b-container>
</template>

<script>
export default {};
</script>

<style>
.button {
  background-color: #759cc9;
  border-color: #759cc9;
  color: white;
}

.button:hover {
  background-color: white;
  border-color: #759cc9;
  color: #759cc9;
}

.button:focus {
  background-color: #759cc9;
  border-color: #759cc9;
  color: white;
}

.button:focus {
  outline: none !important;
}
</style>
