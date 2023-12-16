import React, { useEffect, useState } from "react";
import swal from 'sweetalert';

import AxiosInstance from "../helper/AxiosInstance";
import LeftNav, { LeftNavLocationNameEnum } from "../LeftNav";

const Add = (props) => {
  const { user, logout } = props;

  // Sử dụng state để lưu trữ thông tin của tin tức mới
  const [formData, setFormData] = useState({
    title: "",
    description: "",
  });

  // Xử lý khi người dùng nhập dữ liệu
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  // Xử lý khi người dùng gửi dữ liệu
  const handleSubmit = (e) => {
    e.preventDefault();
    // Thực hiện thêm tin tức vào cơ sở dữ liệu ở đây (cần kết nối với backend)

  };
  // xử lý hình ảnh
  const [previewImage, setPreviewImage] = useState(null);
  const [imageInput, setImageInput] = useState(null);


  const handleImage = (e) => {
    const file = e.target.files[0];
    setImageInput(file);

    // hiển thị hình ảnh
    setPreviewImage(URL.createObjectURL(file));

    // upload hình ảnh lên server lấy đường dẫn
    const formData = new FormData();
    formData.append('image', file);
    AxiosInstance().post('/upload-file.php', formData)
      .then(response => {
        setFormData({
          ...formData,
          image: response.path
        });
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
              name: formData.title,
              description: formData.description,
            };

            const response = await AxiosInstance().post("/insert-topic.php", body);
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
                window.location.href = LeftNavLocationNameEnum.TOPIC;
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
      <LeftNav user={user} logout={logout} className="left_col scroll-view" locationName={LeftNavLocationNameEnum.TOPIC} />

      <div className="list right_col">
        <div className="mb-8" style={{ width: 1000 + "px", margin: 'auto' }}>
          <h2>Add Topic</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Name:</label>
              <input
                type="text"
                name="title"
                className="form-control"
                value={formData.name}
                onChange={handleChange}
              />
            </div>

            <div className="form-group">
              <label>Description:</label>
              <textarea
                name="description"
                className="form-control"
                value={formData.description}
                onChange={handleChange}
              />
            </div>



            <button type="submit" onClick={handleAdd} className="btn btn-primary" style={{ margin: 20 + "px" }}>
              Add Topic
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Add;
