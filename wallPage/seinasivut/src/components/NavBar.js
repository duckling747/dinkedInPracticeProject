import React from "react";
import dinkedin from "../img/logo.png";
import "./NavBar.css";


const searchForPostsHandler = (event) => {
    event.preventDefault();
    // search for posts
};

const NavBar = (props) => {


    const { username, current } = props;

    const showButtons =
        username === current
        ?
        <form action="/logout" method="POST">
            <button id="logoutbutton" type="submit">
                Logout
            </button>
        </form>
        :
        <div>
            <a href="/join">
                Join now
            </a>
            <a id="loginbutton"
                href="/login"
            >
                Sign in
            </a>
        </div>;

    return (
        <nav>
            <header>
                <a href="/">
                    <img id="logo" src={dinkedin} alt="DinkedIn" />
                </a>
                <div>
                    WALL
                </div>
            </header>
            <form onSubmit={searchForPostsHandler}>
                <input placeholder="search for posts"></input>
            </form>
            {showButtons}
        </nav>
    );
};

export default NavBar;
