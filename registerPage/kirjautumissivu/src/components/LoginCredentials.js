import React from "react";
import "./LoginCredentials.css";
import dinked from "../images/dinked.png";

export function LoginCredentials() {

    function submitHandler(event) {
        event.preventDefault();
        // use routes, remember to implement rest-api
    }

    return (
        <>
          <div id="heading">
            <img src={dinked} />
            <h3>Make the most of your life</h3>
          </div>
          <form onSubmit={submitHandler}>
            <p>
                <input type="text" placeholder="Email" />
            </p>
            <p>
                <input type="password" placeholder="Password (6 or more characters)" />
            </p>
            <button type="submit">Agree &amp; Join</button>
          </form>

        </>
    )
    
};
