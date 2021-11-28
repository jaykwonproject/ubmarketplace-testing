import React, {useEffect} from 'react';
import axios from 'axios'
import './listing.css';
import './App.css';
import './profile.css';
import profileImg from "./profile.png";
import {Link} from "react-router-dom";
import Header from "./header";

const profile = () =>{



    const email = localStorage.getItem("email");
    const displayName = localStorage.getItem("username");


    return (
        <div className="profile">
            <Header email={email}/>
            <div className="content">
                <div className="body-Left">
                    <div className="profile_picture">
                        <img src={profileImg}/>
                    </div>
                </div>
                <div className="body-Right">
                    <div className="profile_info">
                        <p>Username:</p>
                        <p>Email:</p>
                    </div>
                    <div className="profile_info2">
                        <p>{displayName}</p>
                        <p>{email}</p>
                    </div>
                </div>
                <Link to={"/profileUpdate"}>
                    <button>Edit</button>
                </Link>
            </div>
        </div>
    );
}

export default profile;
