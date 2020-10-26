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
        <div>
            <form action="/logout" method="POST">
                <button id="logoutbutton" type="submit">
                    Logout
                </button>
            </form>
        </div>
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
            <div>
                <header>
                    <a href="/">
                        <img id="logo" src={dinkedin} alt="DinkedIn" />
                    </a>
                </header>
            </div>
            <div id="authlinks">
                <div id="filter">
                    <input
                        onChange={searchForPostsHandler}
                        placeholder="Filter posts..." 
                    />
                </div>
                {showButtons}
            </div>
        </nav>
    );
};

export default NavBar;
