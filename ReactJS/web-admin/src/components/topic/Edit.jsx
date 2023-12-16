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
    name: "",
    description: "",
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


  // Lấy thông tin tin tức cần sửa
  useEffect(() => {
    const getTopic = async () => {
      try {
        const response = await AxiosInstance().get("/get-topic-detail.php?topic_id=" + id);
        console.log(response);
        setFormData(response.topic);
      } catch (error) {
        console.log("Failed to fetch news", error);
      }
    };

    getTopic();
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
              name: formData.name,
              description: formData.description,
            };

            const response = await AxiosInstance().post("/update-topic.php", body);
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
                window.location.href = LeftNavLocationNameEnum.TOPIC;
              }, 2000);
            } else {
              swal("Lỗi", "Thay đổi không thành công", "error");
            }
          } catch (error) {
            console.log("Failed to add news", error);
            swal("Lỗi", "Thay đổi không thành công", "error");
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
        <h2>Edit Data</h2>
        <form onSubmit={handleSubmit}>

          <div className="form-group">
            <label>Name:</label>
            <input
              type="text"
              name="name"
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

          <button type="submit" onClick={handleUpdate} className="btn btn-primary" style={{ margin: 20 + "px" }}>
            Save Changes
          </button>
        </form>
      </div>
    </div>
  );
};

export default Edit;
