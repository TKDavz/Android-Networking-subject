import React, { useEffect, useState } from "react";
import swal from 'sweetalert';

import AxiosInstance from "../helper/AxiosInstance";
import LeftNav, { LeftNavLocationNameEnum } from "../LeftNav";

const Add = (props) => {
  const { user, logout } = props;


  // Sử dụng state để lưu trữ thông tin của tin tức mới
  const [formData, setFormData] = useState({
    title: "",
    content: "",
    topic_id: -1,
    user_id: user.id,
  });

  const [imagePath, setImagePath] = useState('');

  // Xử lý khi người dùng nhập dữ liệu
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
    console.log(formData);
  };

  // Xử lý khi người dùng gửi dữ liệu
  const handleSubmit = (e) => {
    e.preventDefault();
    // Thực hiện thêm tin tức vào cơ sở dữ liệu ở đây (cần kết nối với backend)

  };
  // xử lý hình ảnh
  const [previewImage, setPreviewImage] = useState(null);
  const [imageInput, setImageInput] = useState("");


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

        setImagePath(response.path);
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

  const handleAdd = async () => {
    swal({
      title: "Xác nhận thêm mới?",
      text: "Thêm mới dữ liệu vào hệ thống!",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    })
      .then(async (will) => {
        if (will) {
          try {
            const body = {
              title: formData.title,
              content: formData.content,
              image: imagePath,
              topic_id: formData.topic_id,
              user_id: user.id,
            };
            console.log(formData);
            console.log(body);
            const response = await AxiosInstance().post("/insert-news.php", body);
            console.log(response);
            // if (response.status === 201) {
            //   // Thêm tin tức thành công
            //   // Thực hiện điều hướng đến trang danh sách tin tức
            //   props.history.push("/news");
            // } 
            if (response.status === true) {
              swal("Thêm mới thành công", {
                icon: "success",
              });
              setTimeout(() => {
                window.location.href = LeftNavLocationNameEnum.NEWS;
              }, 2000);
            } else {
              swal("Lỗi", "Thêm mới không thành công", "error");
            }
          } catch (error) {
            console.log("Failed to add news", error);
            swal("Lỗi", "Thêm mới không thành công", "error");
          }

        } else {
          swal("Lỗi", "Thêm mới không thành công", "error");
        }
      });

  }

  return (
    <div className="container">
      <LeftNav user={user} logout={logout} className="left_col scroll-view" locationName={LeftNavLocationNameEnum.NEWS} />


      <div className="list right_col">
        <div className="mb-8" style={{ width: 1000 + "px", margin: 'auto' }}>
          <h2>Add News</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Title:</label>
              <input
                type="text"
                name="title"
                className="form-control"
                value={formData.title}
                onChange={handleChange}
              />
            </div>

            <div className="form-group">
              <label>Content:</label>
              <textarea
                name="content"
                className="form-control"
                value={formData.content}
                onChange={handleChange}
              />
            </div>

            <div className="form-group">
              <label>Image:</label>
              <input
                name="image"
                id="image"
                type="file"
                className="form-control"
                value={imageInput}
                onChange={(e) => { handleImage(e) }}
              />
            </div>
            <img src={previewImage} alt="" style={previewImage && { width: 400 + 'px', padding: 15 + "px", margin: 20 + "px", border: "1px solid blue", borderRadius: 20 + "px" }} />

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

            <button type="submit" onClick={handleAdd} className="btn btn-primary" style={{ margin: 20 + "px" }}>
              Add News
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Add;
