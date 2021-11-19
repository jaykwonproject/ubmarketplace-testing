import {Link} from "react-router-dom";
import React, {Component} from 'react';
import "./Carousel.css";
import './home.css';
import Carousel from "./Carousel"
import Header from "./header";
import {handleAPIError} from "./errors";


class home extends Component{

    constructor(props) {
        super(props);
        this.state ={
            items:[],
            isLoaded: true,

        }
    }

    componentDidMount() {
        const requestOptions = {
            /*remove 'no-cors' once item insertion function gets finished*/
            method: "get",
            headers: {
                "Content-Type": "application/json"
            }

        };

        fetch("/api/allitem",requestOptions)
            .then(response=> {
                if (response.status !== 200) {
                    handleAPIError(response);

                } else {
                    response.json().then(data => {
                        console.log(data);
                        this.setState({
                            isLoaded: true,
                            items: data.item})
                    });
                }
            })
    }
    render() {

        var{isLoaded, items} = this.state;
        const email = localStorage.getItem("email");
        return (
            <div className="home">
                <Header email={email}/>
                <div className="itemList">
                    <Carousel  show={5} infiniteLoop>
                        {items.map(item=>(
                            <div className="itemImg">
                                <Link to={"/item/" + item.itemId}>
                                    <img src={item.images} alt={item.name}/>
                                </Link>
                                <p>{item.name}</p>
                                <p>${item.price}</p>

                            </div>
                        ))}
                    </Carousel>
                </div>

                <div className="categoryList">
                    <h2>Category List</h2>
                    <ul>
                        <li><Link to ="/electronics">Electronics</Link></li>
                        <li><Link to ="/textbooks">Textbooks</Link></li>
                        <li><Link to ="/clothings">Clothings</Link></li>
                        <li><Link to ="/furnitures">Furnitures</Link></li>
                    </ul>
                    <ul>
                        <li><Link to="/listing">See All Items</Link></li>
                        <li><Link to="/sell">Sell My Items</Link></li>
                    </ul>
                </div>
            </div>

        );
    }
}

export default home;
