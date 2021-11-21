import {Link} from "react-router-dom";
import React, {Component} from 'react';
import "./Carousel.css";
import './home.css';
import Carousel from "./Carousel"
import Header from "./header";
import {handleAPIError} from "./errors";
import {Button} from "react-bootstrap";


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
                        <ul2>
                            <Link to = "/electronics">
                                <Button>Electronics</Button>
                            </Link>
                            <Link to = "/textbooks">
                                <Button>Textbooks</Button>
                            </Link>
                            <Link to = "/clothings">
                                <Button>Clothings</Button>
                            </Link>
                            <Link to = "/furnitures">
                                 <Button>Furnitures</Button>
                             </Link>
                        </ul2>
                        <ul2>
                            <Link to = "/listing">
                                <li><button2>See All Items</button2></li>
                            </Link>
                             <Link to = "/sell">
                                 <li><button2>Sell My Items</button2></li>
                             </Link>
                        </ul2>
                    </ul>
                </div>
            </div>
        );
    }
}

export default home;
