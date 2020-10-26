import React, { useState } from "react";
import "./LoginCredentials.css";
import dinked from "../images/dinked.png";
import ReCAPTCHA from "react-google-recaptcha";

export function LoginCredentials() {

  const[uname, setUname] = useState("");
  const[pw, setPw] = useState("");
  const[fname, setFname] = useState("");
  const[lname, setLname] = useState("");
  const[round, setRound] = useState(0);
  const[errors, setErrors] = useState([]);

  function onChange(setter) {
    return (event) => setter(event.target.value)
  }

  const pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);

  function validateEmail() {
    const msg = "Please enter a valid e-mail";
    if (!uname || !pattern.test(uname)) {
      setErrors(errors.concat(msg));
      return false;
    }
    return true;
  }

  function validatePw() {
    const msg = "Please enter a password at least 6 characters long"
    if (pw.length < 6) {
      setErrors(errors.concat(msg))
      return false;
    }
    return true;
  }

  function firstHandler(event) {
    event.preventDefault();
    if (validateEmail() &&
        validatePw()) {
      setErrors([]);
      setRound(1);
    }
  }

  function secondHandler(event) {
    event.preventDefault();
    //possibly validate names here
    if (true) {
      setErrors([]);
      setRound(2);
    }
  }

  const url = "/accounts";

  async function sendData() {
    // send user to rest api
    const cookieVal = document.cookie
      .split('; ')
      .find(r => r.startsWith('XSRF-TOKEN'))
      .split('=')[1];
    if (!cookieVal) {
      alert("Session cookie not found");
      window.location.href = "/";
    }

    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'X-XSRF-TOKEN': cookieVal
      },
      body: JSON.stringify({
        username: uname,
        password: pw,
        firstName: fname,
        lastName: lname
      })
    });
    console.log("Sent data...", response);
    if (response.ok)
      window.setTimeout( async () => {
        const data = new URLSearchParams();
        data.append("username", uname);
        data.append("password", pw);
        const res = await fetch("/login", {
          method: "POST",
          headers: {
            'X-XSRF-TOKEN': cookieVal,
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          body: data
        })
        if (!res.ok) {
          alert("Error logging in!");
        } else {
          window.location.href = "/";
        }
      }, 1000);
    else
      console.error("error sending data");
  }

  function renderMe(round) {
    switch(round) {
      case 0:
        return (
        <form onSubmit={firstHandler}>
          <p><input value={uname} onChange={onChange(setUname)} placeholder="Email" /></p>
          <p><input value={pw} onChange={onChange(setPw)} type="password" placeholder="Password (min. 6 characters)" /></p>
          <button type="submit">Sign in</button>
        </form>)
      case 1:
        return (
        <form onSubmit={secondHandler}>
          <p><input value={fname} onChange={onChange(setFname)} placeholder="First Name" /></p>
          <p><input value={lname} onChange={onChange(setLname)} placeholder="Last Name" /></p>
          <button type="submit">I'm ready</button>
        </form>)
      case 2:
        return (
        <>
          <p>One more step. Please prove you're a human:</p>
          <p>
            <ReCAPTCHA
              id="captcha"
              sitekey="6LcuONYZAAAAAE6I5WWAgn1h_6yegiRvl3Z4Up-B"
              onChange={sendData}
            />
          </p>
        </>)
      default:
        throw new Error("Illegal round in react signin forms stuff")
    }
  }

  
  return (
      <>
        <div id="heading">
          <img alt="Dinked In" src={dinked} />
          <h3>Make the most of your life</h3>
        </div>
        {
          errors.map((error, i) => 
            <p className="error" key={i}>
              {error}
            </p>
          )
        }
        {renderMe(round)}
      </>
  )

};
