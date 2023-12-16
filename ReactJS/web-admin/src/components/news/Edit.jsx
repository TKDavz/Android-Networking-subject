import React, { useState, useEffect } from "react";
import swal from 'sweetalert';
import { useParams } from "react-router-dom";
import AxiosInstance from "../helper/AxiosInstance";
import LeftNav, { LeftNavLocationNameEnum } from "../LeftNav";

const Edit = (props) => {
  const { id } = useParams();
  const { user, logout } = props;

  // Sử dụng state để lưu trữ thông tin của tin tức mới
  const [formData, setFormData] = useState({
    title: "",
    content: "",
    image: "",
    topic_id: -1,
    user_id: user.id,
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Gửi dữ liệu formData lên máy chủ hoặc thực hiện cập nhật dữ liệu trong cơ sở dữ liệu ở đây
    // Sau khi thành công, bạn có thể xử lý điều gì đó (ví dụ: điều hướng đến trang khác)
  };
  // xử lý hình ảnh
  const [previewImage, setPreviewImage] = useState(null);
  const [imageInput, setImageInput] = useState(null);


  const handleImage = (e) => {
    const file = e.target.files[0];
    setImageInput(e.target.value);

    // hiển thị hình ảnh
    setPreviewImage(URL.createObjectURL(file));

    // upload hình ảnh lên server lấy đường dẫn
    const bodyFormData = new FormData();
    bodyFormData.append('image', file);
    AxiosInstance('multipart/form-data').post('/upload-file.php', bodyFormData)
      .then(response => {

        // setImagePath(response.path);
        handleChange({ target: { name: "image", value: response.path } });
        console.log("response.path", response.path);

      })
      .catch(error => {
        console.log('Failed to upload image', error);
      });
  }

  // danh sách chủ đề
  const [topics, setTopics] = useState([]);

  useEffect(() => {
    const getTopics = async () => {
      try {
        const response = await AxiosInstance().get("/get-topics.php");
        setTopics(response.topics);
        handleChange({ target: { name: "topic_id", value: response.topics[0].id } });
      } catch (error) {
        console.log("Failed to fetch topics", error);
      }
    };

    getTopics();
  }, []);

  // Lấy thông tin tin tức cần sửa
  useEffect(() => {
    const getNews = async () => {
      try {
        const response = await AxiosInstance().get("/get-news-detail.php?news_id=" + id);
        console.log(response);
        setFormData(response.news);
        setPreviewImage(response.news.image + "");
        // setImageInput(response.news.image.file);
      } catch (error) {
        console.log("Failed to fetch news", error);
      }
    };

    getNews();
  }, [id]);


  const handleUpdate = async () => {
    swal({
      title: "Xác nhận thay đổi?",
      text: "Thay đổi dữ liệu vào hệ thống!",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    })
      .then(async (will) => {
        if (will) {
          try {
            const body = {
              id: id,
              title: formData.title,
              content: formData.content,
              image: formData.image,
              topic_id: formData.topic_id,
              user_id: user.id,
            };
            console.log(formData);

            const response = await AxiosInstance().post("/update-news.php?id="+id, body);
            // if (response.status === 201) {
            //   // Thêm tin tức thành công
            //   // Thực hiện điều hướng đến trang danh sách tin tức
            //   props.history.push("/news");
            // } 
            if (response.status === true) {
              swal("Thay đổi thành công", {
                icon: "success",
              });
              setTimeout(() => {
                window.location.href = LeftNavLocationNameEnum.NEWS;
              }, 2000);
            } else {
              swal("Lỗi", "Thay đổi không thành công", "error");
            }
          } catch (error) {
            console.log("Failed to add news", error);
            swal("Lỗi", "Thay đổi không thành công", "error");
          }

        } else {
          swal("Lỗi", "Hủy thay đổi", "error");
        }
      });

  }

  return (
    <div className="container">
      <LeftNav user={user} logout={logout} className="left_col scroll-view" locationName={LeftNavLocationNameEnum.NEWS} />

      <div className="list right_col">
        <h2>Edit Data</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="title">Title</label>
            <input
              type="text"
              className="form-control"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="content">Content</label>
            <input
              type="text"
              className="form-control"
              id="content"
              name="content"
              value={formData.content}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label>Image:</label>
            <input
              name="image"
              type="file"
              className="form-control"
              value={imageInput}
              onChange={(e) => { handleImage(e) }}
            />
          </div>
          <img src={previewImage} alt="" style={previewImage && { width: 400 + 'px', padding: 15 + "px", margin: 20 + "px", border: "1px solid blue", borderRadius: 20 + "px" }} />
          {/* <div className="form-group">
          <label htmlFor="topic_id">Topic ID</label>
          <input
            type="number"
            className="form-control"
            id="topic_id"
            name="topic_id"
            value={formData.topic_id}
            onChange={handleChange}
            required
          />
        </div> */}

          <div className="form-group">
            <label>Topic ID:</label>
            <select
              name="topic_id"
              className="form-control"
              value={formData.topic_id}
              onChange={handleChange}>
              {topics.map((topic) => (
                <option key={topic.id} value={topic.id}>
                  {topic.name}
                </option>
              ))}
            </select>
          </div>

          <button type="submit" onClick={handleUpdate} className="btn btn-primary" style={{ margin: 20 + "px" }}>
            Save Changes
          </button>
        </form>
      </div>
    </div>
  );
};

export default Edit;
