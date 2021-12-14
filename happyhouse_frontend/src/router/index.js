import Vue from "vue";
import VueRouter from "vue-router";

import Main from "@/views/Main.vue";
import QnA from "@/views/QnA.vue";
import User from "@/views/User.vue";
import Notice from "@/views/Notice.vue";
import Community from "@/views/Community.vue";

import QnAList from "@/components/QnA/QnAList.vue";
import Mypage from "@/components/User/Mypage.vue";
import Login from "@/components/User/Login.vue";
import Join from "@/components/User/Join.vue";
import FindPwd from "@/components/User/FindPwd.vue";
import UserCertify from "@/components/User/UserCertify.vue";
import UserCertifyConfirm from "@/components/User/UserCertifyConfirm.vue";

import NoticeDetail from "@/components/Notice/NoticeDetail.vue";
import NoticeList from "@/components/Notice/NoticeList.vue";
import NoticeModify from "@/components/Notice/NoticeModify.vue";
import NoticeWrite from "@/components/Notice/NoticeWrite.vue";

import ComuDetail from "@/components/Community/ComuDetail.vue";
import ComuList from "@/components/Community/ComuList.vue";
import ComuModify from "@/components/Community/ComuModify.vue";
import ComuWrite from "@/components/Community/ComuWrite.vue";

import store from "@/store/index.js";
Vue.use(VueRouter);

// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "SignIn" });
    router.push({ name: "Join" });
  } else {
    console.log("로그인 했다.");
    next();
  }
};
const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/qna",
    name: "QnA",
    component: QnA,
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnAList",
        component: QnAList,
      },
    ],
  },
  {
    path: "/notice",
    name: "Notice",
    component: Notice,
    redirect: "/notice/list",
    children: [
      {
        path: "/write",
        name: "NoticeWrite",
        component: NoticeWrite,
      },
      {
        path: "/modify/:idx",
        name: "NoticeModify",
        component: NoticeModify,
      },
      {
        path: "/detail/:idx",
        name: "NoticeDetail",
        component: NoticeDetail,
      },
      {
        path: "list",
        name: "NoticeList",
        component: NoticeList,
      },
    ],
  },
  {
    path: "/user",
    name: "User",
    component: User,
    children: [
      {
        path: "/login",
        name: "Login",
        component: Login,
      },
      {
        path: "/mypage",
        name: "Mypage",
        component: Mypage,
      },
      {
        path: "/join",
        name: "Join",
        component: Join,
      },
      {
        path: "/findpwd",
        name: "FindPwd",
        component: FindPwd,
      },
      {
        path: "/usercertify",
        name: "UserCertify",
        component: UserCertify,
      },
      {
        path: "/usercertifyconfirm",
        name: "UserCertifyConfirm",
        component: UserCertifyConfirm,
      },
    ],
  },
  {
    path: "/community",
    name: "Community",
    component: Community,
    redirect: "/community/list",
    children: [
      {
        path: "write",
        name: "ComuWrite",
        component: ComuWrite,
      },
      {
        path: "modify/:idx",
        name: "ComuModify",
        component: ComuModify,
      },
      {
        path: "detail/:idx",
        name: "ComuDetail",
        component: ComuDetail,
      },
      {
        path: "list",
        name: "ComuList",
        component: ComuList,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
