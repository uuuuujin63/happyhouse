<template>
  <div class="pt-5 p-st">
    <div
      class="container"
      data-aos="fade"
      data-aos-once="true"
      data-aos-duration="1000"
    >
      <div class="row align-items-center">
        <div class="col-xl-6 col-bg-6 col-md-6 col-sm-12 text-center">
          <img :src="picture" />
        </div>
        <div class="col-xl-6 col-bg-6 col-md-6 col-sm-12">
          <div class="home-title mb-4">HappyHouse에 어서오세요!</div>
          <div>
            <p v-html="description"></p>
          </div>
          <div class="text-center my-5">
            <button class="btn" @click="scrollToMap">
              <span>바로 알아보기</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import info from "@/../info";

export default {
  name: "Home",
  data() {
    return {
      picture: info.flat_picture,
      description: info.description,
    };
  },
  methods: {
    scrollToMap() {
      this.$emit("scrollToMap");
    },
  },
};
</script>

<style scoped>
.home-title {
  font-size: 40px;
  font-weight: 500;
}

img {
  max-width: 800px;
  max-height: 500px;
  margin-top: 80px;
}

@media only screen and (max-width: 580px) {
  img {
    object-fit: cover;
    border-radius: 50%;
    height: 200px;
    width: 200px;
    margin-top: 10px;
    margin-bottom: 10px;
    border: 2px solid rgb(205, 205, 205);
  }
}

.fa {
  font-size: 15px;
}

.btn {
  background-color: #759cc9;
  border-color: #759cc9;
  color: white;
}

.btn:hover {
  background-color: white;
  border-color: #759cc9;
  color: #759cc9;
}

.btn:focus {
  background-color: #759cc9;
  border-color: #759cc9;
  color: white;
}

.btn:focus {
  outline: none !important;
}

p {
  text-align: justify;
  font-weight: 400;
  font-size: 18px;
}

/* LEAVES */
</style>
