import React, { useState, useEffect } from "react";
import AxiosInstance from "../helper/AxiosInstance";

// import "bootstrap/dist/css/bootstrap.min.css";

import swal from 'sweetalert';
import LeftNav, { LeftNavLocationNameEnum } from "../LeftNav";

const List = (props) => {
    const { user, setUser, logout } = props;

    const [news, setNews] = useState([]);

    useEffect(() => {
        const getNews = async () => {
            try {
                const response = await AxiosInstance().get('/get-news.php');
                setNews(response.news);
            } catch (error) {
                console.log('Failed to fetch news', error);
            }

        }

        getNews();
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
                        const response = await AxiosInstance().delete("/delete-news.php?id=" + id);
                        if (response.status === true) {
                            swal("Xóa thành công", {
                                icon: "success",
                            });
                            // setTimeout(() => {
                            //     window.location.reload();
                            // }, 1000);
                            const _list = news.filter((item) => item.id !== id);
                            setNews(_list);
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

    const [newsStatistics, setNewsStatistics] = useState({
        message: '',
        total_news: 0,
        total_your_news: 0,
        total_news_this_month: 0
    });


    useEffect(() => {
        const fetchTopicStatistics = async () => {
            try {
                const response = await AxiosInstance().get('get-news-statistics.php?user_id='+ user.id);

                console.log(response);
                setNewsStatistics(response);

            } catch (error) {
                console.log('Failed to fetch statistics', error);
            }
        }
        fetchTopicStatistics();
    }, []);

    const handleSearch = async (e) => {
        try {
            const response = await AxiosInstance().get('/search-news-by-keyword.php?keyword=' + e.target.value);
            setNews(response.news);
        } catch (error) {
            console.log('Failed to fetch news', error);
        }
    }

    return (
        <div className="container">
            <LeftNav user={user} logout={logout} className="left_col scroll-view" locationName={LeftNavLocationNameEnum.NEWS}/>

            <div className="list right_col">

                <h1>News List</h1>

                <hr />

                <div className="row" style={{ display: "inline-block" }}>
                    <div className="tile_count">
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-user"></i> Total News</span>
                            <div className="count">
                                {
                                    newsStatistics.total_news
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green">4% </i> From last Week</span> */}
                        </div>
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-clock-o"></i>Number of Your news</span>
                            <div className="count">
                                {
                                    newsStatistics.total_your_news
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>3% </i> From last Week</span> */}
                        </div>
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-user"></i> Number of News This month</span>
                            <div className="count green">
                                {
                                    newsStatistics.total_news_this_month
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>34% </i> From last Week</span> */}
                        </div>

                    </div>
                </div>

                <hr />

                <div style={{marginBottom: 10 +"px"}}>
                    <a href="/news/add"> <button className="btn btn-success">Add News</button> </a>
                </div>

                <div style={{marginBottom: 20 +"px"}}>
                    {/* <label htmlFor="search">Search</label> */}
                    <input type="text" className="form-control" id="search" placeholder="Search" onChange={handleSearch}/>
                </div>

                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Content</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {news.map((news) => (
                            <tr key={news.id}>
                                <td>{news.id}</td>
                                <td>{news.title}</td>
                                <td>{news.content}</td>
                                <td style={{display: "flex", gap: 10, justifyContent: 'center'}}>
                                    <a href={"/news/update/" + news.id} > <button className="btn btn-primary">Edit</button></a>
                                    <button className="btn btn-danger" onClick={() => { handleDelete(news.id) }}>Delete</button>
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