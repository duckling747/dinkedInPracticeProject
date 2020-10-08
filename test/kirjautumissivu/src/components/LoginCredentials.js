import React from "react";

export function LoginCredentials() {

    function submitHandler(event) {
        event.preventDefault();

    }

    return (
        <>
            <h3>erittäin jännä testi</h3>
            <form onSubmit={submitHandler}>
                <input type="text" />
                <input type="password" />
            </form>
        </>
    )
    
};
