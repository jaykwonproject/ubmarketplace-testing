import {Link} from "react-router-dom";
import React from "react";


const Header = ({email}) =>{
    if(email=="null" || email===null){
        return <Link className="header">
            <p className="title"><Link to="">UB Marketplace</Link></p>
            <div className="dropdown">
                <button className="dropbtn">Profile</button>
                <div className="dropdown-content">
                    <Link to="/login">Login/Register</Link>
                    <Link to="/profile">View Profile</Link>
                </div>
            </div>
        </Link>;
    }
    else{
        return <Link className="header">
            <p className="title"><Link to="">UB Marketplace</Link></p>
            <div className="dropdown">
                <button className="dropbtn">{email}</button>
                <div className="dropdown-content">
                    <Link to="/login">Login/Register</Link>
                    <Link to="/profile">View Profile</Link>
                    <Link to="/" onClick={() => { localStorage.setItem("email", null); window.location.reload();}}>Logout</Link>
                </div>
            </div>
        </Link>;
    }


};

export default Header