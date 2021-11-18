import React from 'react'

const Pagination = ({postsPerPage, totalPosts, paginate, currentPage}) =>{
    const pageNumbers = [];
    let prevPage;
    let nextPage;
    let maxPages = Math.ceil(totalPosts/postsPerPage);
    for(let i=1; i<=maxPages; i++){
        pageNumbers.push(i);
    }
    if (currentPage===1){
        prevPage = <a>&laquo;</a>
        nextPage = <a onClick={()=> paginate(currentPage+1)}>&raquo;</a>
    }
    else if(currentPage === maxPages){
        prevPage = <a onClick={()=> paginate(currentPage-1)}>&laquo;</a>
        nextPage = <a>&raquo;</a>
    }
    else{
        prevPage = <a onClick={()=> paginate(currentPage-1)}>&laquo;</a>
        nextPage = <a onClick={()=> paginate(currentPage+1)}>&raquo;</a>
    }
   return(
       <div className="center">
           <div className="pagination">
               {prevPage}
               {pageNumbers.map(number =>(
                   <a onClick={()=> paginate(number)} href="#/listing">{number}</a>
               ))}
               {nextPage}
           </div>
       </div>
   )
};

export default Pagination;