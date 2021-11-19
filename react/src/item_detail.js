import {Link} from "react-router-dom";
import React, {Component} from 'react';
import './item_detail.css';
import { handleAPIError } from "./errors";


class Item_Detail extends Component{
    constructor(props) {
        super();
        this.state ={
            isLoaded: false,
            item: null,
            relatedItems: null,
            id: null
        }
    }
    componentDidMount() {
        const id = this.props.match.params.id;
        this.setState({
            id: id
        })
        const requestOptions = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }

        };

        fetch(`/api/getItem/${id}`, requestOptions)
            .then(res=>{
                if (res.status !== 200) {
                    handleAPIError(res);
                } else {
                    res.json().then(data => {
                        const item = data.item;
                        this.setState({
                            item: item
                        })
                    })
                }
            })

        fetch('/api/allitem',requestOptions)
            .then(res=>{
                if (res.status !== 200) {
                    handleAPIError(res);
                } else {
                    res.json().then(data => {
                        const items = data.item;
                        const category = this.state.item.category;
                        const tmp = items.filter(item => item.category == category);
                        let tmp2 = tmp.filter(item => item.itemId !== id);
                        let randoms = [];

                        if(tmp2.length > 5){
                            while(randoms.length < 5){
                                let tmpItem = tmp2[Math.floor(Math.random() * tmp2.length)];
                                if(!randoms.includes(tmpItem)){
                                    randoms.push(tmpItem);
                                }
                            }
                            tmp2 = randoms;
                        }

                        let otherItems = tmp2;

                        this.setState({
                            relatedItems: otherItems,
                            isLoaded: true
                        })
                    });
                }
            });
    }

    render() {
        let{isLoaded,item,relatedItems} = this.state;

        let editLink = `/item/edit/${this.state.id}`;

        const related = relatedItems ? relatedItems.map(item=>(
            <div className="itemImg">
                <a href={'#/item/'+item.itemId.toString()} target="_blank">
                    <img src={item.images[0]} alt={item.name} />
                </a>
                <p>{item.name}</p>
                <p>${item.price}</p>
            </div>
        )) : null;

            return (
                <div className="home">
                    <div className="header">
                        <Link to="/">
                            <p className="title">UB Marketplace</p>
                        </Link>
                        <div className="dropdown">
                            <button className="dropbtn"></button>
                            <div className="dropdown-content">
                                <Link to="/login">Login/Register</Link>
                                <Link to="/profile">View Profile</Link>
                                <Link to="/">Logout</Link>
                            </div>
                        </div>
                    </div>
                    {item ?
                        <div className="itemInfo">
                            <div className="itemLeft">
                                <div className="itemThumbnail">
                                    <img src={item.images[0]} alt={item.name} />
                                </div>
                            </div>
                            <div className="itemRight">
                                <div className="itemName">
                                    <h2>{item.name} {item.owner.userId === localStorage.getItem("email") ? (<Link to={editLink}>Edit Item</Link>) : ''}</h2>
                                </div>
                                <div className="itemPrice">
                                    <h3>Price: ${item.price}</h3>
                                </div>
                                <div className="itemLocation">
                                    <h3>Available Meetup Location</h3>
                                    <button>{item.meetingPlace}</button>
                                </div>
                                <h3 className="descriptionTitle">Item Description</h3>
                                <div className="itemDescription">
                                    {item.description}
                                </div>
                                <h3 className="descriptionTitle">Contact Info</h3>
                                <div className="itemContact">
                                    {item.contactPhoneNumber ? `${item.owner.userId} || ${item.contactPhoneNumber}` : item.owner.userId }
                                </div>
                            </div>
                        </div>
                        :
                        <p>Loading...</p>
                    }
                    { related ?
                        <div className="relatedList">
                            <div className="relatedItems">
                                <h3>Related Items</h3>
                            </div>
                            {related}
                        </div>
                        : '' }
                </div>
            );
        }
}

export default Item_Detail;
