function submitCheck() {
    // password/username check goes here
    // return false to cancel submit

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    console.log(username);
    console.log(password);

    if(password.length < 8){
        alert("password must at least 8 characters");
        return false;
    }else{
        return true;
    }
}
