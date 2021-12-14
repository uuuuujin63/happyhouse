<template>
  <b-tbody>
    <b-tr>
      <b-td>{{ qna.q_no }}</b-td>
      <b-td>{{ qna.replied }}</b-td>
      <b-td @click="toggleView">{{ qna.q_title }}</b-td>
      <b-td>{{ qna.iduser }}</b-td>
      <b-td>{{ qna.regtime }}</b-td>
    </b-tr>
    <b-tr v-show="toggle">
      <b-th colspan="5">{{ qna.q_title }}</b-th>
    </b-tr>
    <b-tr v-show="toggle">
      <b-td colspan="5">{{ qna.q_content }}</b-td>
    </b-tr>
  </b-tbody>
</template>

<script>
export default {
  name: "QnAListRow",
  props: {
    qna: Object,
    toggle: Boolean,
  },
  methods: {
    toggleView() {
      this.$emit("toggleView");
    },
  },
};
</script>

<style></style>
