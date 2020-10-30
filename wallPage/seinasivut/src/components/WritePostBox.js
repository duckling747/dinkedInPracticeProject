import React, { useEffect, useState } from "react";
import { postPost } from "../services/user";
import "./WritePostBox.css";


const buttonTexts = [
    "Send it!",
    "Hit it!",
    "Smash that button!",
    "No turning back!",
    "Regret is for monkeys!"
];

const randomizedMessage = () => {
    return buttonTexts[buttonTexts.length * Math.random() | 0];
};

const WritePostBox = ({ id, refetchPosts, username, current }) => {

    const [post, setPost] = useState("");
    const [buttonMessage, setButtonMessage] = useState("");

    useEffect(() => {
        setButtonMessage(randomizedMessage());
    }, []);

    const sendIt = async () => {
        if (!post) {
            alert("Please write something");
            return;
        }
        const r = confirm("Publish?");
        if (!r) return;
        const res = await postPost(id, post);
        if (!res.ok) {
            alert("problems with send!");
            return;
        }
        setPost("");
        console.log("resposts", res);
        setButtonMessage(randomizedMessage());
        refetchPosts();
    };
    if (username !== current) return null;

    return (
        <div id="postbox">
            <div id="textareabox">
                <textarea
                    id="posttextarea"
                    cols="40"
                    rows="5"
                    placeholder="Write something..."
                    value={post}
                    onChange={event => setPost(event.target.value)}
                >
                </textarea>
            </div>
            <div>
                <button id="postbutton" type="button" onClick={sendIt}>
                    {buttonMessage}
                </button>
            </div>
        </div>
    );
};

export default WritePostBox;
