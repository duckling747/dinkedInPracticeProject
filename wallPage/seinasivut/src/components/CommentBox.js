import React, { useState } from "react";
import { addComment } from "../services/user";

const CommentBox = ({ p, userId, show, refetchComments }) => {

    const [comment, setComment] = useState("");

    const sendIt = async () => {
        if (!comment) {
            alert("Please write something");
            return;
        }
        const r = confirm("Publish comment?");
        if (!r) return;
        const res = await addComment(userId, p.id, comment);
        if (!res.ok) {
            alert("Problems with commenting, comment not sent!");
            return;
        }
        setComment("");
        refetchComments();
        alert("Comment published!");
    };

    if (!show) return null;

    return (
        <div id="commentboxcontainer">
            <div id="commentbox">
                <textarea
                    id="commenttextarea"
                    cols="25"
                    rows="2"
                    placeholder="Write a comment..."
                    value={comment}
                    onChange={event => setComment(event.target.value)}
                >
                </textarea>
            </div>
            <div>
                <button id="commentbutton" type="button" onClick={sendIt}>
                    comment
                </button>
            </div>
        </div>
    );

}

export default CommentBox;
