import { useNavigate } from "react-router-dom";
import React from 'react'

import profilePic from "../images/icons/icons8-user-48.png"
import statisticPic from "../images/icons/icons8-graph-48.png"
import newsPic from "../images/icons/icons8-news-48.png"
import topicPic from "../images/icons/icons8-category-48.png"
import infoPic from "../images/icons/icons8-info-48.png"
import logoutPic from "../images/icons/icons8-logout-48.png"

import "./news/List.css"

export const LeftNavLocationNameEnum = {
    STATISTIC: "/",
    TOPIC: "/topic",
    NEWS: "/news",
    INFO: "/info",
    LOGOUT: "/logout"
};


const LeftNav = (props) => {
    const { user, logout, locationName } = props;

    const navigate = useNavigate();

    const navigatoToNews = () => {
        navigate(LeftNavLocationNameEnum.NEWS);
    }

    const navigatoToTopic = () => {
        navigate(LeftNavLocationNameEnum.TOPIC);
    }

    const navigatoToStatistic = () => {
        navigate(LeftNavLocationNameEnum.STATISTIC);
    }

    return (

        <div className="col-md-3 left_col">
            <div className="left_col scroll-view">
                <div className="navbar nav_title" style={{ border: 0 }}>
                    <a href="index.html" className="site_title">
                        <i className="fa fa-paw"></i>
                        <span>Web admin MyFPL!</span>
                    </a>
                </div>

                <div className="clearfix"></div>

                {/* <!-- menu profile quick info --> */}
                <div className="profile clearfix">
                    <div className="profile_pic">
                        <img src={profilePic} alt="..." className="img-circle profile_img" />
                    </div>
                    <div className="profile_info">
                        <span>Welcome,</span>
                        <h2>{user.name}</h2>
                    </div>
                </div>
                {/* <!-- /menu profile quick info --> */}

                <br />

                {/* <!-- sidebar menu --> */}
                <div id="sidebar-menu" className="main_menu_side hidden-print main_menu">
                    <div className="menu_section active">
                        <h3>General</h3>
                        <ul className="nav child_menu" style={{ display: "block" }}>
                            <li className={locationName === LeftNavLocationNameEnum.STATISTIC ? "current-page" : ""}
                                onClick={navigatoToStatistic}>
                                <img src={statisticPic} /> <button >Statistic</button>
                            </li>
                            <li className={locationName === LeftNavLocationNameEnum.TOPIC ? "current-page" : ""}
                                onClick={navigatoToTopic}>
                                <img src={topicPic} />  <button>Topic</button>
                            </li>
                            <li className={locationName === LeftNavLocationNameEnum.NEWS ? "current-page" : ""}
                                onClick={navigatoToNews}>
                                <img src={newsPic} />  <button >News</button>
                            </li>
                        </ul>
                    </div>

                    <div className="menu_section active">
                        <h3>Authication</h3>
                        <ul className="nav child_menu" style={{ display: "block" }}>
                            <li className={locationName === LeftNavLocationNameEnum.INFO ? "current-page" : ""}>
                                <img src={infoPic} />  <button>Info</button>
                            </li>
                            <li>
                                <img src={logoutPic} />  <button className="btn btn-danger" onClick={logout}>Logout</button>
                            </li>
                        </ul>
                    </div>

                </div>
                {/* <!-- /sidebar menu --> */}

            </div>
        </div>
    )
}

export default LeftNav