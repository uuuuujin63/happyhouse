<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <div class="blank"></div>
    <b-row class="mb-4 text-center" align-h="center">
      <b-col cols="7">
        <!-- <span class="display-3">동네 인증</span> -->
        <b-card bg-variant="light" header="공지" class="text-center">
          <b-card-text
            >커뮤니티활동을 위해서는 <strong>동네 인증</strong>이 필요합니다.<br />
            주소가 잘 보이도록 신분증 사진을 올려주세요.<br />
            신속하게 처리해드리겠습니다.<br />
            (※ 신분증 인정 : 주민등록증, 운전면허증, 여권)
          </b-card-text>
        </b-card>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="5">
        <b-form-group label="신분증 :" label-cols-sm="2">
          <b-form-file
            id="file-default"
            accept="image/*"
            ref="file"
            @change="selectImage"
          ></b-form-file>
        </b-form-group>
        <div v-if="previewImage">
          <div>
            <img
              class="preview my-3"
              :src="previewImage"
              alt=""
              style="max-width: 440px"
            />
          </div>
        </div>
        <div v-if="message" class="alert alert-secondary" role="alert">
          {{ message }}
        </div>
        <div style="text-align: center">
          <b-button
            type="button"
            variant="primary"
            @click="cancel"
            style="margin: 10px"
          >
            취소
          </b-button>
          <b-button type="button" variant="success" @click="register">
            등록
          </b-button>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import UploadService from "@/util/FileUploadService";

export default {
  name: "UserCertify",
  data() {
    return {
      currentImage: undefined,
      previewImage: undefined,

      progress: 0,
      message: "",

      imageInfo: null,
    };
  },
  computed: {},
  methods: {
    register() {
      // this.$store.dispatch("comuStore/"); 옮길거면 나중에
      this.progress = 0;

      UploadService.upload(this.currentImage, (event) => {
        this.progress = Math.round((100 * event.loaded) / event.total);
      })
        .then((response) => {
          this.message = response.data.message;
          // return UploadService.getFile();
        })
        .then((image) => {
          // this.imageInfo = image.data;
          this.$router.push({ name: "UserCertifyConfirm" });
        })
        .catch((err) => {
          this.progress = 0;
          this.message = "이미지를 업로드하지 못했습니다." + err;
          this.currentImage = undefined;
        });
    },
    cancel() {
      this.$router.push({ name: "Main" });
    },
    selectImage(event) {
      console.log(event.target);
      this.currentImage = event.target.files[0];
      this.previewImage = URL.createObjectURL(this.currentImage);
      this.progress = 0;
      this.message = "";
    },
  },
  mounted() {
    // let userid = this.$store.state.memberStore.userinfo;
    // UploadService.getFile().then((response) => {
    //   this.imageInfo = response.data;
    // });
  },
};
</script>

<style></style>
