import './App.css';
import './account_page.css';
import { handleAPIError } from './errors';
import sha256 from 'js-sha256'
import React from 'react';
import profileImg from "./profile.png";
import Header from "./header";

class profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {userId: '', password: '', displayName: ''};
        this.changePassword = this.changePassword.bind(this);
        this.changeUserId = this.changeUserId.bind(this);
        this.changeDisplayName = this.changeDisplayName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    changeUserId(event) {
        this.setState({userId: event.target.value});
    }
    changeDisplayName(event) {
        this.setState({displayName: event.target.value})
    }
    changePassword(event) {
        this.setState({password: event.target.value})
    }
    handleSubmit(event) {
        event.preventDefault();
        console.log(this.state.userId);
        if (this.state.password.length < 8) {
            alert("Password provided is too small!")
            return;
        }
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: this.state.userId, password: sha256(this.state.password), displayName: this.state.displayName })
        };
        fetch('/api/profileUpdate', requestOptions)
            .then(response => {
                if (response.status !== 200) {
                    handleAPIError(response);
                } else {
                    this.props.history.push('/profile');
                }
            });
    }

    render() {

        const email = localStorage.getItem("email");
        return (
            <div className="profile">
                <Header email={email}/>
                <div className="content">
                    <div className="body-Left">
                        <div className="profile_picture">
                            <img src={profileImg}/>
                        </div>
                    </div>
                    <div className='panel login'>
                        <form onSubmit={this.handleSubmit}>
                            <label htmlFor="userId">Current Email</label>
                            <input type="text" name="userId" value={this.state.userId} onChange={this.changeUserId}/>
                            <label htmlFor="password">Current Password</label>
                            <input type="text" name="password" value={this.state.password} onChange={this.changePassword}/>
                            <label htmlFor="displayName">Change Username</label>
                            <input type="text" name="displayName" value={this.state.displayName} onChange={this.changeDisplayName}/>
                            <input type="submit" value="Submit"/>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

}
export default profile;

