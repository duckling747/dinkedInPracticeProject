import React, { useEffect, useState } from "react";
import { getComments } from "../services/user";
import CommentBox from "./CommentBox";

const Comments = ({ userId, p, show }) => {
    
    const [showComments, setShowComments] = useState(false);
    const [comments, setComments] = useState([]);

    useEffect(() => {
        if (!showComments) return;
        console.log("running useEffect");
        getComments(userId, p.id)
            .then(bod => setComments(bod));
    }, [showComments]);

    const refetchComments = async () => {
        const bod = await getComments(userId, p.id);
        setComments([...bod]);
    };

    const commentsButtonListener = () => {
        setShowComments(!showComments);
    };

    const ShowCommentsButton = () => {
        if (!showComments)
            return <div><button onClick={commentsButtonListener}>Show comments</button></div>
        else
            return <div><button onClick={commentsButtonListener}>Close comments</button></div>
    };

    if (!showComments) {
        return(
            <ShowCommentsButton />
        )
    }

    if (comments.length < 1) return <p>no comments yet...</p>

    return (
        <div>
            <ShowCommentsButton />
            <h4>Comments</h4>
                <div>
                    {
                    comments
                    .map((c, i) =>
                    <section key={i}>
                        <i>
                        {
                        `@${new Date(c.timestamp).toLocaleString()}, by ${c.user.username}:`
                        }
                        </i>
                        <p>
                        {c.text}
                        </p>
                    </section>
                    )
                    }
                </div>
            <CommentBox p={p} userId={userId}
                show={show} refetchComments={refetchComments}
            />
        </div>
    )
};

export default Comments;
