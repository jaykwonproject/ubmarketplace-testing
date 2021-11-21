import React, {useEffect,useState} from 'react';
import axios from 'axios'
import './listing.css';
import Header from "./header";
import Posts from './posts'
import Pagination from './Pagination'
import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";

const Listing = () =>{
    const[posts, setPosts] = useState([]);
    const[loading, setLoading] = useState(false);
    const[currentPage, setCurrentPage] = useState(1);
    const[postPerPage] = useState(8);

    useEffect(()=> {
        const fetchPosts = async () => {
            setLoading(true);
            const res = await axios.get('/api/allitem');
            setPosts(res.data.item);
            setLoading(false);
        }
        fetchPosts();
    }, []);


    const fetchPosts = async () => {
        setLoading(true);
        const res = await axios.get('/api/allitem');
        setPosts(res.data.item);
        setCurrentPage(currentPage => 1);
        setLoading(false);
    }

    const fetchSortedItems = async (param) => {
        setLoading(true);
        const res = await axios.post('/api/categoryitem', param);
        setPosts(res.data.item);
        setCurrentPage(currentPage => 1);
        setLoading(false);
    }
    const electronics = {
        "category":"electronics",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const textbooks = {
        "category":"textbooks",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const clothes = {
        "category":"clothes",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const furnitures = {
        "category":"furniture",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const priceLow2High = {
        "category":"",
        "userId":"",
        "location":"",
        "pricing":"ascend"
    }
    const priceHigh2Low = {
        "category":"",
        "userId":"",
        "location":"",
        "pricing":"descend"
    }
    const NSC = {
        "category":"",
        "userId":"",
        "location":"NSC",
        "pricing":""
    }
    const Capen = {
        "category":"",
        "userId":"",
        "location":"Capen",
        "pricing":""
    }
    const Norton = {
        "category":"",
        "userId":"",
        "location":"Norton",
        "pricing":""
    }



    const indexOfLastPost = currentPage * postPerPage;
    const indexofFirstPost = indexOfLastPost - postPerPage;
    const currentPosts = posts.slice(indexofFirstPost, indexOfLastPost);
    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const email = localStorage.getItem("email");
    return (
        <div className="listing">
            <Header email={email}/>
            <div className="searchBar">
                <div className="sorting">
                    <button className="sortBtn">Category</button>
                    <div className="sort-content">
                        <Button className="btns" onClick={fetchPosts}>All Items</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(electronics)}>Electronics</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(textbooks)}>Textbooks</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(clothes)}>Clothes</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(furnitures)}>Furnitures</Button>
                    </div>
                </div>
                <div className="sorting">
                    <button className="sortBtn">Price</button>
                    <div className="sort-content">
                        <Button className="btns" onClick={()=>fetchSortedItems(priceLow2High)}>Ascending</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(priceHigh2Low)}>Descending</Button>
                    </div>
                </div>
                <div className="sorting">
                    <button className="sortBtn">Location</button>
                    <div className="sort-content">
                        <Button className="btns" onClick={()=>fetchSortedItems(NSC)}>NSC</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(Capen)}>Capen</Button>
                        <Button className="btns" onClick={()=>fetchSortedItems(Norton)}>Norton</Button>
                    </div>
                </div>
            </div>
            <Posts posts={currentPosts} loading={loading}/>
            <Pagination postsPerPage={postPerPage} totalPosts={posts.length} paginate={paginate} currentPage={currentPage}/>
        </div>
    );
}

export default Listing;