import React, { useState } from "react";
import dinkedin from "../img/logo.png";
import "./NavBar.css";
import { postLogout } from "../services/user";

const NavBar = (props) => {


    const { username, current, setFilter } = props;

    
    const searchForPostsHandler = (event) => {
        event.preventDefault();
        setFilter(event.target.value);
    };
    
    

    const submitLogout = (event) => {
        event.preventDefault();
        postLogout()
            .then(res => {
                if(res.ok) {
                    window.location.href = "/login?logout";
                } else {
                    alert("Error logging out!");
                }
            });
    };

    const showButtons =
        username === current
        ?
        <div>
            <form onSubmit={event => submitLogout(event)}>
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
