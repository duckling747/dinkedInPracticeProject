import React from "react";



const searchForPostsHandler = (event) => {
    event.preventDefault();
    // search for posts
};

const NavBar = (props) => {
    return (
        <nav>
            <title></title>
            <form onSubmit={searchForPostsHandler}>
                <input placeholder="search for posts"></input>
            </form>
            <form action="http://localhost:8080/logout" method="POST">
                <button id="logoutbutton" type="submit">
                    Logout
                </button>
            </form>
        </nav>
    );
};

export default NavBar;
