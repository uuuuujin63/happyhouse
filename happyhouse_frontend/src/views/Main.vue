<template>
  <div class="parent">
    <Home v-on:scrollToMap="scrollToMap" />
    <Map id="housemap" />
    <Information v-on:scrollToMap="scrollToMap" />
    <Advertisement />
    <Footer />
  </div>
</template>

<script>
import Home from "@/components/Main/Home";
import Map from "@/components/Main/Map";
import Information from "@/components/Main/Information";
import Advertisement from "@/components/Main/Advertisement";
import Footer from "@/components/Main/Footer";

import info from "@/../info";

export default {
  name: "Main",
  components: {
    Home,
    Map,
    Information,
    Advertisement,
    Footer,
  },
  data() {
    return {
      config: info.config,
    };
  },
  methods: {
    scrollToMap() {
      var elementPosition = document.getElementById("housemap").offsetTop;
      window.scrollTo({ top: elementPosition - 35, behavior: "smooth" });
    },
  },
};
</script>

<style></style>
