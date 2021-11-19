import React, {useEffect,useState} from 'react';
import axios from 'axios'
import './listing.css';
import Header from "./header";
import Posts from './posts'
import Pagination from './Pagination'
import {Button} from "react-bootstrap";

const Listing = () =>{
    const[posts, setPosts] = useState([]);
    const[loading, setLoading] = useState(false);
    const[currentPage, setCurrentPage] = useState(1);
    const[postPerPage] = useState(8);

    const defaultCategorize = {
        "category":"electronics",
        "userId":"",
        "location":"",
        "pricing":""
    }
    useEffect(()=> {
        const fetchPosts = async () => {
            setLoading(true);
            const res = await axios.post('/api/categoryitem', defaultCategorize);
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
    /*for categorized items*/
    const[clicked,setClicked] = useState(false);
    const[category, setCategory] = useState([]);
    const electronics = {
        "category":"electronics",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const fetchElectronics = async () => {
        setLoading(true);
        const res = await axios.post('/api/categoryitem', electronics);
        setClicked(current => !current);
        setPosts(res.data.item);
        setCurrentPage(currentPage => 1);
        setLoading(false);
    }
    const textbooks = {
        "category":"textbooks",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const fetchTextbooks = async () => {
        setLoading(true);
        const res = await axios.post('/api/categoryitem', textbooks);
        setClicked(current => !current);
        setPosts(res.data.item);
        setCurrentPage(currentPage => 1);
        setLoading(false);
    }
    const clothes = {
        "category":"clothes",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const fetchClothes = async () => {
        setLoading(true);
        const res = await axios.post('/api/categoryitem', clothes);
        setClicked(current => !current);
        setPosts(res.data.item);
        setCurrentPage(currentPage => 1);
        setLoading(false);
    }
    const furnitures = {
        "category":"furniture",
        "userId":"",
        "location":"",
        "pricing":""
    }
    const fetchFurnitures = async () => {
        setLoading(true);
        const res = await axios.post('/api/categoryitem', furnitures);
        setClicked(current => !current);
        setPosts(res.data.item);
        setCurrentPage(currentPage => 1);
        setLoading(false);
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
                <Button onClick={fetchPosts}>All Items</Button>
                <Button onClick={fetchElectronics}>Electronics</Button>
                <Button onClick={fetchTextbooks}>Textbooks</Button>
                <Button onClick={fetchClothes}>Clothes</Button>
                <Button onClick={fetchFurnitures}>Furnitures</Button>
            </div>
            <Posts posts={currentPosts} loading={loading}/>
            <Pagination postsPerPage={postPerPage} totalPosts={posts.length} paginate={paginate} currentPage={currentPage}/>
        </div>
    );
}

export default Listing;