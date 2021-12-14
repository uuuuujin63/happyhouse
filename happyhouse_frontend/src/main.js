import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import AOS from "aos";
import "aos/dist/aos.css";
import VueParallaxJs from "vue-parallax-js";
import VueScrollTo from "vue-scrollto";
import VTooltip from "v-tooltip";

import { BootstrapVue, BootstrapVueIcons } from "bootstrap-vue";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

Vue.use(BootstrapVue);
Vue.use(BootstrapVueIcons);

var VueCookie = require("vue-cookie");

Vue.use(VTooltip);
Vue.use(VueScrollTo);
Vue.use(VueCookie);
Vue.use(VueParallaxJs);

Vue.config.productionTip = false;

new Vue({
  created() {
    AOS.init();
  },
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
