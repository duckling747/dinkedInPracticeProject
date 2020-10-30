import React from "react";
import dinkedin from "../img/logo.png";
import cog from "../img/cog.png";
import "./NavBar.css";
import { postLogout } from "../services/user";
import PropTypes from "prop-types";

const NavBar = (props) => {

    const { setFilter } = props;

    const searchForPostsHandler = (event) => {
        event.preventDefault();
        setFilter(event.target.value);
    };

    const submitLogout = async (event) => {
        event.preventDefault();
        const res = await postLogout();
        if (res.ok) {
            window.location.href = "/login?logout";
        } else {
            alert("Error logging out!");
        }
    };

    return (
        <nav>
            <div>
                <header>
                    <a href="/">
                        <img id="logo" src={dinkedin} alt="DinkedIn" />
                    </a>
                </header>
            </div>
            <div>
                <a id="settingslink" href="/settings">
                    <img id="cog" src={cog} alt="cog" />
                </a>
            </div>
            <div id="authlinks">
                <div id="filter">
                    <input
                        onChange={searchForPostsHandler}
                        placeholder="Filter posts..." 
                    />
                </div>
                <div>
                    <form onSubmit={event => submitLogout(event)}>
                        <button id="logoutbutton" type="submit">
                            Logout
                        </button>
                    </form>
                </div>
            </div>
        </nav>
    );
};

NavBar.propTypes = {
    setFilter: PropTypes.func.isRequired
}

export default NavBar;
