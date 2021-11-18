import React from 'react'
import {Link} from "react-router-dom";

const Posts = ({posts, loading}) =>{
    if(loading){
        return <h2>Loading</h2>;
    }

    return <div className="row">
        {posts.slice(0,8).map(item=>(
            <div className="column">
                <div className="card">
                    <Link to={"/item/" + item.itemId}>
                        <img src={item.images} alt={item.name}/>
                    </Link>
                    <p>{item.name}</p>
                    <p>${item.price}</p>
                </div>
            </div>
        ))}
    </div>;
};

export default Posts;