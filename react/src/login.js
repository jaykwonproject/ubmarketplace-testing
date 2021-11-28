import './App.css';
import './account_page.css';
import sha256 from 'js-sha256'
import React from 'react';
import { handleAPIError } from './errors';

class Login extends React.Component {
    constructor(props) {
        super(props);
        this.state = {userId: '', password: ''};
        this.changeUserId = this.changeUserId.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.goToRegistration = this.goToRegistration.bind(this);
    }
    
    changeUserId(event) {
        this.setState({userId: event.target.value});
    }

    changePassword(event) {
        this.setState({password: event.target.value})
    }

    handleSubmit(event) {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId: this.state.userId, password: sha256(this.state.password) })
        };
        
        fetch('/api/login', requestOptions)
            .then(response => {
                if (response.status !== 200) {
                    handleAPIError(response)
                } else {
                    response.json().then(data => {
                        localStorage.setItem("username", data.user.displayName);
                    });
                    localStorage.setItem("email", this.state.userId);
                    this.props.history.push({pathname:"/",state: {name: this.state.username}})
                }
            });
        
            event.preventDefault();
        
    }

    goToRegistration(event) {
        this.props.history.push('/register')
    }
    
    render() {
        return (
            <div>
                <h1>UB Marketplace</h1>
                <div className='panel login'>
                    <form onSubmit={this.handleSubmit}>
                        <label htmlFor="userId">Email</label>
                        <input type="text" name="userId" value={this.state.userId} onChange={this.changeUserId} />
                        <label htmlFor="password">Password</label>
                        <input type="password" name="password" value={this.state.password} onChange={this.changePassword} />

                        <div class="options">
                            <input type="submit" value="Submit" />

                            <button onClick={this.goToRegistration}> 
                                Register
                            </button>

                            <a href="forgot.html">
                                <input type="button" value="Forgot Username/Password" />
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}

export default Login;