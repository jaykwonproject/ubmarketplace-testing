import './App.css';
import './profile.css';
import profileImg from "./profile.png";
import {Link} from "react-router-dom";

function profile() {
    return (
        <div className="profile">
            <div className="header">
                <p className="title">
                    <Link to ="/">UB Marketplace
                    </Link>
                </p>
                <div className="dropdown">
                    <button className="dropbtn">Profile</button>
                    <div className="dropdown-content">
                        <Link to="/login">Login/Register</Link>
                        <Link to="/profile">View Profile</Link>
                    </div>
                </div>
            </div>
            <div className="content">

                <div className="body-Left">

                    <div className="profile_picture">
                        <img src={profileImg}/>
                    </div>

                </div>

                <div className="body-Right">

                    <div className="profile_info">
                        <p>First Name:</p>
                        <p>Last Name:</p>
                        <p>Email:</p>
                    </div>
                    <div className="profile_info2">
                        <p>Jay</p>
                        <p>Kwon</p>
                        <p>hyukjook@buffalo.edu</p>
                    </div>
                    <div className="editBtn">
                        <button>Edit</button>
                    </div>
                </div>


            </div>
        </div>

    );
}

export default profile;
