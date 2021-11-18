import './App.css';
import './account_page.css';
import { handleAPIError } from './errors';
import sha256 from 'js-sha256'
import React from 'react';

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {userId: '', password: '', displayName: '', confirm: ''};
        this.changeUserId = this.changeUserId.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.changeConfirm = this.changeConfirm.bind(this);
        this.changeDisplayName = this.changeDisplayName.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    changeUserId(event) {
        this.setState({userId: event.target.value});
    }

    changePassword(event) {
        this.setState({password: event.target.value})
    }

    changeConfirm(event) {
        this.setState({confirm: event.target.value})
    }

    changeDisplayName(event) {
        this.setState({displayName: event.target.value})
    }

    handleSubmit(event) {
        event.preventDefault();
        if (this.state.password !== this.state.confirm) {
             alert("The provided passwords do not match!");
             return;
        }
        if (this.state.password.length < 8) {
            alert("Password provided is too small!")
            return;
        }
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userId: this.state.userId, password: sha256(this.state.password), displayName: this.state.displayName })
        };
        fetch('/api/register', requestOptions)
            .then(response => {
                if (response.status !== 200) {
                    handleAPIError(response);
                } else {
                    this.props.history.push('/login');
                }
            });
    }
    
    render() {
        return (
            <div>
                <h1>UB Marketplace</h1>
                <div className='panel login'>
                    <form onSubmit={this.handleSubmit}>
                        <label for="userId">Email</label>
                        <input type="text" name="userId" value={this.state.userId} onChange={this.changeUserId} />
                        <label for="displayName">Username</label>
                        <input type="text" name="displayName" value={this.state.displayName} onChange={this.changeDisplayName} />
                        <label for="password">Password</label>
                        <input type="password" name="password" value={this.state.password} onChange={this.changePassword} />
                        <label for="confirm">Confirm Password</label>
                        <input type="password" name="confirm" value={this.state.confirm} onChange={this.changeConfirm} />

                        <input type="submit" value="Submit" />
                    </form>
                </div>
            </div>
        );
    }
}

export default Register;