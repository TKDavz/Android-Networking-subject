import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from 'react'
import { Doughnut, Bar } from "react-chartjs-2";

import LeftNav, { LeftNavLocationNameEnum } from "./LeftNav";
import AxiosInstance from "./helper/AxiosInstance";
import BarChart from "./charts/BarChart";

const Index = (props) => {
    const { user, setUser, logout } = props;
    const navigate = useNavigate();

    const navigatoToNews = () => {
        navigate("/news");
    }

    const navigatoToTopic = () => {
        navigate("/topic");
    }

    const navigatoToStatistic = () => {
        navigate("/");
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
                const response = await AxiosInstance().get('/get-news-statistics.php?user_id=' + user.id);

                console.log(response);
                setNewsStatistics(response);

            } catch (error) {
                console.log('Failed to fetch statistics', error);
            }
        }
        fetchTopicStatistics();
    }, []);

    const [topicStatistics, setTopicStatistics] = useState({
        message: '',
        total_topics: 0,
        total_topics_this_month: 0
    });


    useEffect(() => {
        const fetchTopicStatistics = async () => {
            try {
                const response = await AxiosInstance().get('/get-topic-statistics.php');

                console.log(response);
                setTopicStatistics(response);

            } catch (error) {
                console.log('Failed to fetch statistics', error);
            }
        }
        fetchTopicStatistics();
    }, []);

    const [topicSta, setTopicSta] = useState({
        message: '',
        newsOfTopics: [],
        top3Topics: []
    });


    useEffect(() => {
        const fetchTopicSta = async () => {
            try {
                const response = await AxiosInstance().get('/get-topics-sta.php');

                console.log(response);
                setTopicSta(response);

            } catch (error) {
                console.log('Failed to fetch statistics', error);
            }
        }
        fetchTopicSta();
    }, []);


    return (
        <div className="container" >
            <LeftNav user={user} logout={logout} className="left_col scroll-view" locationName={LeftNavLocationNameEnum.STATISTIC} />

            <div className="list right_col">

                <h1>Statistic</h1>

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
                            <span className="count_top"><i className="fa fa-clock-o"></i>Total Your News</span>
                            <div className="count">
                                {
                                    newsStatistics.total_your_news
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>3% </i> From last Week</span> */}
                        </div>
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-user"></i> Total News Mounth</span>
                            <div className="count green">
                                {
                                    newsStatistics.total_news_this_month
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green"><i className="fa fa-sort-asc"></i>34% </i> From last Week</span> */}
                        </div>
                        <div className="col-md-2 col-sm-4  tile_stats_count">
                            <span className="count_top"><i className="fa fa-user"></i> Total Topic</span>
                            <div className="count">
                                {
                                    topicStatistics.total_topics
                                }
                            </div>
                            {/* <span className="count_bottom"><i className="green">4% </i> From last Week</span> */}
                        </div>

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

                <div className="col-md-6 col-sm-6 " style={{display: "flex", flexDirection: "row", justifyContent: "space-between", alignItems: "center", width: "100%", marginTop: 150 + "px"}}>
                    <div className="x_panel" style={{width: "45%"}}>
                        <div className="x_title">
                            <h2>Top 3 Topics</h2>
                            <div className="clearfix"></div>
                        </div>
                        <div className="x_content">
                            <table className="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Topic</th>
                                        <th>Number of News</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        topicSta?.top3Topics?.map((topic) => (
                                            <tr key={topic.id}>
                                                <td>{topic.name}</td>
                                                <td>{topic.total_news}</td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div className="x_panel" style={{width: "45%"}}>
                        <div className="x_title">
                            <h2>News of Topics</h2>
                            <div className="clearfix"></div>
                        </div>
                        <div className="x_content">
                            <table className="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Topic</th>
                                        <th>Number of News</th>
                                        <th>Percentage </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        topicSta?.newsOfTopics?.map((topic) => (
                                            <tr key={topic.id}>
                                                <td>{topic.name}</td>
                                                <td>{topic.total_news}</td>
                                                <td>{(topic.total_news / newsStatistics.total_news * 100).toFixed(2) }%</td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div >
    )
}
// npm install react-chartjs-2 chart.js
export default Index