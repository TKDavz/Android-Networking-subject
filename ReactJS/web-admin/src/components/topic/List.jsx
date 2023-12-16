import React, { useState, useEffect } from "react";
import AxiosInstance from "../helper/AxiosInstance";

// import "bootstrap/dist/css/bootstrap.min.css";


import swal from 'sweetalert';
import LeftNav, { LeftNavLocationNameEnum } from "../LeftNav";

const List = (props) => {

    const { user, setUser, logout } = props;

    const [topics, setTopics] = useState([]);

    useEffect(() => {
        const getTopics = async () => {
            try {
                const response = await AxiosInstance().get('/get-topics.php');
                setTopics(response.topics);
            } catch (error) {
                console.log('Failed to fetch news', error);
            }

        }

        getTopics();
    }, []);



    const handleDelete = async (id) => {
        swal({
            title: "Xác nhận xóa?",
            text: "Xóa dữ liệu khỏi hệ thống!",
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then(async (will) => {
                if (will) {
                    try {
                        const response = await AxiosInstance().delete("/delete-topic.php?topic_id=" + id);
                        if (response.status === true) {
                            swal("Xóa thành công", {
                                icon: "success",
                            });
                            // setTimeout(() => {
                            //     window.location.reload();
                            // }, 1000);
                            const _list = topics.filter((item) => item.id !== id);
                            setTopics(_list);
                        } else {
                            swal("Xóa thất bại", {
                                icon: "error",
                            });
                        }
                    } catch (error) {
                        console.log("Failed to delete news", error);
                    }
                }
            });
    }

    const [topicStatistics, setTopicStatistics] = useState({
        message: '',
        total_topics: 0,
        total_topics_this_month: 0
    });


    useEffect(() => {
        const fetchTopicStatistics = async () => {
            try {
                const response = await AxiosInstance().get('get-topic-statistics.php');

                console.log(response);
                setTopicStatistics(response);

            } catch (error) {
                console.log('Failed to fetch statistics', error);
            }
        }
        fetchTopicStatistics();
    }, []);

    const handleSearch = async (e) => {
        try {
            const response = await AxiosInstance().get('/search-topics-by-keyword.php?keyword=' + e.target.value);
            setTopics(response.topics);
        } catch (error) {
            console.log('Failed to fetch news', error);
        }
    }

    return (
        <div className="container">
            <LeftNav user={user} logout={logout} className="left_col scroll-view" locationName={LeftNavLocationNameEnum.TOPIC} />

            <div className="list right_col">

                <h1>Topic List</h1>

                <hr />

                <div className="row" style={{ display: "inline-block" }}>
                    <div className="tile_count">
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-user"></i> Total Topic</span>
                            <div className="count">
                                {
                                    topicStatistics.total_topics
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green">4% </i> From last Week</span> */}
                        </div>
                        {/* <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-clock-o"></i>Total Your News</span>
                            <div className="count">
                                {
                                    topics.filter((item) => item.user_id === user.id).length
                                }
                            </div>
                            // {/* <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>3% </i> From last Week</span> *
                        </div> */}
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-user"></i> Number of Topics This Month</span>
                            <div className="count green">
                                {
                                    topicStatistics.total_topics_this_month
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>34% </i> From last Week</span> */}
                        </div>

                    </div>
                </div>

                <hr />

                <div style={{ marginBottom: 10 + "px" }}>
                    <a href="/topic/add"> <button className="btn btn-success">Add Topic</button> </a>
                </div>

                <div style={{ marginBottom: 20 + "px" }}>
                    {/* <label htmlFor="search">Search</label> */}
                    <input type="text" className="form-control" id="search" placeholder="Search" onChange={handleSearch}/>
                </div>

                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {topics.map((topic) => (
                            <tr key={topic.id}>
                                <td>{topic.id}</td>
                                <td>{topic.name}</td>
                                <td>{topic.description}</td>
                                <td style={{ display: "flex", gap: 10, justifyContent: 'center' }}>
                                    <a href={"/topic/update/" + topic.id} > <button className="btn btn-primary">Edit</button></a>
                                    <button className="btn btn-danger" onClick={() => { handleDelete(topic.id) }}>Delete</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default List;