import http from "@/util/http-common";

class FileUploadService {
  upload(file, onUploadProgress) {
    let formData = new FormData();

    formData.append("file", file);

    return http.post("/user/file", formData, {
      headers: {
        "Content-type": "multipart/form-data",
      },
      onUploadProgress,
    });
  }

  // getFile(userid) {
  //   return http.get(`/user/file/${userid}`);
  // }
}

export default new FileUploadService();
